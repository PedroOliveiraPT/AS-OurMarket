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
    
    private boolean manIdle;
    private boolean cashIdle;

    public SAIdle( int maxCustomers ) {
        cCustomerIdle = new Condition[maxCustomers];
        for ( int i = 0; i < maxCustomers; i++ ) {
            cCustomerIdle[ i ] = rl.newCondition();
        }
        cManagerIdle = rl.newCondition();
        cCashierIdle = rl.newCondition();
        cWaking = rl.newCondition();
        
        manIdle = true;
        cashIdle = true;
    }
    @Override
    public void idle() {
        if (!manIdle) return;
        try {
            rl.lock();
            
            cManagerIdle.await();
            cWaking.signal();
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
        if (!cashIdle) return;
        try {
            rl.lock();
            
            cCashierIdle.await();
            cWaking.signal();
        } catch (Exception ex) {}
        finally {
            rl.unlock();
        }
    }
    
    @Override
    public void start( int nCustmers ) {
        System.out.println("waking up ppl");
        cashIdle = false;
        manIdle = false;
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
