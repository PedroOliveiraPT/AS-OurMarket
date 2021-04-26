/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SACorridorHall;

import FIFO.CorridorFIFO;
import FIFO.IFIFO;

/**
 *
 * @author pedro
 */
public class SACorridorHall implements ICorridorHall_Customer, ICorridorHall_Manager{
    private IFIFO saCorridorHall;
    
    public SACorridorHall(int maxCustomers){
        this.saCorridorHall = new CorridorFIFO(maxCustomers);
    }
    
    public void in(int customerId){
        this.saCorridorHall.in(customerId);
    }

    public void call(){
        this.saCorridorHall.out();
    }
    
    public boolean checkFull(){
        return this.saCorridorHall.full();
    }

}
