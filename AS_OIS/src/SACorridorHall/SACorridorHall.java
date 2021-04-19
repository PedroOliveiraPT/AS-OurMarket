/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SACorridorHall;

import FIFO.FIFO;
import FIFO.IFIFO;

/**
 *
 * @author pedro
 */
public class SACorridorHall implements ICorridorHall_Customer{
    private IFIFO saCorridorHall;
    
    public SACorridorHall(int maxCustomers){
        this.saCorridorHall = new FIFO(maxCustomers);
    }
    
}
