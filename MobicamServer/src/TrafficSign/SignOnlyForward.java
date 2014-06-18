package TrafficSign;

import java.rmi.RemoteException;
import roboter.NewDriver;

public class SignOnlyForward extends SignForTraffic {

	private NewDriver drive;
	@Override
	public boolean signImage() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void signDrive() throws RemoteException, InterruptedException {
		drive.forward();
		Thread.sleep(5000);

	}

	public SignOnlyForward(NewDriver drive){
		this.drive=drive;
	}
}
