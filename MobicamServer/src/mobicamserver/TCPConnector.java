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
import java.util.logging.Level;
import java.util.logging.Logger;

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
        this.userdir = new File("/home/pi/");
    }

    public void init() {
        this.signDetector = new SignDetector();
        this.picmd = new CommandRaspberryPi();
        try {
            System.out.println("opening ServerSocket");
            this.tcpServer = new ServerSocket(6789);
            System.out.println("Running on Port 6789");
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            TCPConnector server = new TCPConnector();
            server.work();
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void work() throws InterruptedException {
        this.init();
        while (true) {
            try {
                if (this.client == null) {
                    if (this.picmd.connectToEV3()) {
                        System.out.println("USB-Connection ok");
                        this.connect();
                        System.out.println("Connection ready for use.");
                    } else {
                        System.out.println("USB-Connection fail");
                    }
                    this.sendMessage("go");
                    while (true) {
                        System.out.println("wating for Message");
                        String answer = null;
                        answer = this.getMessage();
                        if (answer != null) {
                            System.out.println(answer);
                            if (answer.contains("EV3Error")) {
                                System.out.println("EV3 Fehler");
                                System.out.println("");
                                System.out.println(answer);
                                System.out.println("");
                                System.out.println("Neue Verbindung beginnnt");
                                try {
                                    this.client.close();
                                    this.client = null;
                                } catch (IOException ex) {
                                    System.out.println(ex.getMessage());
                                } finally {
                                    break;
                                }
                            }
                            if (answer.contains("takepicture")) {
                                String signname = this.handlePictureRequest();
                                if (!this.sendMessage(signname)) {
                                    System.out.println("ERROR by sending.....");
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public String handlePictureRequest() throws InterruptedException {
        System.out.println("handlePictureRequest");
        File picture = new File(this.userdir, "image.jpg");
        if (this.makePicture(picture)) {
            String signname = this.signDetector.detektTrafficSign(picture);
            System.out.println(signname);
            if (signname.contains("new picture please")) {
                picture.renameTo(new File(this.userdir, "image.jpg.old"));
                picture = null;
                sleep(10000);
                return this.handlePictureRequest();
            }
            picture.renameTo(new File(this.userdir, "image.jpg.old"));
            picture = null;
            return signname;
        } else {
            System.out.println("Error while taking Picture");
            picture.renameTo(new File(this.userdir, "image.jpg.old"));
            picture = null;
            return null;
        }
    }

    public boolean makePicture(File picture) {
        this.picmd.makeAPicture(picture);
        if (picture.exists()) {
            return true;
        }
        return false;
    }

    public boolean sendMessage(String message) {
        System.out.println(message);
        this.outputStream.println(message);
        this.outputStream.flush();
        return true;
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
