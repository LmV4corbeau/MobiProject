/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package image;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author corbeau
 */
public class TCPConnetion {

    private int port = 1337;
    private DataOutputStream output;
    private BufferedReader input;
    private ServerSocket socket;

    public TCPConnetion(){
        try {
            this.socket = new ServerSocket(1337);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
 public void waitForInput() throws Exception
      {
         String clientSentence;
         String capitalizedSentence = "Server(Pi) Nachricht bekommen";
         while(true)
         {
            Socket connectionSocket = this.socket.accept();
            this.input = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            clientSentence = this.input.readLine();
            System.out.println("Received: " + clientSentence);
            capitalizedSentence = clientSentence.toUpperCase() + '\n';
            outToClient.writeBytes(capitalizedSentence);
         }
      }

}
