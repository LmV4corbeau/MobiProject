package roboter;

import java.rmi.RemoteException;

public class SignRight extends SignForTraffic {

	private NewDriver drive;
	@Override
	public boolean signImage() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void signDrive() throws RemoteException, InterruptedException {
		drive.forward();
		Thread.sleep(1000);
	}
	public SignRight(NewDriver drive){
		this.drive=drive;
	}

}
