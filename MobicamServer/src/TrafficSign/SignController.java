package TrafficSign;

import java.rmi.RemoteException;
import java.util.LinkedList;

import lejos.hardware.lcd.LCD;
import roboter.Connection;
import roboter.NewDriver;

public class SignController {
	
	private LinkedList<SignForTraffic> sign; 
	private Connection connect;
	
	
	public SignController(NewDriver drive){
		LinkedList<SignForTraffic> schilder = new LinkedList<>();
		schilder.add(new SignDeadEnd(drive));
		schilder.add(new SignPlay(drive));
		schilder.add(new SignStop(drive));
		schilder.add(new SignRight(drive));
		schilder.add(new SignStandardSpeed(drive));
		schilder.add(new SignOnlyForward(drive));
		connect= new Connection(); 
	}

	
	public void chooseSign() throws RemoteException, InterruptedException {
		String name = "";
		while(name.isEmpty()){
			name = this.connect.sendMessage("takepicture");
		}
		if(name.equals("Error")){
			LCD.drawString("Fehler!", 0, 1);
		}

		for (SignForTraffic signForTraffic : sign) {
			if (signForTraffic.getClass().getSimpleName().equals(name)) {
				signForTraffic.signDrive();
			}
		}

	}
        
        public LinkedList<SignForTraffic> getSignList(){
            return this.sign;
        }
}
