/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobicamserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author corbeau
 */
public class TCPConnector extends Thread {
    
    private CommandRaspberryPi picmd;
    private ServerSocket tcpServer;
    private BufferedReader inputStream;
    private DataOutputStream outputStream;
    private Socket client;
    private SignDetector signDetector;

    public TCPConnector() {
        this.picmd = new CommandRaspberryPi();
        this.signDetector = new SignDetector();
        try {
            System.out.println("opening ServerSocket");
            this.tcpServer = new ServerSocket(6789);
            System.out.println("Running on Port 6789");
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }

    }
    
    public static void main(String[] args) {
        TCPConnector server = new TCPConnector();
        server.run();
    }

    @Override
    public void run() {
        while (true) {
            if (this.client == null) {
                this.connect();
                System.out.println("Connection ready for use.");
            }
            String answer = "";
            while (answer.isEmpty()) {
                answer = this.getMessage();
            }
            if (answer.contains("takepicture")) {
                //TODO Photo machen
                this.picmd.makeAPicture("image");
                File picture = new File(this.picmd.getpictureFolder(), "image.jpg");
                if(!picture.exists()){
                	this.picmd.makeAPicture("image");
                	picture = new File(this.picmd.getpictureFolder(), "image.jpg");
                }
                if(!picture.exists()){
                	this.picmd.makeAPicture("image");
                	picture = new File(this.picmd.getpictureFolder(), "image.jpg");
                }
                String Sign = this.signDetector.detektTrafficSigne(picture);
                if(!this.sendMessage(Sign)){
                    System.out.println("ERROR by sending.....");
                    System.exit(1);
                }
            }
        }
    }

    public boolean sendMessage(String message) {
        try {
            this.outputStream.writeBytes(message);
            this.outputStream.flush();
            String answer = this.getMessage();
            if (answer.equalsIgnoreCase("true")) {
                return true;
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public String getMessage() {
        String answer = "";
        while (answer.isEmpty()) {
            try {
                answer = this.inputStream.readLine();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return answer;
    }

    public void connect() {
        try {
            while (this.client == null) {
                try {
                    System.out.println("waiting for Client");
                    this.client = this.tcpServer.accept();

                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            System.out.println("open inputstream");
            this.inputStream = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
            System.out.println("open outputstream");
            this.outputStream = new DataOutputStream(this.client.getOutputStream());
            System.out.println("Streams open");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
