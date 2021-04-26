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
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Criar cliente para enviar comandos para o OIS.
 * @author omp
 */
public class CClient extends Thread {
    private int portNumber;
    
    private Socket socket;
    
    private BufferedReader in;
    private BufferedReader stdIn;
    private PrintWriter out;
    
    public CClient(int portNumber) {
        this.portNumber = portNumber;
    }
    
    public CClient(int portNumber, Socket socket) {
        this.portNumber = portNumber;
        this.socket = socket;
    }
    
    
    public void connect(){
        String hostName = "localhost";
        
        System.out.println("Initiating OCC Side Client...");
        
        boolean connected = false;
        while(!connected){
            try {
                this.socket = new Socket(hostName, portNumber);
                out =
                    new PrintWriter(socket.getOutputStream(), true);
                
                connected = true;
                System.out.println("Connection Established!");
                
            } catch (UnknownHostException e) {
                System.err.println("Don't know about host " + hostName);
            } catch (IOException e) {
                System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            }
            try {

                
                Thread.sleep(1500);
                System.out.println("Retrying...");
            } catch (InterruptedException ex) {
                Logger.getLogger(CClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
    }
    
    public void send(String message){
        out.println(message);
    }
    
    @Override
    public void run(){
        String hostName = "localhost";
        
        System.out.println("Initiating OCC Side Client...");
        
        boolean connected = false;
        while(!connected){
            try (
                Socket echoSocket = new Socket(hostName, portNumber);
                PrintWriter out =
                    new PrintWriter(echoSocket.getOutputStream(), true);
                BufferedReader in =
                    new BufferedReader(
                        new InputStreamReader(echoSocket.getInputStream()));
                BufferedReader stdIn =
                    new BufferedReader(
                        new InputStreamReader(System.in))
            ) {
                String userInput;
                while ((userInput = stdIn.readLine()) != null) {
                    out.println(userInput);
                    System.out.println("echo: " + in.readLine());
                }
            } catch (UnknownHostException e) {
                System.err.println("Don't know about host " + hostName);
            } catch (IOException e) {
                System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            }
            try {
                Thread.sleep(1500);
                System.out.println("Retrying...");
            } catch (InterruptedException ex) {
                Logger.getLogger(CClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
}
