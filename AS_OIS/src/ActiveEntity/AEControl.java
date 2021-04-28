

package ActiveEntity;

import Communication.CServer;
import SAIdle.IIdle_Control;
import java.net.Socket;

/**
 * Esta entidade é responsável por fazer executar os comandos originados no OCC
 * tais como start, stop, end, etc....
 * 
 * @author omp
 */
public class AEControl extends Thread {

    private final IIdle_Control idle;
    private CServer cServer;
    
    public AEControl( IIdle_Control idle, CServer cServer /* mais áreas partilhadas */ ) {
        this.idle = idle;
        this.cServer = cServer;
    }
    public void start( int nCustomers /*, Socket socket */) {
        idle.start( nCustomers );
    }
    public void end() {
        // terminar Customers em idle
        idle.end();
        // terminar restantes Customers e outras AE
    }
    // mais comandos 
    
    
    @Override
    public void run() {
        while(true){
            String msg = cServer.get(); // thread blocks here
            System.out.println("Received: " + msg);
            if (msg.equalsIgnoreCase("start")){   // n verifico se manda 1 start durante 1 execução: supoe-se k isso n acontece
                start(75); 
            } else if (msg.equalsIgnoreCase("anything")){
                System.out.println("PEDRO DO");
            }
        }
    }
}
