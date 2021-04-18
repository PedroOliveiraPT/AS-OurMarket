/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SAOutsideHall;

import FIFO.IFIFO;
import FIFO.FIFO;

/**
 *
 * @author omp 
 */
public class SAOutsideHall implements IOutsideHall_Manager,
                                      IOutsideHall_Customer,
                                      IOutsideHall_Control {
    
    private IFIFO saOutsideHallFIFO;

    public SAOutsideHall( int maxCustomers ) {
        this.saOutsideHallFIFO = new FIFO(maxCustomers);
    } 
    @Override
    public void call() {
        this.saOutsideHallFIFO.out();
    }

    @Override
    public void in(int customerId) {
        this.saOutsideHallFIFO.in(customerId);
    }
}
