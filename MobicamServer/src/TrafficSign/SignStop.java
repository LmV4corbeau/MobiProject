package TrafficSign;

import java.rmi.RemoteException;

public class SignStop extends SignForTraffic {

    

    @Override
    public boolean signImage() {
        // TODO Auto-generated method stub
        return false;
    }

    public SignStop() {

    }

    @Override
    public void signDrive() throws RemoteException, InterruptedException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
