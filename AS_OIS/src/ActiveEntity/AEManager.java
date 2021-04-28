/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActiveEntity;


import Communication.CClient;
import GUI.GUI_Manager;
import SACorridorHall.ICorridorHall_Manager;
import SAEntranceHall.IEntranceHall_Manager;
import SAIdle.IIdle_Manager;
import SAOutsideHall.IOutsideHall_Manager;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author pedro
 */
public class AEManager extends Thread { 
    
    // árae partilhada Idle
    private final IIdle_Manager idle;
    // área partilhada OutsideHall
    private final IOutsideHall_Manager outsideHall;
    // área partilhada EntranceHall
    private final IEntranceHall_Manager entranceHall;
    // Grupo de áreas para os Corridor Halls;
    private final ICorridorHall_Manager[] corridorHalls;
    
    private final int maxCustomers;
    
    //    GUI MANAGER
    private GUI_Manager gUI_Manager;
    
    private CClient cClient;
    
    public AEManager(int maxCustomers, IIdle_Manager idle, IOutsideHall_Manager outsideHall, 
            IEntranceHall_Manager entranceHall, ICorridorHall_Manager[] corridorHalls,
            GUI_Manager gUI_Manager, CClient cClient){
        this.idle = idle;
        this.outsideHall = outsideHall;
        this.entranceHall = entranceHall;
        this.corridorHalls = corridorHalls;
        this.maxCustomers = maxCustomers;
        this.gUI_Manager = gUI_Manager;
        this.cClient = cClient;
    }
    
    @Override
    public void run() {
        int numCustomersEnter = 0;
        boolean idled = false;
        while (true){
            gUI_Manager.moveManager(0);
            if (!idled)
                cClient.send("manager#idle");
            this.idle.idle();
            idled = true;
            if (!this.entranceHall.checkFull() && this.outsideHall.count() > 0){
                try {
                    gUI_Manager.moveManager(1);
                    cClient.send("manager#outsidehall");
                    this.outsideHall.call();
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(AEManager.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
                
            
            for (ICorridorHall_Manager man: corridorHalls){
                if (!man.checkFull() && this.entranceHall.count() > 0){
                    try {
                        gUI_Manager.moveManager(2);
                        cClient.send("manager#entrancehall");
                        this.entranceHall.call();
                        TimeUnit.MILLISECONDS.sleep(100);
                        numCustomersEnter += 1;
                    } catch (InterruptedException ex) {
                        Logger.getLogger(AEManager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
            if (numCustomersEnter == this.maxCustomers) {
                cClient.send("manager#idle");
                this.idle.idle();
                idled = false;
            }
        }
    }
}
