/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SAIdle;

import ActiveEntity.AECustomer;
import ActiveEntity.StatusCashier;
import ActiveEntity.StatusCustomer;
import ActiveEntity.StatusManager;
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

    private int maxCustomers;
    
    private int manCounter;
    private int cashCounter;
    
    private boolean pause;
    private boolean end;
    private boolean reset;
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
        
        this.maxCustomers = maxCustomers;
        
        manCounter = 0;
        cashCounter = 0;
        reset = false;
        this.pause = true;
    }
    @Override
    public StatusManager idle() {
        if (manCounter <= maxCustomers && !pause) return null;
        try {
            rl.lock();
            
            cManagerIdle.await();
            cWaking.signal();
        } catch (Exception ex) {}
        finally {
            rl.unlock();
        }
        if (reset || manCounter > maxCustomers) return StatusManager.IDLE;
        return null;
    }
    @Override
    public StatusCustomer idle( int customerId ) {
        if (!pause) return null;
        
        try {
            rl.lock();
            
            cCustomerIdle[customerId].await();
            
            cWaking.signal();
        } catch (Exception ex) {}
        finally {
            rl.unlock();
        }
        if (reset) return StatusCustomer.IDLE;
        return null;
    }
    
    @Override
    public StatusCashier idleCashier() {
        if (cashCounter <= maxCustomers && !pause) return null;
        try {
            rl.lock();
            
            cCashierIdle.await();
            cWaking.signal();
        } catch (Exception ex) {}
        finally {
            rl.unlock();
        }
        if (reset || cashCounter > maxCustomers) return StatusCashier.IDLE;
        return null;
    }
    
    @Override
    public void start( int nCustmers ) {
        cashIdle = false;
        manIdle = false;
        pause = false;
        this.maxCustomers = nCustmers;
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
    public void pause(){
        this.pause = true;
        
    }
    @Override
    public void end() {
        this.reset = true;
    }
    
    @Override
    public void resume(){
        this.start(this.maxCustomers);
    }

    @Override
    public void update(AECustomer aec) {
        
    }

    @Override
    public void managerIncrementCounter() {
        this.manCounter+=1;
    }

    @Override
    public void cashierIncrement() {
        this.cashCounter += 1;
    }

   
   
}
