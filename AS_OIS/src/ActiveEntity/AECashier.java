/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActiveEntity;

import Communication.CClient;
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
    
    private CClient cClient;
    
    public AECashier(int maxCustomers, IIdle_Cashier idle, IPaymentHall_Cashier paymentHall, 
            IPaymentPoint_Cashier entranceHall, GUI_Manager gUI_Manager, CClient cClient){
        this.idle = idle;
        this.paymentHall = paymentHall;
        this.paymentPoint = entranceHall;
        this.maxCustomers = maxCustomers;
        this.gUI_Manager = gUI_Manager;
        this.cClient = cClient;
    }
    
    @Override
    public void run() {
        //local vars
        int paidCustomers = 0;
        boolean idled = false;
        while (true){
            try {
                gUI_Manager.moveCashier(0);
                if (!idled)
                    cClient.send("cashier#idle");
                this.idle.idleCashier();
                idled = true;
                if (this.paymentHall.getCount() > 0){
                    gUI_Manager.moveCashier(1);
                    cClient.send("cashier#paymentbox");
                    this.paymentHall.call();
                    TimeUnit.MILLISECONDS.sleep(100);
                    
                    cClient.send("cashier#idle");
                    gUI_Manager.moveCashier(0);
                    this.paymentPoint.call();
                    paidCustomers += 1;
                    TimeUnit.MILLISECONDS.sleep(100);
                }
                
                if (paidCustomers == maxCustomers) {
                    cClient.send("cashier#idle");
                    this.idle.idleCashier();
                    idled = false;
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(AECashier.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
