package roboter;

import image.CommandRaspberryPi;

import java.rmi.RemoteException;

public class Main {

    private static Calibrate cali;
    private static NewDriver drive;
    private static CommandRaspberryPi cmdRsPi;

    public static void main(String[] args) throws InterruptedException, RemoteException {
        cmdRsPi = new CommandRaspberryPi();
        if (cmdRsPi.connectToEV3()) {
            System.out.print("Connecting...");
            Remote remote = new Remote();
            remote.remote();
            remote.testDrive();
        }else{
            
        }

    }

}
