/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SACorridor;

import SAPaymentHall.SAPaymentHall;

/**
 *
 * @author pedro
 */
public interface ICorridor_Customer {
    public void call();
    public void in(int customerId, SAPaymentHall ph );
    public boolean checkFull();
    
}
