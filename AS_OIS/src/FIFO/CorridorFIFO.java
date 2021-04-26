/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FIFO;

import SAPaymentHall.SAPaymentHall;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class has the purpose of implementing a FIFO based on the next position
 * @author pedro
 */
public class CorridorFIFO implements IFIFO {
    // lock para acesso à área partilhada 
    private final ReentrantLock rl = new ReentrantLock( true );

    // array para ids dos customers
    private final int customerId[];
    // array para Condition de bloqueio (uma por customer)
    private final Condition cStay[];
    // array para Condition de bloqueio (uma por customer)
    private final Condition cShop[];
    // Conditio  de bloqueio de fifo cheio
    private final Condition cFull;
    // Condition de bloqueio de fifo vazio
    private final Condition cEmpty;
    // Condition de bloqueio para aguardar saída do fifo
    private final Condition cLeaving;
    // array para condição de bloqueio de cada customer
    private final boolean leave[];
    // array para treadmill de 10 passos para simular shopping
    private final boolean shopTreadmill[];
    // número máximo de customer (dimensão dos arrays)
    private final int maxCustomers;
    
    // próxima posição de escrita no fifo
    private int idxIn;
    // próxima posição de leitura no fifo
    private int idxOut;
    // número de customers no fifo
    private int count = 0;
    
    private static int id_in = 0;
    private static int id_out = 0;
    
    public CorridorFIFO( int maxCustomers ) {
        this.maxCustomers = maxCustomers;
        customerId = new int[ maxCustomers ];
        cStay = new Condition[ maxCustomers ];
        cShop = new Condition[ maxCustomers ];
        leave = new boolean[ maxCustomers ];
        for ( int i = 0; i < maxCustomers; i++ ) {
            cStay[ i ] = rl.newCondition();
            cShop[ i ] = rl.newCondition();
            leave[ i ] = false;
        }
        cFull = rl.newCondition();
        cEmpty = rl.newCondition();
        cLeaving = rl.newCondition();
        idxIn = 0;
        idxOut = 0;
        shopTreadmill = new boolean[10];
        for (int i = 0; i < 10; i++) shopTreadmill[i] = true;
    }

    @Override
    public int in(int customerId) {
        // variáveis locais
        int idx;
        int id = 0;
        try {
            // garantir acesso em exclusividade
            rl.lock();
                                  
            // se fifo cheio, espera na Condition cFull
            while ( count == maxCustomers )
                cFull.await();
                                   
            // esta operação não pode ser feita antes da anterior para
            // garantir q o idxIn utilizado é apenas deste Thread activo
            idx = idxIn;
            // incrementar apontador de escrita
            idxIn = (++idxIn) % maxCustomers;
            // inserir customer no fifo
            this.customerId[ idx ] = customerId;
            
            // incrementar número customers no fifo
            count++;
            
             // ciclo à espera de autorização para sair do fifo
            while ( !leave[ idx ] )
                // qd se faz await, permite-se q outros thread tenham acesso
                // à zona protegida pelo lock
                cStay[ idx ].await();

            // id do Customer q está a sair do fifo
            id = this.customerId[ idx ];
                    
            // atualizar variável de bloqueio
            leave[ idx ] = false;
            
            // testar se Customer q vai sair é o q está há mais tempo no fifo
            assert idx == ( idxOut == 0 ? maxCustomers - 1 : idxOut - 1 );
            // testar se o id do fifo corresponde ao id do Thread (Customer)
            assert customerId == id;
            
            
            
            // se fifo estava cheio, acordar Customer q esteja à espera de entrar
            if ( count == maxCustomers )
                cFull.signalAll();
            // decrementar número de customers no fifo
            count--;
            
        } catch ( Exception ex ) {}
        finally {
            rl.unlock();
        }
        // Customer a sair não só do fifo como tb a permitir q outros
        // threads possam entrar na zona crítica
        return id;
    }
    
    public void shop(int customerId, SAPaymentHall paymenthall) {
        try {
            // variáveis locais
            int idx;
            int id = 0;
            
            // esta operação não pode ser feita antes da anterior para
            // garantir q o idxIn utilizado é apenas deste Thread activo
            idx = idxIn;
            // incrementar apontador de escrita
            idxIn = (++idxIn) % maxCustomers;
            // inserir customer no fifo
            this.customerId[ idx ] = customerId;
            
            // incrementar número customers no fifo
            count++;
            for (int i = 0; i < 10; i++){
                
                while (!shopTreadmill[i]) TimeUnit.SECONDS.sleep(1);
                
                shopTreadmill[i] = false;
                
                TimeUnit.MILLISECONDS.sleep(100);
                
                System.out.println(customerId + "shopping item " + i);
                
                shopTreadmill[i] = true;
                
            }
            
            // ciclo à espera de autorização para sair do fifo
            while ( paymenthall.checkFull() ){
                System.out.println("is full, waiting"); 
                TimeUnit.MILLISECONDS.sleep(100);
            }
                // qd se faz await, permite-se q outros thread tenham acesso
                // à zona protegida pelo lock
               
            count--;
        } catch (InterruptedException ex) {
            Logger.getLogger(CorridorFIFO.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }

    @Override
    public void out() {
        if (count == 0) return;
        try {
            rl.lock();
            
            int idx = idxOut;
            // atualizar idxOut
            idxOut = (++idxOut) % maxCustomers; 
            // autorizar a saída do customer q há mais tempo está no fifo
            leave[ idx ] = true;
            // acordar o customer
            cStay[ idx ].signal();
            // aguardar até q Customer saia do fifo
            while ( leave[ idx ] == true )
                // qd se faz await, permite-se q outros thread tenham acesso
                // à zona protegida pelo lock
                cLeaving.await();  

        } catch ( Exception ex ) {}
        finally {
            rl.unlock();
        }
    }

    @Override
    public boolean full() {
        boolean isFull;
        rl.lock();
        try {
            isFull = count == maxCustomers;
        } finally {
            rl.unlock();
        }
        return isFull;
    }

    @Override
    public int getCount() {
        return this.count;
    }
    
    public int getIdxOut(){
        return this.idxOut;
    }
    
}
