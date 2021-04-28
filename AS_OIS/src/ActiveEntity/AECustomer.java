package ActiveEntity;


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
    
//    GUI MANAGER
    private GUI_Manager gUI_Manager;
    
    public AECustomer( int customerId, IIdle_Customer idle, IOutsideHall_Customer outsideHall, 
                        IEntranceHall_Customer entranceHall, ICorridorHall_Customer[] corridorHall,
                        ICorridor_Customer[] corridorShop, IPaymentHall_Customer paymentHall, IPaymentPoint_Customer paymentPoint,
                        GUI_Manager gUI_Manager) {
        this.customerId = customerId; 
        this.idle = idle;
        this.outsideHall = outsideHall;
        this.entranceHall = entranceHall;
        this.corridorHall = corridorHall;
        this.corridorShop = corridorShop;
        this.paymentHall = paymentHall;
        this.paymentPoint = paymentPoint;
        this.gUI_Manager = gUI_Manager;
    }
    @Override
    public void run() {
        while ( true ) {
            // thread avança para Idle
            idle.idle(customerId );
            // se simulação activa (não suspend, não stop, não end), thread avança para o outsideHall
            System.out.println(this.customerId + " entering Outside Hall");
            try {
                TimeUnit.SECONDS.sleep(11);
            } catch (InterruptedException ex) {
                Logger.getLogger(AECustomer.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            gUI_Manager.enterOutsideHall(); // so sabemos k entrou quando sai (old comment)
            outsideHall.in( customerId );
            
            try {
                TimeUnit.SECONDS.sleep(0);
            } catch (InterruptedException ex) {
                Logger.getLogger(AECustomer.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(this.customerId + " entering Entrance Hall");
            
            gUI_Manager.enterEntranceHall(customerId);  // so sabemos k entrou quando entra
            entranceHall.in(customerId);
            
            try {
                TimeUnit.SECONDS.sleep(0);
            } catch (InterruptedException ex) {
                Logger.getLogger(AECustomer.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            System.out.println(this.customerId + " left entrance hall");
            int curr_index=0;
            for (int curr=0; curr < corridorHall.length; curr++){
                if (!corridorHall[curr].checkFull()){
                    curr_index = curr;
                    break;
                }
            }
                
            System.out.println(this.customerId + " entering corridor hall num " + curr_index);
            gUI_Manager.enterCorridorHall(customerId, curr_index);      
//            try {
//                TimeUnit.SECONDS.sleep(5);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(AECustomer.class.getName()).log(Level.SEVERE, null, ex);
//            }
            if (corridorShop[curr_index].checkFull()){
                System.out.println(this.customerId + "will wait");
                corridorHall[curr_index].in(customerId);
            }
                
            
            gUI_Manager.enterCorridorShop(customerId, curr_index);

            System.out.println(this.customerId + " shopping INICIO num " + curr_index);
            corridorShop[curr_index].in(customerId, (SAPaymentHall) this.paymentHall);
            System.out.println(this.customerId + " shopping ");
            //corridorShop[curr_index].call();
            
            System.out.println(this.customerId + " paymenting hall");
            gUI_Manager.enterPaymentHall(customerId, curr_index);
            this.paymentHall.in(customerId);
            try {
                TimeUnit.SECONDS.sleep(0);
            } catch (InterruptedException ex) {
                Logger.getLogger(AECustomer.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Paying" + customerId);
            gUI_Manager.enterPaymentPoint(customerId, curr_index);
            this.paymentPoint.in(customerId);
            try {
                TimeUnit.SECONDS.sleep(0);
            } catch (InterruptedException ex) {
                Logger.getLogger(AECustomer.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Finished shopping" + customerId);

            gUI_Manager.leaveStore(customerId);
            this.idle.idle(customerId);
            break;
        }
    }
}
