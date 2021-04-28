/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Criar Server para receber info do OIS.
 * @author omp
 */
public class CServer{
    private int portNumber;
    
    private Socket clientSocket;
    
    private BufferedReader in;

    public CServer(int portNumber) {
        this.portNumber = portNumber;
    }
    
    public String get(){
        try {
            String a = in.readLine();
            return a;
        } catch (IOException ex) {
            Logger.getLogger(CServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void connect(){
        
        System.out.println("Initiating OCC Side Server...");
        try{
            ServerSocket serverSocket =
                new ServerSocket(this.portNumber);
            this.clientSocket = serverSocket.accept();     
                 
            this.in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("Connection Accepted!");
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
