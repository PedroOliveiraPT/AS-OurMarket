/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActiveEntity;

import GUI.GUI_Manager;
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
    
    //    GUI MANAGER
    private GUI_Manager gUI_Manager;
    
    private StatusCashier stCashier;
    
    public AECashier(int maxCustomers, IIdle_Cashier idle, IPaymentHall_Cashier paymentHall, 
            IPaymentPoint_Cashier entranceHall, GUI_Manager gUI_Manager){
        this.idle = idle;
        this.paymentHall = paymentHall;
        this.paymentPoint = entranceHall;
        this.maxCustomers = maxCustomers;
        this.gUI_Manager = gUI_Manager;
        this.stCashier = StatusCashier.IDLE;
    }
    
    @Override
    public void run() {
        //local vars
        int paidCustomers = 0;
        StatusCashier temp;
        while (true){
            try {
                gUI_Manager.moveCashier(0);
                temp = this.idle.idleCashier();
                stCashier = (temp==null)? stCashier:temp;
                if (stCashier == StatusCashier.IDLE){
                    stCashier = StatusCashier.PAYMENTHALL;
                }
                if (this.paymentHall.getCount() > 0){
                    
                    if (stCashier == StatusCashier.PAYMENTHALL){
                        gUI_Manager.moveCashier(1);
                        this.paymentHall.call();
                        stCashier = StatusCashier.PAYMENTPOINT;
                        TimeUnit.MILLISECONDS.sleep(100);
                    
                    }
                    
                    if (stCashier == StatusCashier.PAYMENTPOINT){
                        gUI_Manager.moveCashier(0);
                        this.paymentPoint.call();
                        stCashier = StatusCashier.PAYMENTHALL;
                        this.idle.cashierIncrement();
                        TimeUnit.MILLISECONDS.sleep(100);
                    }
                    
                }
                
                if (paidCustomers == maxCustomers) this.idle.idleCashier();
            } catch (InterruptedException ex) {
                Logger.getLogger(AECashier.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
