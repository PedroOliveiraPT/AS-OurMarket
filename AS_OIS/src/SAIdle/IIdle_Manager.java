/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SAIdle;

import ActiveEntity.StatusManager;

/**
 *
 * @author omp
 */
public interface IIdle_Manager {
    public StatusManager idle();

    public void managerIncrementCounter();
    
}
