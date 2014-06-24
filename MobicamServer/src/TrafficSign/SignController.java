package TrafficSign;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.LinkedList;



public class SignController {

    private LinkedList<SignForTraffic> sign;

    public SignController() {
        this.sign = new LinkedList<>();
        //sign.add(new SignDeadEnd());
        sign.add(new SignPlay());
        sign.add(new SignStop());
        sign.add(new SignStandardSpeed());
        //sign.add(new SignOnlyForward());
        File networkFolder = new File("/home/pi", "NeurolaNetwork");
        if (!networkFolder.exists()) {
            networkFolder.mkdir();
        }
        for (SignForTraffic currentSign : sign) {
            File currentSignNetwork = new File(networkFolder, currentSign
                    .getClass().getSimpleName() + "Network.nnet");
            System.out.println(currentSignNetwork.getAbsolutePath());
            if (currentSignNetwork.exists()) {
                currentSign.init(currentSignNetwork);
                System.out.println("Network found!");
            }else{
                System.out.println("Network not found!");
            }
        }
    }

    public LinkedList<SignForTraffic> getSignList() {
        return this.sign;
    }
}
