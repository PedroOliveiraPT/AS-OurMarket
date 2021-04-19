/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SAPaymentPoint;

import FIFO.FIFO;
import FIFO.IFIFO;

/**
 *
 * @author pedro
 */
public class SAPaymentPoint implements IPaymentPoint_Cashier,
                                        IPaymentPoint_Customer,
                                        IPaymentPoint_Control{
    public IFIFO saPaymentPoint;

    public SAPaymentPoint( int maxCustomers ) {
        this.saPaymentPoint = new FIFO(maxCustomers);
    }
    
    @Override
    public void in(int customerId) {
        this.saPaymentPoint.in(customerId);
    }

    @Override
    public void call() {
        this.saPaymentPoint.out();
    }
}
