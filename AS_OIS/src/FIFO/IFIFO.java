/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FIFO;

/**
 *
 * @author omp
 */
public interface IFIFO {
    public int in( int customerId );
    public void out();
    public boolean full();

    public int getCount();
}
