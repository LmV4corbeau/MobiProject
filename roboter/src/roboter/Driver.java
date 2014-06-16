package roboter;

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BasicMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.MindsensorsGlideWheelMRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;

public class Driver extends Thread {

	private int calout = 0;
	private int calin = 0;

	private boolean isleft;
	private Calibrate cali;
	private RegulatedMotor rightMotor = new MindsensorsGlideWheelMRegulatedMotor(
			MotorPort.A);
	private RegulatedMotor leftMotor = new MindsensorsGlideWheelMRegulatedMotor(
			MotorPort.D);
	private int speed;
	final int black =7;
	final int blue =2;

	public int getBlue() {
		return blue;
	}

	public int getBlack() {
		return black;
	}

	public Driver(Calibrate calibrate) {
		this.cali = calibrate;
		this.speed = 50;
	}

	@Override
	public void run() {

		try {
			drive();
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}
	
	public void blueMotion() throws InterruptedException{
		if(cali.getinColor()==getBlue()){
			stopMotor();
			
		}
		forward();
	}

	public void stopMotor() {
		leftMotor.stop();
		rightMotor.stop();
		leftMotor.flt();
		rightMotor.flt();
	}

	public void forward() throws InterruptedException {
		leftMotor.setSpeed(speed);
		rightMotor.setSpeed(speed);
		leftMotor.forward();
		rightMotor.forward();

	}

	public void backward() throws InterruptedException {
		leftMotor.setSpeed(speed);
		rightMotor.setSpeed(speed);
		leftMotor.backward();
		rightMotor.backward();

	}

	public void backward(boolean a) {
		leftMotor.setSpeed(speed);
		rightMotor.setSpeed(speed);
		leftMotor.resetTachoCount();
		rightMotor.resetTachoCount();
		rightMotor.rotate(50);
		leftMotor.rotate(50);
		/*while (leftMotor.isMoving()) {
			if ((getBlack() != cali.getinColor())
					&& (getBlack() != cali.getoutColor())) {
				this.stopMotor();
				this.isleft = true;

			}
		}*/
	}

	public void left() {
		leftMotor.setSpeed(speed);
		rightMotor.setSpeed(speed);
		leftMotor.resetTachoCount();
		rightMotor.resetTachoCount();
		rightMotor.rotate(-25,true);
		leftMotor.rotate(25,true);
		/*while (leftMotor.isMoving()) {
			if ((getBlack() != cali.getoutColor())) {
				this.stopMotor();
				this.isleft = true;
				return true;
			}
		}*/
		
	}

	public void testdrive() throws InterruptedException {
		
		while (true) {
			this.setspeed();
			if (Button.ENTER.isDown()) {
				break;
			}
			while(cali.getinColor() !=cali.getoutColor()){
				forward();
			}
		}
	}

	public void drive() throws InterruptedException {
		this.speed=50;

		while (true) {
			this.setspeed();
			if (Button.ENTER.isDown()) {
				break;
			}
			
			while ((getBlack() != cali.getinColor())
					&& (getBlack() != cali.getoutColor())) {
				blueMotion();
				this.setspeed();
				if (Button.ENTER.isDown()) {
					this.setspeed();
					break;
				}

			}
			this.setspeed();
			stopMotor();
			this.setspeed();
			if (getBlack() == cali.getoutColor()) {
				this.setspeed();
				this.right();
			} else if (getBlack() == cali.getinColor()) {
				this.setspeed();
				this.left();
			} else if ((getBlack() == cali.getoutColor())
					&& (getBlack() == calin)) {
				this.setspeed();
				backward();
				LCD.drawString("oben", 0, 4);
				if (this.isleft) {
					this.setspeed();
					left();
				}
				if (!this.isleft) {
					this.setspeed();
					right();
				}
			} else {
				this.setspeed();
				backward();
				LCD.drawString("unten", 0, 3);
				if (this.isleft) {
					LCD.drawString("lunten", 0, 5);
					this.setspeed();
					left();
				} else {
					right();
					LCD.drawString("runten", 0, 6);
					this.setspeed();
				}
			}
			this.setspeed();

		}
	}

	public void right() {
		leftMotor.setSpeed(50);
		rightMotor.setSpeed(50);
		leftMotor.forward();
		rightMotor.backward();
		/*leftMotor.resetTachoCount();
		rightMotor.resetTachoCount();
		rightMotor.rotate(25,true);
		leftMotor.rotate(-25,true);
		
		while (leftMotor.isMoving()) {
			if ((getBlack() != cali.getinColor())) {
				this.stopMotor();
				this.isleft = false;
				return true;
			}
		}*/

		
	}

	/**
	 * distance to low=0 distance to big=1
	 * 
	 * @param dis
	 * @return
	 */
	/*public int makespeed(int dis) {
		LCD.clear();
		if (dis == 0) {
			LCD.drawString("lower", 0, 0);
			speed = 75;
		}
		if (dis == 1) {
			LCD.drawString("faster", 0, 0);
			speed = 200;
		}
		if (dis == -1) {
			speed = 100;
		}
		return speed;

	}*/

	public void setspeed() {
		leftMotor.setSpeed(speed);
		rightMotor.setSpeed(speed);
	}

}