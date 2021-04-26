/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActiveEntity;


import SACorridorHall.ICorridorHall_Manager;
import SAEntranceHall.IEntranceHall_Manager;
import SAIdle.IIdle_Manager;
import SAOutsideHall.IOutsideHall_Manager;
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
    
    public AEManager(IIdle_Manager idle, IOutsideHall_Manager outsideHall, IEntranceHall_Manager entranceHall, ICorridorHall_Manager[] corridorHalls){
        this.idle = idle;
        this.outsideHall = outsideHall;
        this.entranceHall = entranceHall;
        this.corridorHalls = corridorHalls;
    }
    
    @Override
    public void run() {
        while (true){
            // this.idle.idle();
            
            if (!this.entranceHall.checkFull() && this.outsideHall.count() > 0)
                this.outsideHall.call(); //
            
            for (ICorridorHall_Manager man: corridorHalls)
                if (!man.checkFull() && this.entranceHall.count() > 0)
                    this.entranceHall.call();
        }
    }
}
