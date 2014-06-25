/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TrafficSign;

import java.rmi.RemoteException;

/**
 *
 * @author corbeau
 */
public class SignZone extends SignForTraffic {

    

    @Override
    public boolean signImage() {
        // TODO Auto-generated method stub
        return false;
    }

    public SignZone() {

    }

    @Override
    public void signDrive() throws RemoteException, InterruptedException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
