package TrafficSign;

import java.io.File;
import java.rmi.RemoteException;
import java.util.LinkedList;



public class SignController {

    private LinkedList<SignForTraffic> sign;

    public SignController() {
        LinkedList<SignForTraffic> schilder = new LinkedList<>();
        schilder.add(new SignDeadEnd());
        schilder.add(new SignPlay());
        schilder.add(new SignStop());
        schilder.add(new SignRight());
        schilder.add(new SignStandardSpeed());
        schilder.add(new SignOnlyForward());
        File networkFolder = new File(System.getProperty("user.dir"), "NeurolaNetwork");
        if (!networkFolder.exists()) {
            networkFolder.mkdir();
        }
        for (SignForTraffic currentSign : schilder) {
            File currentSignNetwork = new File(networkFolder, currentSign
                    .getClass().getSimpleName() + "Network.nnet");
            if (currentSignNetwork.exists()) {
                currentSign.init(currentSignNetwork);
                System.out.println("Network not found!");
            }
        }
    }

    public LinkedList<SignForTraffic> getSignList() {
        return this.sign;
    }
}
