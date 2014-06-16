package roboter;

import java.rmi.RemoteException;

public class SignStandardSpeed extends SignForTraffic {

	private NewDriver drive;
	@Override
	public boolean signImage() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void signDrive() throws RemoteException, InterruptedException {
		drive.setSpeed(75);
		Thread.sleep(1000);
	}
	public SignStandardSpeed(NewDriver drive){
		this.drive=drive;
	}

}
