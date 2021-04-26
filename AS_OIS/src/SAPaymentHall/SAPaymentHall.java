/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SAPaymentHall;

import FIFO.CorridorFIFO;
import FIFO.FIFO;
import FIFO.IFIFO;
import SACorridor.SACorridor;

/**
 *
 * @author pedro
 */
public class SAPaymentHall implements IPaymentHall_Cashier,
                                    IPaymentHall_Control,
                                    IPaymentHall_Customer{
    
    private IFIFO saPaymentHall;
    private SACorridor[] saCorridors;

    public SAPaymentHall( int maxCustomers, SACorridor[] saCorridors ) {
        this.saPaymentHall = new FIFO(maxCustomers);
        this.saCorridors = saCorridors;
    }
    
    @Override
    public void in(int customerId) {
        this.saPaymentHall.in(customerId);
    }

    @Override
    public void call() {
        /*SACorridor outCorr = this.saCorridors[0];
        int oldest_id = -1, id = outCorr.getIdxOut();
        for (SACorridor corr: this.saCorridors){
            id = corr.getIdxOut();
            if (oldest_id < 0 || oldest_id < id){
                outCorr = corr;
                oldest_id = id;
            }
        }
        System.out.println("id " + id);
        */
        this.saPaymentHall.out();
    }
    
    @Override
    public boolean checkFull(){
        return this.saPaymentHall.full();
    }

    @Override
    public int getCount() {
        return this.saPaymentHall.getCount(); //To change body of generated methods, choose Tools | Templates.
    }
        
}
