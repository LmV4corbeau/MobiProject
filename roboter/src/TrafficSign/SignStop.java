package TrafficSign;

import java.rmi.RemoteException;
import roboter.NewDriver;

public class SignStop extends SignForTraffic {
	private NewDriver drive;

	@Override
	public boolean signImage() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	@Override
	public void signDrive() throws RemoteException, InterruptedException {
		drive.stop();
		Thread.sleep(8000);
		drive.forward();
		Thread.sleep(1000);
		
	}
	public SignStop(NewDriver drive){
		this.drive=drive;
	}
	
	

}
