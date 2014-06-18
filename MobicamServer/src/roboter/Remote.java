package roboter;

import TrafficSign.TrafficSign;
import java.io.*;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.hardware.lcd.LCD;
import lejos.hardware.port.Port;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;

public class Remote {
	private DataOutputStream output;
	private Calibrate cali = new Calibrate();
	private NewDriver drive;
	private RemoteEV3 EV21;
	private TrafficSign sign;
	
	
	
	public boolean remote() {
		try {

			EV21 = new RemoteEV3("10.0.1.1");
			LCD.refresh();
			LCD.drawString("hat geklappt!", 0, 3);
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

	public void  testDrive() throws RemoteException, InterruptedException{
		
		
		 
		Port motorPortA = EV21.getPort("A");
		Port motorPortD = EV21.getPort("D");
	
		RMIRegulatedMotor rightMotor = EV21.createRegulatedMotor("D", 'L');
		RMIRegulatedMotor leftMotor =  EV21.createRegulatedMotor("A", 'L');
		//drive =new NewDriver(cali ,rightMotor,leftMotor);
		drive.drive();
		
	}

}
