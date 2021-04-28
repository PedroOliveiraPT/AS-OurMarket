/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Communication.CServer;
import java.awt.Color;
import java.awt.Label;
import java.util.Arrays;
import javax.swing.JTextField;
/**
 *
 * @author Luís
 */
public class GUI_Manager extends Thread{
    
    private Label[] costumersLabels;
    private JTextField managerLabel;
    private JTextField cashierLabel;
    private CServer cServer;
        
    public GUI_Manager(CServer cServer, Label[] costumersLabels, JTextField managerlLabel, JTextField cashLabel) {
        this.costumersLabels = costumersLabels;
        this.managerLabel = managerlLabel;
        this.cashierLabel = cashLabel;
        this.cServer = cServer;
        
        for (java.awt.Label i : costumersLabels){
            i.setText("Costumer--");
            i.setBackground(Color.WHITE);
        }
    }
    
    @Override
    public void run() {
        while(true){
            String[] msg = cServer.get().split("#"); // thread blocks here
            System.out.println("Received: " + Arrays.toString(msg));
            if (msg[0].equalsIgnoreCase("manager")){
                switch (msg[1].toLowerCase()){
                    case "idle":
                        managerLabel.setText("idle");
                        break;
                    case "outsidehall":
                        managerLabel.setText("outsidehall");
                        break;
                    case "entrancehall":
                        managerLabel.setText("entrancehall");
                        break;
                    default:
                        System.err.print("Error at Manager Labeling");
                        break;
                }
            } else if (msg[0].equalsIgnoreCase("cashier")){
                switch (msg[1].toLowerCase()){
                    case "idle":
                        cashierLabel.setText("idle");
                        break;
                    case "paymentbox":
                        cashierLabel.setText("paymentbox");
                        break;
                    default:
                        System.err.print("Error at Manager Labeling");
                        break;
                }
            } else {
                switch (msg[1].toLowerCase()){
                    case "idle":
                        costumersLabels[Integer.parseInt(msg[0])].setText("Costumer"+msg[0]);
                        costumersLabels[Integer.parseInt(msg[0])].setBackground(Color.GRAY);
                        break;
                    case "outsidehall":
                        costumersLabels[Integer.parseInt(msg[0])].setBackground(Color.YELLOW);
                        break;
                    case "entrancehall":
                        costumersLabels[Integer.parseInt(msg[0])].setBackground(Color.RED);
                        break;
                    case "corridorhall":
                        costumersLabels[Integer.parseInt(msg[0])].setBackground(Color.BLUE);
                        break;
                    case "corridor":
                        costumersLabels[Integer.parseInt(msg[0])].setBackground(Color.GREEN);
                        break;
                    case "payhall":
                        costumersLabels[Integer.parseInt(msg[0])].setBackground(Color.PINK);
                        break;
                    case "paypoint":
                        costumersLabels[Integer.parseInt(msg[0])].setBackground(Color.WHITE);
                        break;
                    default:
                        System.err.print("Error at Costumer Labeling");
                        break;
                }
            }

        }
    }
    
}
