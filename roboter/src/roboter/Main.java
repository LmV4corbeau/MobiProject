package roboter;

import image.CommandRaspberryPi;

import java.rmi.RemoteException;

public class Main {

    private static Calibrate cali;
    private static NewDriver drive;
    private static CommandRaspberryPi cmdRsPi;

    public static void main(String[] args) throws InterruptedException, RemoteException {
        
        Remote remote = new Remote();
        remote.remote("10.0.1.1");
    }

}
