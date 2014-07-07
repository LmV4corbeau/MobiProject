package TrafficSign;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.LinkedList;



public class SignController {

    private LinkedList<SignForTraffic> sign;

    public SignController() {
        this.sign = new LinkedList<>();
        sign.add(new SignDeadEnd());
        sign.add(new SignPlay());
        sign.add(new SignStop());
        sign.add(new SignStandardSpeed());
        sign.add(new SignOnlyForward());
        sign.add(new SignAttention());
        for (SignForTraffic currentSign : sign) {
                currentSign.init();
        }
    }

    public LinkedList<SignForTraffic> getSignList() {
        return this.sign;
    }
}
