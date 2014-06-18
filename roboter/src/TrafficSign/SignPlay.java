package TrafficSign;

import java.rmi.RemoteException;
import roboter.NewDriver;

public class SignPlay extends SignForTraffic {

	private NewDriver drive;
	@Override
	public boolean signImage() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void signDrive() throws RemoteException, InterruptedException {
		drive.setSpeed(35);
		Thread.sleep(1000);
	}
	public SignPlay(NewDriver drive){
		this.drive=drive;
	}

}
