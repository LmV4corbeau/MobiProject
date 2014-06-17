package roboter;

import java.rmi.RemoteException;

public class Main {
	private static Calibrate cali;
	private static NewDriver drive;

	public static void main(String[] args) throws InterruptedException,
			RemoteException {

		cali = new Calibrate();
		drive = new NewDriver(cali);
		drive.drive();

	}

}
