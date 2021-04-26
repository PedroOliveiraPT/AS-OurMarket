/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.awt.Color;
import java.awt.Label;
/**
 *
 * @author Luís
 */
public class InterfaceManager {
    
    private Label[] costumersLabels;

    public InterfaceManager(Label[] costumersLabels) {
        this.costumersLabels = costumersLabels;
        
        for (java.awt.Label i : costumersLabels){
            i.setText("Costumer--");
            i.setBackground(Color.red);
        }
    }
    
}
