package roboter;

import java.io.File;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.MindsensorsGlideWheelMRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;

public class NewDriver {
	private boolean driveRight = false;

	private Calibrate cali;
	private RegulatedMotor rightMotor = new MindsensorsGlideWheelMRegulatedMotor(
			MotorPort.D);
	private RegulatedMotor leftMotor = new MindsensorsGlideWheelMRegulatedMotor(
			MotorPort.B);
	private int speed;
	final int black = 7;
	final int blue = 2;
	private boolean standardSpeed = true;

	private Connection connection;

	private String signName;

	public NewDriver(Calibrate cali) {

		this.connection = new Connection();
		this.cali = cali;
		this.speed = 150;
		if (rightMotor == null) {
			LCD.drawString("recht", 0, 5);
		}
		if (leftMotor == null) {
			LCD.drawString("links", 0, 6);
		}
	}

	public boolean isDriveRight() {
		return driveRight;
	}

	public void setDriveRight(boolean driveRight) {
		this.driveRight = driveRight;
	}

	public boolean isStandardSpeed() {
		return standardSpeed;
	}

	public void setStandardSpeed(boolean standardSpeed) {
		this.standardSpeed = standardSpeed;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
		rightMotor.setSpeed(speed);
		leftMotor.setSpeed(speed);
	}

	public int getBlue() {
		return blue;
	}

	public int getBlack() {
		return black;
	}

	public void forward() {
		if (isStandardSpeed() == true) {
			setSpeed(150);
		} else {
			setSpeed(75);
		}
		rightMotor.forward();
		leftMotor.forward();
	}

	public void left() {
		setSpeed(50);
		rightMotor.forward();
		leftMotor.backward();

	}

	public void right() {
		setSpeed(50);
		rightMotor.backward();
		leftMotor.forward();

	}

	public void stop() {
		setSpeed(0);
		rightMotor.backward();
		leftMotor.backward();
		setSpeed(100);
	}

	public void waitMotor() throws InterruptedException {
		rightMotor.wait(4000);
		leftMotor.wait(4000);
	}

	public void drive() {
		LCD.drawString("Bin Laden", 0, 1);
		LCD.drawString("in Ghana", 0, 2);
		if (this.connection.getMessage().equals("go")) {
			LCD.clear();
			setSpeed(100);
			while (!Button.ESCAPE.isDown()) {
				try {
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
						stop();
						chooseSign();

					}
					if (Button.ESCAPE.isDown()) {
						connection.sendMessage("EV3Error");
						System.exit(0);
					}
				} catch (Exception e) {
					stop();
					LCD.clear();
					LCD.drawString(e.getMessage(), 0, 1);
					this.connection.sendMessage("EV3Error :" + e.getMessage());

				}
			}
		}
	}

	public void signDeadEnd() throws InterruptedException {
		boolean temp = this.isStandardSpeed();

		setStandardSpeed(true);
		setSpeed(75);
		left();
		Thread.sleep(2500);
		forward();
		Thread.sleep(1000);
		left();
		Thread.sleep(4500);
		forward();
		Thread.sleep(500);
		setSpeed(150);
		setStandardSpeed(temp);
	}

	public void signOnlyForward() throws InterruptedException {
		LCD.clear();
		LCD.drawString(this.signName, 0, 3);
		forward();
		if (isStandardSpeed() == true) {
			Thread.sleep(7000);
		} else {
			Thread.sleep(14000);
		}

	}

	public void signAttention() throws InterruptedException {
		LCD.clear();
		LCD.drawString(this.signName, 0, 3);
		setStandardSpeed(false);
		forward();
		Thread.sleep(500);

	}

	public void signStandardSpeed() throws InterruptedException {
		LCD.clear();
		LCD.drawString(this.signName, 0, 3);
		setStandardSpeed(true);
		forward();
		Thread.sleep(500);

	}

	public void signStop() throws InterruptedException {
		LCD.clear();
		LCD.drawString(this.signName, 0, 3);
		stop();
		Thread.sleep(8000);
		forward();
		Thread.sleep(500);

	}

	public void signZone() throws InterruptedException {
		LCD.clear();
		LCD.drawString(this.signName, 0, 3);
		setSpeed(50);
		forward();
		Thread.sleep(500);

	}

	public void chooseSign() throws InterruptedException {
		this.signName = null;
		while ((signName == null) || (!signName.contains("Sign"))) {
			signName = this.connection.sendMessage("EV3takepicture");
		}
		if (signName.equals("Error")) {
			LCD.drawString("Fehler!", 0, 1);
		}

		switch (signName) {
		case "SignDeadEnd":
			signDeadEnd();
			break;
		case "SignOnlyForward":
			signOnlyForward();
			break;
		case "SignAttention":
			signAttention();
			break;
		case "SignStandardSpeed":
			signStandardSpeed();
			break;
		case "SignStop":
			signStop();
			break;
		case "SignZone":
			signZone();
			break;
		default:
			LCD.drawString(signName, 4, 4);
		}

	}

	public String getSignName() {
		return signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}

	public void awesome() {
		File awesome = new File("awesome.wav");
		Sound.playSample(awesome, 10);
	}

}
