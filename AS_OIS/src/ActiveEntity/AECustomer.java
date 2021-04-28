package ActiveEntity;


import Communication.CClient;
import GUI.GUI_Manager;
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
    
    // Customer status
    private StatusCustomer stCustomer;
    
    // Customer corridor hall index;
    private int corr_index;
    
//    GUI MANAGER
    private GUI_Manager gUI_Manager;
    
    private CClient cClient;
    
    public AECustomer( int customerId, IIdle_Customer idle, IOutsideHall_Customer outsideHall, 
                        IEntranceHall_Customer entranceHall, ICorridorHall_Customer[] corridorHall,
                        ICorridor_Customer[] corridorShop, IPaymentHall_Customer paymentHall, IPaymentPoint_Customer paymentPoint,
                        GUI_Manager gUI_Manager, CClient cClient) {
        this.customerId = customerId; 
        this.idle = idle;
        this.outsideHall = outsideHall;
        this.entranceHall = entranceHall;
        this.corridorHall = corridorHall;
        this.corridorShop = corridorShop;
        this.paymentHall = paymentHall;
        this.paymentPoint = paymentPoint;
        this.gUI_Manager = gUI_Manager;

        this.cClient = cClient;

        this.stCustomer = StatusCustomer.IDLE;
        this.corr_index = -1;

    }
    @Override
    public void run() {
        StatusCustomer temp;
        while ( true ) {
            // thread avança para Idle
            cClient.send(this.customerId + "#idle");
            temp = idle.idle(customerId );
            this.stCustomer = (temp==null)? stCustomer:temp;
            if (this.stCustomer == StatusCustomer.IDLE) {
                
                this.stCustomer = StatusCustomer.OUTSIDE;
                
            }
            // se simulação activa (não suspend, não stop, não end), thread avança para o outsideHall
            
            if (this.stCustomer == StatusCustomer.OUTSIDE) {
                System.out.println(this.customerId + " entering Outside Hall");

                gUI_Manager.enterOutsideHall();
                cClient.send(this.customerId + "#outsidehall");
                outsideHall.in( customerId );
                this.stCustomer = StatusCustomer.ENTRANCE;
            }
            
            if (this.stCustomer == StatusCustomer.ENTRANCE) {

                System.out.println(this.customerId + " entering Entrance Hall");

                gUI_Manager.enterEntranceHall(customerId);
                cClient.send(this.customerId + "#entrancehall");
                entranceHall.in(customerId);
                this.stCustomer = StatusCustomer.CORRIDORHALL;
            }
            
            if (this.corr_index < 0){
                for (int curr=0; curr < corridorHall.length; curr++){
                    if (!corridorHall[curr].checkFull()){
                        corr_index = curr;
                        break;
                    }
                }
            }

            
            
            if (this.stCustomer == StatusCustomer.CORRIDORHALL) {
                System.out.println(this.customerId + " left entrance hall");
                System.out.println(this.customerId + " entering corridor hall num " + this.corr_index);
                gUI_Manager.enterCorridorHall(customerId, this.corr_index);      
                cClient.send(this.customerId + "#corridorhall");
                if (corridorShop[this.corr_index].checkFull()){
                    System.out.println(this.customerId + "will wait");
                    corridorHall[this.corr_index].in(customerId);
                }
                this.stCustomer = StatusCustomer.CORRIDOR;
            }
            
            if (this.stCustomer == StatusCustomer.CORRIDOR){
                gUI_Manager.enterCorridorShop(customerId, this.corr_index);

                System.out.println(this.customerId + " shopping INICIO num " + this.corr_index);
                cClient.send(this.customerId + "#corridor");
                corridorShop[this.corr_index].in(customerId, (SAPaymentHall) this.paymentHall);
                this.stCustomer = StatusCustomer.PAYMENTHALL;
            }
            
            if (this.stCustomer == StatusCustomer.PAYMENTHALL) {
                System.out.println(this.customerId + " paymenting hall");
                gUI_Manager.enterPaymentHall(customerId, this.corr_index);
                cClient.send(this.customerId + "#payhall");
                this.paymentHall.in(customerId);

                this.stCustomer = StatusCustomer.PAYMENTPOINT;
            }
            
            if (this.stCustomer == StatusCustomer.PAYMENTPOINT) {
                System.out.println("Paying" + customerId);
                gUI_Manager.enterPaymentPoint(customerId, this.corr_index);
                cClient.send(this.customerId + "#paypoint");
                this.paymentPoint.in(customerId);
                System.out.println("Finished shopping" + customerId);

                gUI_Manager.leaveStore(customerId);
                cClient.send(this.customerId + "#idle");
                this.stCustomer = StatusCustomer.IDLE;
                this.idle.setCustDone(customerId);
            }

        }
    }
}
