

package ActiveEntity;

import java.net.Socket;

/**
 * Esta entidade é responsável por fazer executar os comandos originados no OCC
 * tais como start, stop, end, etc....
 * 
 * @author omp
 */
public class AEControl extends Thread {

    
    public AEControl() {
    }
    public void start( int nCustomers, Socket socket ) {

    }
    public void end() {
        // terminar Customers em idle
        // terminar restantes Customers e outras AE
    }
    // mais comandos 
    
    
    @Override
    public void run() {
        // ver qual a msg recebida, executar comando e responder
    }
}
