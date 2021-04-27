/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SAIdle;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author omp
 */
public class SAIdle implements IIdle_Customer,
                               IIdle_Manager,
                               IIdle_Control,
                               IIdle_Cashier{
    
    // lock para acesso à área partilhada 
    private final ReentrantLock rl = new ReentrantLock( true );

    // array para Condition de bloqueio (uma por customer)
    private final Condition cCustomerIdle[];
    
    private final Condition cManagerIdle;
    
    private final Condition cCashierIdle;
    
    private final Condition cWaking;

    public SAIdle( int maxCustomers ) {
        cCustomerIdle = new Condition[maxCustomers];
        for ( int i = 0; i < maxCustomers; i++ ) {
            cCustomerIdle[ i ] = rl.newCondition();
        }
        cManagerIdle = rl.newCondition();
        cCashierIdle = rl.newCondition();
        cWaking = rl.newCondition();
    }
    @Override
    public void idle() {
        try {
            rl.lock();
            
            cManagerIdle.await();
        } catch (Exception ex) {}
        finally {
            rl.unlock();
        }
    }
    @Override
    public void idle( int customerId ) {
        try {
            rl.lock();
            
            cCustomerIdle[customerId].await();
            
            cWaking.signal();
        } catch (Exception ex) {}
        finally {
            rl.unlock();
        }
    }
    
    @Override
    public void idleCashier() {
        try {
            rl.lock();
            
            cCashierIdle.await();
        } catch (Exception ex) {}
        finally {
            rl.unlock();
        }
    }
    
    @Override
    public void start( int nCustmers ) {
        try {
            rl.lock();
            cCashierIdle.signal();
            cWaking.await();
            
            cManagerIdle.signal();
            cWaking.await();
            
            for (int i = 0; i < nCustmers; i++) {
                cCustomerIdle[i].signal();
                cWaking.await();
            }
        } catch (Exception ex) {}
        finally {
            rl.unlock();
        }
        
        
    }
    @Override
    public void end() {   
    }

   
   
}
