/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SACorridor;

import FIFO.CorridorFIFO;
import FIFO.IFIFO;
import SACorridorHall.SACorridorHall;
import SAPaymentHall.SAPaymentHall;

/**
 *
 * @author pedro
 */
public class SACorridor implements ICorridor_Customer {
    private CorridorFIFO corridor;
    private SACorridorHall corrHall;
    
    private int cto = 100;

    public void setCto(int cto) {
        this.cto = cto;
    }
    
    public SACorridor(int maxCustomers, SACorridorHall corr) {
        this.corridor = new CorridorFIFO(maxCustomers);
        this.corrHall = corr;
    }
    
    
    @Override
    public void call() {
        corridor.out();
        this.corrHall.call();
    }

    @Override
    public void in(int customerId, SAPaymentHall ph) {
        corridor.shop(customerId, ph, cto);
        this.corrHall.call();
    }

    public int getIdxOut() {
        return this.corridor.getIdxOut();
    }
    
    @Override
    public boolean checkFull(){
        return this.corridor.full();
    }
    
}
