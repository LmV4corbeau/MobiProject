package TrafficSign;

import java.rmi.RemoteException;


public class SignDeadEnd extends SignForTraffic {

	
	@Override
	public boolean signImage() {
		// TODO Auto-generated method stub
		return false;
	}

	public SignDeadEnd(){
		
	}

    @Override
    public void signDrive() throws RemoteException, InterruptedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
