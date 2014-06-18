package roboter;

import TrafficSign.SignController;
import TrafficSign.TrafficSign;
import java.rmi.RemoteException;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.MindsensorsGlideWheelMRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;

public class NewDriver {
	private boolean driveRight = false;

	private Calibrate cali;
	private RegulatedMotor rightMotor = new MindsensorsGlideWheelMRegulatedMotor(
			MotorPort.A);
	private RegulatedMotor leftMotor = new MindsensorsGlideWheelMRegulatedMotor(
			MotorPort.D);
	private int speed;
	private TrafficSign sign;
	final int black = 7;
	final int blue = 2;
	private SignController signController;

	public boolean isDriveRight() {
		return driveRight;
	}

	public void setDriveRight(boolean driveRight) {
		this.driveRight = driveRight;
	}

	public NewDriver(Calibrate cali) {
		SignController signController = new SignController(this);
		this.cali = cali;
		this.speed = 75;
		if (rightMotor == null) {
			LCD.drawString("recht", 0, 5);
		}
		if (leftMotor == null) {
			LCD.drawString("links", 0, 6);
		}
		sign = new TrafficSign(this);
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getBlue() {
		return blue;
	}

	public int getBlack() {
		return black;
	}

	public void forward() throws RemoteException {

		rightMotor.setSpeed(speed);
		leftMotor.setSpeed(speed);
		rightMotor.forward();
		leftMotor.forward();
	}

	public void backward() throws RemoteException {

		rightMotor.setSpeed(speed);
		leftMotor.setSpeed(speed);
		rightMotor.backward();
		leftMotor.backward();
	}

	public void left() throws RemoteException {

		rightMotor.setSpeed(speed);
		leftMotor.setSpeed(speed);
		rightMotor.forward();
		leftMotor.backward();

	}

	public void right() throws RemoteException {

		rightMotor.setSpeed(speed);
		leftMotor.setSpeed(speed);
		rightMotor.backward();
		leftMotor.forward();

	}

	public void stop() throws RemoteException {
		setSpeed(0);
		rightMotor.setSpeed(speed);
		leftMotor.setSpeed(speed);
		rightMotor.backward();
		leftMotor.backward();
		setSpeed(75);
	}

	public void left(boolean a) throws RemoteException, InterruptedException {
		rightMotor.setSpeed(speed);
		leftMotor.setSpeed(speed);
		rightMotor.forward();
		leftMotor.backward();
		Thread.sleep(8000);

	}

	public void waitMotor() throws InterruptedException {
		rightMotor.wait(4000);
		leftMotor.wait(4000);
	}

	public void drive() throws InterruptedException, RemoteException {
		
		while (!Button.ESCAPE.isDown()) {
			SignController signController = new SignController(this);
			LCD.drawInt(cali.getoutColor(), 0, 1);
			LCD.drawInt(cali.getinColor(), 0, 2);
			forward();

			while (cali.getoutColor() == getBlack()) {
				right();

				Thread.sleep(200);

			}
			while (cali.getinColor() == getBlack()) {
				left();
				Thread.sleep(200);

			}
			while (cali.getinColor() == getBlue()) {
				this.signController.chooseSign();
			}
			if (Button.ESCAPE.isDown()) {
				System.exit(0);
			}
		}
	}
}
