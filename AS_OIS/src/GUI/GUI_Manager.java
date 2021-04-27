/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.util.Arrays;
import javax.swing.JTextField;
import javax.swing.JTextPane;

/**
 *
 * @author Luís
 */
public class GUI_Manager {
    JTextField outsideHall;
    JTextPane[] entranceHallJTextPanes;
    JTextPane[] corridorHallJTextPanes;
    JTextPane[] corridorJTextPanes;
    JTextPane[] payHallJTextPanes;
    JTextPane[] payJTextPanes;

    public GUI_Manager(JTextField OH_all, JTextPane[] entranceHallJTextPanes, JTextPane[] corridorHallJTextPanes, JTextPane[] corridorJTextPanes, JTextPane[] payHallJTextPanes, JTextPane[] payJTextPanes) {
        this.outsideHall = OH_all;
        this.entranceHallJTextPanes = entranceHallJTextPanes;
        this.corridorHallJTextPanes = corridorHallJTextPanes;
        this.corridorJTextPanes = corridorJTextPanes;
        this.payHallJTextPanes = payHallJTextPanes;
        this.payJTextPanes = payJTextPanes;
    }
    
    public void enterOutsideHall(){
        String val;
        val = this.outsideHall.getText().split(" ")[0];
        this.outsideHall.setText((Integer.parseInt(val)+1) + " Costumers Waiting");
    }
    
    public void enterEntranceHall(int costumerId){
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
    }
    
    public void enterCorridorHall(int costumerId, int corridor){
        
        String val;
        for (int i=0; i<entranceHallJTextPanes.length; i++){
            val = entranceHallJTextPanes[i].getText();
            if (val.equalsIgnoreCase(costumerId + "")){
                entranceHallJTextPanes[i].setText("empty");
                break;
            }
        }
        
        for (int i=(corridor)*3; i< ((corridor + 1)*3)-1; i++){
            System.out.println("i: " +  i + "; " + corridor);
            val = corridorHallJTextPanes[i].getText();
            if (val.equalsIgnoreCase("empty")){
                corridorHallJTextPanes[i].setText(costumerId + "");
                break;
            }
        }
    }
    
    public void enterCorridorShop(int costumerId, int corridor){
        
        String val;

        for (int i=(corridor)*3; i< ((corridor + 1)*3)-1; i++){
            val = corridorHallJTextPanes[i].getText();
            if (val.equalsIgnoreCase(costumerId + "")){
                corridorHallJTextPanes[i].setText("empty");
                break;
            }
        }
        
        for (int i=(corridor)*2; i< ((corridor + 1)*2)-1; i++){
            val = corridorJTextPanes[i].getText();
            if (val.equalsIgnoreCase("empty")){
                corridorJTextPanes[i].setText(costumerId + "");
                break;
            }
        }
    }
    
    public void enterPaymentHall(int costumerId, int corridor){
        
        String val;

        for (int i=(corridor)*2; i< ((corridor + 1)*2)-1; i++){
            val = corridorJTextPanes[i].getText();
            if (val.equalsIgnoreCase(costumerId+"")){
                corridorJTextPanes[i].setText("empty");
                break;
            }
        }
        
        for (int i=(corridor)*2; i< ((corridor + 1)*2)-1; i++){
            val = payHallJTextPanes[i].getText();
            if (val.equalsIgnoreCase("empty")){
                payHallJTextPanes[i].setText(costumerId +"");
                break;
            }
        }
    }
    
    public void enterPaymentPoint(int costumerId, int corridor){
        
        String val;

        for (int i=(corridor)*2; i< ((corridor + 1)*2)-1; i++){
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
    }
}
