/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActiveEntity;

import SAIdle.IIdle_Cashier;
import SAPaymentHall.IPaymentHall_Cashier;
import SAPaymentPoint.IPaymentPoint_Cashier;

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
    
    public AECashier(IIdle_Cashier idle, IPaymentHall_Cashier outsideHall, IPaymentPoint_Cashier entranceHall){
        this.idle = idle;
        this.paymentHall = outsideHall;
        this.paymentPoint = entranceHall;
        
    }
    
    @Override
    public void run() {
        while (true){
            // this.idle.idle();
            
            this.paymentHall.call();
            
            this.paymentPoint.call();
        }
    }
}
