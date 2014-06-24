/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobicamserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
    private PrintWriter outputStream;
    private Socket client;
    private SignDetector signDetector;
    private File userdir;

    public TCPConnector() {
        this.picmd = new CommandRaspberryPi();
        this.signDetector = new SignDetector();
        this.userdir = new File("/home/pi/");
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
        //server.start();
        server.work();
    }

    @Override
    public void run() {
        while (true) {
            if (this.client == null) {
                if (this.picmd.connectToEV3()) {
                    System.out.println("USB-Connection ok");
                    this.connect();
                    System.out.println("Connection ready for use.");
                } else {
                    System.out.println("USB-Connection fail");
                    System.exit(1);
                }
            }
            while (true) {
                String answer = null;
                answer = this.getMessage();
                System.out.println(answer);
                if (answer != null) {
                    if (answer.contains("takepicture")) {
                        String signname = this.handlePictureRequest();
                        if (!this.sendMessage(signname)) {
                            System.out.println("ERROR by sending.....");
                        }
                    }
                }

            }

        }
    }

    public void work() {
        while (true) {
            if (this.client == null) {
                if (this.picmd.connectToEV3()) {
                    System.out.println("USB-Connection ok");
                    this.connect();
                    System.out.println("Connection ready for use.");
                } else {
                    System.out.println("USB-Connection fail");
                    System.exit(1);
                }
            }
            while (true) {
                System.out.println("wating for Message");
                String answer = null;
                answer = this.getMessage();

                if (answer != null) {
                    System.out.println(answer);
                    if (answer.contains("takepicture")) {
                        String signname = this.handlePictureRequest();
                        if (!this.sendMessage(signname)) {
                            System.out.println("ERROR by sending.....");
                        }
                    }
                }

            }

        }
    }

    public String handlePictureRequest() {
        System.out.println("handlePictureRequest");
        File picture = new File("image.jpg");
        this.makePicture(picture);
        /**for (int i = 0; i <= 5; i++) {
            System.out.println("try picture : " + i);
            if (this.makePicture(picture)) {
                break;
            }
        }
        **/
        String signname = this.signDetector.detektTrafficSign(picture);
        System.out.println(signname);
        if (signname.contentEquals("new picture please")) {
            return this.handlePictureRequest();
        }
        return signname;
    }

    public boolean makePicture(File picture) {
        this.picmd.makeAPicture("image");
        if (picture.exists()) {
            return true;
        }
        return false;
    }

    public boolean sendMessage(String message) {
        System.out.println(message);
        this.outputStream.println(message);
        this.outputStream.flush();
        String answer = this.getMessage();
        if (answer.equalsIgnoreCase("true")) {
            return true;
        }
        return false;
    }

    public String getMessage() {
        String answer = null;
        try {
            System.out.println("Waitng for Message, bussy");
            answer = this.inputStream.readLine();
            if (answer.contains("EV3")) {
                return answer;
            }
            if (answer.contains("true")) {
                return answer;
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return "ERROR";
        }
        return null;
    }

    public void connect() {
        try {
            System.out.println("waiting for Client");
            System.out.println("");
            this.client = this.tcpServer.accept();
            this.client.setKeepAlive(true);
            System.out.println("open inputstream");
            this.inputStream = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
            System.out.println("open outputstream");
            this.outputStream = new PrintWriter(this.client.getOutputStream(), true);
            System.out.println("Streams open");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public boolean checkConnection() {
        if (this.client.isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}
