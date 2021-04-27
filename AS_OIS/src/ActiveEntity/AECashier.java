/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActiveEntity;

import SAIdle.IIdle_Cashier;
import SAPaymentHall.IPaymentHall_Cashier;
import SAPaymentPoint.IPaymentPoint_Cashier;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedro
 */
public class AECashier extends Thread{
    // árae partilhada Idle
    private final IIdle_Cashier idle;
    // área partilhada OutsideHall
    private final IPaymentHall_Cashier paymentHall;
    // área partilhada EntranceHall
    private final IPaymentPoint_Cashier paymentPoint;
    private final int maxCustomers;
    
    public AECashier(int maxCustomers, IIdle_Cashier idle, IPaymentHall_Cashier paymentHall, IPaymentPoint_Cashier entranceHall){
        this.idle = idle;
        this.paymentHall = paymentHall;
        this.paymentPoint = entranceHall;
        this.maxCustomers = maxCustomers;
        
    }
    
    @Override
    public void run() {
        //local vars
        int paidCustomers = 0;
        while (true){
            try {
                this.idle.idleCashier();
                if (this.paymentHall.getCount() > 0){
                    
                    this.paymentHall.call();
                    TimeUnit.MILLISECONDS.sleep(100);
                    
                    
                    
                    this.paymentPoint.call();
                    paidCustomers += 1;
                    TimeUnit.MILLISECONDS.sleep(100);
                }
                
                if (paidCustomers == maxCustomers) this.idle.idleCashier();
            } catch (InterruptedException ex) {
                Logger.getLogger(AECashier.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
