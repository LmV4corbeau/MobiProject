package TrafficSign;

import java.rmi.RemoteException;
import roboter.NewDriver;

public class SignDeadEnd extends SignForTraffic {

	private NewDriver drive;
	@Override
	public boolean signImage() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void signDrive() throws RemoteException, InterruptedException {
		drive.setSpeed(75);
		drive.left();
		Thread.sleep(3050);
	}
	public SignDeadEnd(NewDriver drive){
		this.drive=drive;
	}

}
