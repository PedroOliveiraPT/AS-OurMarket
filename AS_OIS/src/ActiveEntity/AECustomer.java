package ActiveEntity;


import SACorridor.ICorridor_Customer;
import SAEntranceHall.IEntranceHall_Customer;
import SAIdle.IIdle_Customer;
import SAOutsideHall.IOutsideHall_Customer;
import SACorridorHall.ICorridorHall_Customer;
import SAPaymentHall.IPaymentHall_Customer;
import SAPaymentHall.SAPaymentHall;
import SAPaymentPoint.IPaymentPoint_Customer;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Não pretende implementar a entidade activa Customer. Serve apenas para dar pistas como o
 * Thread Custumer deve recorrer a àreas partilhadas para gerir as transições de estado.
 * @author omp
 */
public class AECustomer extends Thread { 
    
    // id do customer
    private final int customerId;
    
    // árae partilhada Idle
    private final IIdle_Customer idle;
    // área partilhada OutsideHall
    private final IOutsideHall_Customer outsideHall;
    // área partilhada EntranceHall
    private final IEntranceHall_Customer entranceHall;
    // área partilhada CorridorHall
    private final ICorridorHall_Customer[] corridorHall;
    // área partilhada Corridor
    private final ICorridor_Customer[] corridorShop;
    // área partilhada PaymentHall
    private final IPaymentHall_Customer paymentHall;
    // área partilhada PaymentPoint
    private final IPaymentPoint_Customer paymentPoint;
    
    public AECustomer( int customerId, IIdle_Customer idle, IOutsideHall_Customer outsideHall, 
                        IEntranceHall_Customer entranceHall, ICorridorHall_Customer[] corridorHall,
                        ICorridor_Customer[] corridorShop, IPaymentHall_Customer paymentHall, IPaymentPoint_Customer paymentPoint ) {
        this.customerId = customerId; 
        this.idle = idle;
        this.outsideHall = outsideHall;
        this.entranceHall = entranceHall;
        this.corridorHall = corridorHall;
        this.corridorShop = corridorShop;
        this.paymentHall = paymentHall;
        this.paymentPoint = paymentPoint;
    }
    @Override
    public void run() {
        while ( true ) {
            try {
                // thread avança para Idle
                // idle.idle(customerId );
                // se simulação activa (não suspend, não stop, não end), thread avança para o outsideHall
                System.out.println(this.customerId + " entering Outside Hall");
                outsideHall.in( customerId );
                TimeUnit.MILLISECONDS.sleep(100);
                //System.out.println(this.customerId + " entering Entrance Hall");
                entranceHall.in(customerId);
                TimeUnit.MILLISECONDS.sleep(100);
                //System.out.println(this.customerId + " left entrance hall");
                int curr_index=0;
                for (int curr=0; curr < corridorHall.length; curr++){
                    if (!corridorHall[curr].checkFull()){
                        curr_index = curr;
                        break;
                    }
                }
                
                //
                if (corridorShop[curr_index].checkFull()){
                    //System.out.println(this.customerId + " entering corridor hall num " + curr_index);
                    corridorHall[curr_index].in(customerId);
                    TimeUnit.MILLISECONDS.sleep(100);
                }
                corridorShop[curr_index].in(customerId, (SAPaymentHall) this.paymentHall);
                TimeUnit.MILLISECONDS.sleep(100);
                //System.out.println(this.customerId + " payment hall ");
                //corridorShop[curr_index].call();
                this.paymentHall.in(customerId);
                TimeUnit.MILLISECONDS.sleep(50);
                //System.out.println("Paying");
                this.paymentPoint.in(customerId);
                System.out.println("Finished shopping" + customerId);
                
                break;
                // mais
            } catch (InterruptedException ex) {
                Logger.getLogger(AECustomer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
