package TrafficSign;

import java.rmi.RemoteException;

import lejos.remote.ev3.RMIRegulatedMotor;
import roboter.NewDriver;

public class TrafficSign {
	
	
	private int speed;
	private NewDriver drive;
	
	
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	
	
	public  TrafficSign(NewDriver drive){
		this.drive=drive;
		
		
		}
	public void choiceSign(int signID) throws RemoteException, InterruptedException{
		if(signID ==0){       // stopSign
			stopSign();	
		}
		if(signID ==1){       //playSign
			playSign();	
		}
		if(signID ==2){       //StandartSpeed
			standardSpeed();	
		}
		if(signID ==3){     //RightSign  
			rightSign();	
		}
		if(signID ==4){     //DeadEnd  
			deadEnd();	
		}
		
	}
	public void stopSign() throws RemoteException, InterruptedException{
		drive.stop();
		Thread.sleep(8000);
		drive.forward();
		Thread.sleep(1000);
	}
	
	public void playSign() throws InterruptedException{
		drive.setSpeed(35);
		Thread.sleep(1000);
	}
	public void standardSpeed() throws InterruptedException{
		drive.setSpeed(75);
		Thread.sleep(1000);
	}
	public void rightSign() throws InterruptedException, RemoteException{
		drive.forward();
		Thread.sleep(1000);
	}
	public void deadEnd() throws RemoteException, InterruptedException{
		drive.setSpeed(75);
		drive.right();
		Thread.sleep(2750);
	}

}
