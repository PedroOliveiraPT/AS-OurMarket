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
    
    public AECashier(IIdle_Cashier idle, IPaymentHall_Cashier paymentHall, IPaymentPoint_Cashier entranceHall){
        this.idle = idle;
        this.paymentHall = paymentHall;
        this.paymentPoint = entranceHall;
        
    }
    
    @Override
    public void run() {
        while (true){
            try {
                // this.idle.idle();
                if (this.paymentHall.getCount() > 0){
                    
                    this.paymentHall.call();
                    
                    this.paymentPoint.call();
                }
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(AECashier.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
