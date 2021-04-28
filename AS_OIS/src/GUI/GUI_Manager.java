/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JTextField;
import javax.swing.JTextPane;

/**
 *
 * @author Luís
 */
public class GUI_Manager {
    private final ReentrantLock rl = new ReentrantLock( true );
    
    JTextField outsideHall;
    JTextPane[] entranceHallJTextPanes;
    JTextPane[] corridorHallJTextPanes;
    JTextPane[] corridorJTextPanes;
    JTextPane[] payHallJTextPanes;
    JTextPane[] payJTextPanes;
    
    private Queue<Integer>[] corridorQueue;
    private Queue<Integer>[] paymentHallQueue;

    public GUI_Manager(JTextField OH_all, JTextPane[] entranceHallJTextPanes, JTextPane[] corridorHallJTextPanes, JTextPane[] corridorJTextPanes, JTextPane[] payHallJTextPanes, JTextPane[] payJTextPanes) {
        this.outsideHall = OH_all;
        this.entranceHallJTextPanes = entranceHallJTextPanes;
        this.corridorHallJTextPanes = corridorHallJTextPanes;
        this.corridorJTextPanes = corridorJTextPanes;
        this.payHallJTextPanes = payHallJTextPanes;
        this.payJTextPanes = payJTextPanes;
        
        this.corridorQueue = new LinkedList[3];
        this.paymentHallQueue = new LinkedList[3];
        for (int i = 0; i < 3; i++){
            this.corridorQueue[i] = new LinkedList<>();
            this.paymentHallQueue[i] = new LinkedList<>();
        }
    }
    
    public void enterOutsideHall(){
        try{
            rl.lock();
            String val;
            val = this.outsideHall.getText().split(" ")[0];
            this.outsideHall.setText((Integer.parseInt(val)+1) + " Costumers Waiting");
        } finally{
            rl.unlock();
        }
    }
    
    public void enterEntranceHall(int costumerId){
        try{
            rl.lock();
            String[] dict;
            dict = this.outsideHall.getText().split(" ");
            this.outsideHall.setText((Integer.parseInt(dict[0])-1) + " Costumers Waiting");

            String val;
            for (int i=0; i<entranceHallJTextPanes.length; i++){
                val = entranceHallJTextPanes[i].getText();
                if (val.equalsIgnoreCase("empty")){
                    entranceHallJTextPanes[i].setText(costumerId + "");
                    break;
                }
            }
        } finally{
            rl.unlock();
        }
    }
    
    public void enterCorridorHall(int costumerId, int corridor){
        try{
            rl.lock();
            String val;
            System.out.println("> A ENTRAR EM CORRIDOR NORMAL: "+ costumerId + " AT CORR=" + corridor);
            
            for (int i=0; i<entranceHallJTextPanes.length; i++){
                val = entranceHallJTextPanes[i].getText();
                if (val.equalsIgnoreCase(costumerId + "")){
                    entranceHallJTextPanes[i].setText("empty");
                    break;
                }
            }

            for (int i=(corridor)*3; i<= ((corridor + 1)*3)-1; i++){
                val = corridorHallJTextPanes[i].getText();
                if (val.equalsIgnoreCase("empty")){
                    corridorHallJTextPanes[i].setText(costumerId + "");
                    break;
                }
            }
        } finally {
            rl.unlock();
        }
    }
    
    public void enterCorridorShop(int costumerId, int corridor){
        try{
            rl.lock();
            String val;
            System.out.println("> A ENTRAR EM CORRIDOR SHOP: "+ costumerId + " AT CORR=" + corridor);
            corridorQueue[corridor].add(costumerId);

            for (int i=(corridor)*3; i<= ((corridor + 1)*3)-1; i++){
                val = corridorHallJTextPanes[i].getText();
                if (val.equalsIgnoreCase(costumerId + "")){
                    corridorHallJTextPanes[i].setText("empty");
                    break;
                }
            }
            boolean found = false;
            for (int i=(corridor)*2; i<= ((corridor + 1)*2)-1; i++){
                val = corridorJTextPanes[i].getText();
                if (val.equalsIgnoreCase("empty")){
                    corridorJTextPanes[i].setText(costumerId + "");
                    System.out.println("> CUSTUMER " + costumerId + " AT I="+i);
                    found = true;
                    break;
                }
            }
            
            if (!found){
                int leavingC = corridorQueue[corridor].poll();
                for (int i=(corridor)*2; i<= ((corridor + 1)*2)-1; i++){
                val = corridorJTextPanes[i].getText();
                if (val.equalsIgnoreCase(leavingC+"")){
                        corridorJTextPanes[i].setText(costumerId + "");
                        System.out.println("> CUSTUMER " + costumerId + " AT I="+i);
                        break;
                    }
                }
            }
        } finally{
            rl.unlock();
        }
    }
    
    public void enterPaymentHall(int costumerId, int corridor){
        try{
            rl.lock();
            String val;
            System.out.println("> A ENTRAR EM PAYMENTE HALL: "+ costumerId + " FROM " + corridor);
            paymentHallQueue[corridor].add(costumerId);
            for (int i=(corridor)*2; i<= ((corridor + 1)*2)-1; i++){
                val = corridorJTextPanes[i].getText();
                if (val.equalsIgnoreCase(costumerId+"")){
                    corridorJTextPanes[i].setText("empty");
                    break;
                }
            }
            
            boolean found = false;
            for (int i=0; i< payHallJTextPanes.length; i++){
                val = payHallJTextPanes[i].getText();
                if (val.equalsIgnoreCase("empty")){
                    payHallJTextPanes[i].setText(costumerId +"");
                    found = true;
                    break;
                }
            }
            
            if (!found){
                int leavingC = paymentHallQueue[corridor].poll();
                for (int i=0; i< payHallJTextPanes.length; i++){
                    val = payHallJTextPanes[i].getText();
                    if (val.equalsIgnoreCase(leavingC+"")){
                        payHallJTextPanes[i].setText(costumerId +"");
                        break;
                    }
                }
            }
            
        } finally{
            rl.unlock();
        }
    }
    
    public void enterPaymentPoint(int costumerId, int corridor){
        try{
            rl.lock();
            String val;

            for (int i=0; i< payHallJTextPanes.length; i++){
                val = payHallJTextPanes[i].getText();
                if (val.equalsIgnoreCase(costumerId+"")){
                    payHallJTextPanes[i].setText("empty");
                    break;
                }
            }

            for (int i=0; i < payJTextPanes.length; i++){
                val = payJTextPanes[i].getText();
                if (val.equalsIgnoreCase("empty")){
                    payJTextPanes[i].setText(costumerId+"");
                }
                else {
                    System.err.println("ERRO AT PAYMENT");
                }
            }
        } finally{
            rl.unlock();
        }
    }
    
    public void leaveStore(int costumerId){
        try{
            rl.lock();
            String val;
            for (int i=0; i < payJTextPanes.length; i++){
                val = payJTextPanes[i].getText();
                if (val.equalsIgnoreCase(costumerId+"")){
                    payJTextPanes[i].setText("empty");
                }
            }
        } finally {
            rl.unlock();
        }
       
    }
}
