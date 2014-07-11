package roboter;

public class Main {
	private static Calibrate cali;
	private static NewDriver drive;

	public static void main(String[] args) {
		cali = new Calibrate();
		drive = new NewDriver(cali);
		drive.drive();

	}

}
