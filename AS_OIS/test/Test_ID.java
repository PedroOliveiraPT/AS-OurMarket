
import FIFO.FIFO;
import FIFO.IFIFO;
import java.util.concurrent.locks.ReentrantLock;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author omp
 */
public class Test_ID extends Thread {
    
    private final int id;
    private final IFIFO fifo;
    
    private static ReentrantLock rl = null;

    public Test_ID( int id, IFIFO fifo ) {
        this.id = id;
        this.fifo = fifo;
        // criar apenas uma instância de rl
        if ( rl == null )
            rl = new ReentrantLock(true);
    }
    
    // verificar se o id do Customer q saiu é o mm do que o  qentrou
    public void run() {
        // Thread q provoca saída de outros Threads do fifo?
        if ( id == -1 ) {
            while ( true )
                fifo.out();
        }
        // Threads q entram e saem do fifo
        while ( true ) {
            int i = fifo.in( id );
            try {
                rl.lock();
                System.out.printf( "IDs: %3d %3d\n", id, i );
                // se id q saiu do fifo não for o deste Thread, erro!
                if ( i != id )
                    System.exit( 1 );
            } catch( Exception ex ) {}
            finally {
                rl.unlock();
            }
        }
    }
    
    public static void main(String[] args) {
        // máximo número de Threads q entram no fifo
        final int MAX_THREADS = 20;
        // dimensão do fifo
        final int SIZE_FIFO = 15;
        // array para instâncias dos Threads q entram no fifo
        final Test_ID test[] = new Test_ID[ MAX_THREADS ];
        // o fifo
        final IFIFO fifo = new FIFO( SIZE_FIFO );
      
        // instanciar e ativar Threads
        for ( int i = 0; i < MAX_THREADS; i++ ) {
            test[ i ] = new Test_ID( i, fifo );
            test[ i ].start();
        }
        // instanciar Thread q provoca saídas do fifo
        Test_ID t = new Test_ID( -1, fifo);
        // ativar o Thread
        t.start();
    }
}
