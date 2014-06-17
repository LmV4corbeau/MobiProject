package roboter;

import java.io.*;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.hardware.Audio;
import lejos.hardware.Brick;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.MindsensorsGlideWheelMRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RMIRemoteEV3;
import lejos.remote.ev3.RemoteEV3;
import lejos.remote.nxt.BTConnector;
import lejos.robotics.RegulatedMotor;

public class Remote {

    private DataOutputStream output;
    private Calibrate cali = new Calibrate();
    private NewDriver drive;
    private RemoteEV3 EV21;
    private TrafficSign sign;

    public boolean remote(String ipAdress) {
        try {

            EV21 = new RemoteEV3(ipAdress);
            LCD.refresh();
            LCD.drawString("hat geklappt!", 0, 3);
            System.out.println("hat geklappt!");
            EV21.getAudio().systemSound(0);
            return true;
        } catch (RemoteException e) {
            LCD.clear();
            LCD.drawString(e.getLocalizedMessage(), 0, 1);
            LCD.refresh();
            return false;

        } catch (MalformedURLException e) {
            LCD.clear();
            LCD.drawString(e.getLocalizedMessage(), 0, 1);
            LCD.refresh();
            return false;
        } catch (NotBoundException e) {
            LCD.clear();
            LCD.drawString(e.getLocalizedMessage(), 0, 1);
            LCD.refresh();
            return false;

        }

    }

    public void testDrive() throws RemoteException, InterruptedException {

        Port motorPortA = EV21.getPort("A");
        Port motorPortD = EV21.getPort("D");

        RMIRegulatedMotor rightMotor = EV21.createRegulatedMotor("D", 'L');
        RMIRegulatedMotor leftMotor = EV21.createRegulatedMotor("A", 'L');
        drive = new NewDriver(cali, rightMotor, leftMotor);
        drive.drive();

    }

}
