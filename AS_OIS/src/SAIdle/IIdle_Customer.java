/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SAIdle;

import ActiveEntity.AECustomer;
import ActiveEntity.StatusCustomer;
/**
 *
 * @author omp
 */
public interface IIdle_Customer {
    public StatusCustomer idle( int customerId );
    public void pause();
    public void update(AECustomer aec);
}
