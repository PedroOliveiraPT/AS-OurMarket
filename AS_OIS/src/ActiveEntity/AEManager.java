/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActiveEntity;


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
    
    public AEManager(IIdle_Manager idle, IOutsideHall_Manager outsideHall, IEntranceHall_Manager entranceHall){
        this.idle = idle;
        this.outsideHall = outsideHall;
        this.entranceHall = entranceHall;
        
    }
    
    @Override
    public void run() {
        while (true){
            // this.idle.idle();
            
            this.outsideHall.call();
            
            System.out.println(this.entranceHall.count());
            
            this.entranceHall.call();
        }
    }
}
