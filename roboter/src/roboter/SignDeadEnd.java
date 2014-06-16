package roboter;

import java.rmi.RemoteException;

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
		drive.right();
		Thread.sleep(2750);
	}
	public SignDeadEnd(NewDriver drive){
		this.drive=drive;
	}

}
