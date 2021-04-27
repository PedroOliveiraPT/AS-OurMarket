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
        
        for (int i=0; i<entranceHallJTextPanes.length; i++){
            dict = entranceHallJTextPanes[i].getText().split(":");
            if (dict[1].equalsIgnoreCase("empty")){
                entranceHallJTextPanes[i].setText("Pos0" + (i + 1) + ":" + costumerId);
                break;
            }
        }
    }
    
    public void enterCorridorHall(int costumerId, int corridor){
        
        String[] dict;
        for (int i=0; i<entranceHallJTextPanes.length; i++){
            dict = entranceHallJTextPanes[i].getText().split(":");
            if (dict[1].equalsIgnoreCase(costumerId + "")){
                entranceHallJTextPanes[i].setText("Pos0" + (i + 1) + ":" + "empty");
                break;
            }
        }
        
        for (int i=(corridor)*3; i< ((corridor + 1)*3)-1; i++){
            System.out.println("i: " +  i + "; " + corridor);
            dict = corridorHallJTextPanes[i].getText().split(":");
            if (dict[1].equalsIgnoreCase("empty")){
                corridorHallJTextPanes[i].setText("Pos0" + (i + 1) + ":" + costumerId);
                break;
            }
        }
    }
}
