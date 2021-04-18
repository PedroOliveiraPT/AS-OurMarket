package ActiveEntity;


import SAEntranceHall.IEntranceHall_Customer;
import SAIdle.IIdle_Customer;
import SAOutsideHall.IOutsideHall_Customer;
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
    
    public AECustomer( int customerId, IIdle_Customer idle, IOutsideHall_Customer outsideHall, IEntranceHall_Customer entranceHall  /* mais args */ ) {
        this.customerId = customerId; 
        this.idle = idle;
        this.outsideHall = outsideHall;
        this.entranceHall = entranceHall;
    }
    @Override
    public void run() {
        while ( true ) {
            // thread avança para Idle
            // idle.idle(customerId );
            // se simulação activa (não suspend, não stop, não end), thread avança para o outsideHall
            System.out.println(this.customerId + " entering Outside Hall");
            outsideHall.in( customerId );
            System.out.println(this.customerId + " entering Entrance Hall");
            entranceHall.in(customerId);
            System.out.println(this.customerId + " left entrance hall");
            break;
            // mais
        }
    }
}
