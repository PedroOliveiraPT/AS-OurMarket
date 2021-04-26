/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SAEntranceHall;

import FIFO.FIFO;
import FIFO.IFIFO;

/**
 *
 * @author omp
 */
public class SAEntranceHall implements IEntranceHall_Customer,
                                       IEntranceHall_Manager,
                                       IEntranceHall_Control {

    public IFIFO saEntranceHall;

    public SAEntranceHall( int maxCustomers ) {
        this.saEntranceHall = new FIFO(maxCustomers);
    }
    
    @Override
    public void in(int customerId) {
        this.saEntranceHall.in(customerId);
    }

    @Override
    public void call() {
        this.saEntranceHall.out();
    }
    
    @Override
    public int count(){ return this.saEntranceHall.getCount(); }

    @Override
    public boolean checkFull() {
        return this.saEntranceHall.full();
    }
}
