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

	
	private int calout =0;
	private int calin=0;
	

	private boolean isleft;
	private Calibrate cali;
	private RegulatedMotor rightMotor = new MindsensorsGlideWheelMRegulatedMotor(MotorPort.D);
	private RegulatedMotor leftMotor = new MindsensorsGlideWheelMRegulatedMotor(MotorPort.A);
	private int speed;

	
	public Driver(Calibrate calibrate){
		this.cali = calibrate;
		this.speed = 100;
	}
	@Override
	public void run() {
		
		
		try {
			drive();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
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
		leftMotor.backward();
		rightMotor.backward();
		
	}

	public void backward() throws InterruptedException {
		leftMotor.setSpeed(speed);
		rightMotor.setSpeed(speed);
		leftMotor.forward();
		rightMotor.forward();
		
	}
	public void backward(boolean a) {
		leftMotor.setSpeed(speed);
		rightMotor.setSpeed(speed);
		leftMotor.resetTachoCount();
		rightMotor.resetTachoCount();
		rightMotor.rotate(150, true);
		leftMotor.rotate(150, true);
		while (leftMotor.isMoving()) {
			if ((getCalin() > cali.getinlight()) && (getCalout() > cali.getoutlight())) {
				this.stopMotor();
				this.isleft=true;
				
			}
		}
	}

	public int getCalout() {
		return calout;
	}
	
	
	public boolean left() {
		leftMotor.setSpeed(speed);
		rightMotor.setSpeed(speed);
		leftMotor.resetTachoCount();
		rightMotor.resetTachoCount();
		rightMotor.rotate(-100, true);
		leftMotor.rotate(100, true);
		while (leftMotor.isMoving()) {
			if ((getCalout() > cali.getoutlight())) {
				this.stopMotor();
				this.isleft=true;
				return true;
			}
		}
		return false;
	}

	

	public void drive() throws InterruptedException {
		cali.Calibrate(calin, calout);
		
		while (true) {
			this.setspeed();
			if (Button.ENTER.isDown()) {
				break;
			}
			forward();
			while ((getCalin() > cali.getinlight()) && (getCalout() > cali.getoutlight())) {
				this.setspeed();
				if (Button.ENTER.isDown()) {
					this.setspeed();
					break;
				}

			}
			this.setspeed();
			stopMotor();
			this.setspeed();
			if (getCalin() <= cali.getinlight()) {
				this.setspeed();
				this.right();
			} else if (getCalout() <= cali.getoutlight()) {
				this.setspeed();
				this.left();
			}else if((getCalout() >= cali.getoutlight()) && (getCalin() >= calin)){
				this.setspeed();
				backward(false);
				LCD.drawString("oben", 0, 4);
				if(this.isleft){
					this.setspeed();
					left();
				}
				if(!this.isleft){
					this.setspeed();
					right();
				}
			}
			else{
				this.setspeed();
				backward(false);
				LCD.drawString("unten", 0, 3);
				if(this.isleft){
					LCD.drawString("lunten", 0, 5);
					this.setspeed();
					left();
				}
				else{
					right();
					LCD.drawString("runten", 0, 6);
					this.setspeed();
				}
			}
			this.setspeed();
			
		}
	}

	public boolean right() {
		leftMotor.setSpeed(speed);
		rightMotor.setSpeed(speed);
		leftMotor.resetTachoCount();
		rightMotor.resetTachoCount();
		rightMotor.rotate(100, true);
		leftMotor.rotate(-100, true);
		while (leftMotor.isMoving()) {
			if ((getCalin() > cali.getinlight())) {
				this.stopMotor();
				this.isleft=false;
				return true;
			}
		}

		return false;
	}
	/**
	 * distance to low=0 distance to big=1
	 * @param dis
	 * @return
	 */
	public int makespeed(int dis){
		LCD.clear();
		if (dis == 0){
			LCD.drawString("lower", 0 ,0);
			speed = 75;
		}
		if(dis == 1){
			LCD.drawString("faster", 0 ,0);
			speed = 200;
		}
		if(dis == -1){
			speed = 100;
		}
		return speed;
		
	}
	
	public void setspeed(){
		leftMotor.setSpeed(speed);
		rightMotor.setSpeed(speed); 
	}
	public int getCalin() {
		return calin;
	}
	
}