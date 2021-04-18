/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SAIdle;

/**
 * @author omp
 */
public class SAIdle implements IIdle_Customer,
                               IIdle_Manager,
                               IIdle_Control {

    public SAIdle() {
    }
    @Override
    public void idle() {
    }
    @Override
    public void idle( int customerId ) {
    }
    @Override
    public void start( int nCustmers ) {
        for (int i = 0; i < nCustmers; i++) continue; // here we unlock the N customers
        
    }
    @Override
    public void end() {   
    }
   
}
