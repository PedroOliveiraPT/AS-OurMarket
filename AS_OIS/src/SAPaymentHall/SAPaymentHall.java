/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SAPaymentHall;

import FIFO.FIFO;
import FIFO.IFIFO;

/**
 *
 * @author pedro
 */
public class SAPaymentHall implements IPaymentHall_Cashier,
                                    IPaymentHall_Control,
                                    IPaymentHall_Customer{
    
    public IFIFO saPaymentHall;

    public SAPaymentHall( int maxCustomers ) {
        this.saPaymentHall = new FIFO(maxCustomers);
    }
    
    @Override
    public void in(int customerId) {
        this.saPaymentHall.in(customerId);
    }

    @Override
    public void call() {
        this.saPaymentHall.out();
    }
        
}
