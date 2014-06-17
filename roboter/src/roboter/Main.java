package roboter;

import java.rmi.RemoteException;

public class Main {
	private static Calibrate cali;
	private static NewDriver drive;

	public static void main(String[] args) throws InterruptedException,
			RemoteException {

<<<<<<< HEAD
    public static void main(String[] args) throws InterruptedException, RemoteException {
        
        Remote remote = new Remote();
        remote.remote("10.0.1.1");
    }
=======
		cali = new Calibrate();
		drive = new NewDriver(cali);
		drive.drive();

	}
>>>>>>> ce053abf2686e2d6ebcf958071ff957cd197e64c

}
