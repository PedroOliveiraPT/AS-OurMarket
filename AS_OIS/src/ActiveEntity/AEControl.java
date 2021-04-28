

package ActiveEntity;

import Communication.CServer;
import SACorridor.SACorridor;
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
    
    private SACorridor[] sACorridor;
    private AEManager aem;
    
    public AEControl( IIdle_Control idle, CServer cServer, SACorridor[] sAC, AEManager aem) {
        this.idle = idle;
        this.cServer = cServer;
        
        this.sACorridor = sAC;
        this.aem = aem;
    }
    public void start( int nCustomers /*, Socket socket */) {
        idle.start( nCustomers );
    }
    
    public void pause(){
        this.idle.pause();
    }
    public void resumeRun(){
        this.idle.resume();
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
            String args[];
            System.out.println("Received: " + msg);
            msg = msg.toLowerCase();
            if (msg.startsWith("start")){   // n verifico se manda 1 start durante 1 execução: supoe-se k isso n acontece
                args = msg.split("#");
                int cto = Integer.parseInt(args[2]);
                for (SACorridor sACorridor1 : sACorridor) {
                    sACorridor1.setCto(cto);    
                }
                int sto = Integer.parseInt(args[3]);
                aem.setSto(sto);
                
                start(Integer.parseInt(args[1]));
            } else if (msg.equals("suspend")){
                this.pause();
            } else if (msg.equals("resume")) {
                this.resumeRun();
            } else if (msg.equals("stop")){
                this.end();
            }
        
        }
    }
}
