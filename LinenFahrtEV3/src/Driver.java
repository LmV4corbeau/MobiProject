
import org.jfree.chart.plot.ThermometerPlot;

import lejos.*;
import lejos.ev3.*;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;

public class Driver extends Thread {
	
	private boolean interrupt = false;

	private Port ausererLichtSensorPort = LocalEV3.get().getPort("S1");
	private Port innererLichtSensorPort = LocalEV3.get().getPort("S4");

	private EV3ColorSensor ausererLichtSensor = new EV3ColorSensor(
			ausererLichtSensorPort);
	private EV3ColorSensor innererLichtSensor = new EV3ColorSensor(
			innererLichtSensorPort);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Driver driver = new Driver();
		driver.run();
	}

	@Override
	public void run() {
		while (!this.interrupted()) {
			LCD.drawString("Test", 0, 0);
			LCD.drawInt(this.ausererLichtSensor.getColorID(), 0, 1);
			LCD.drawInt(this.innererLichtSensor.getColorID(), 0, 2);
			if(Button.ENTER.isDown()){
				this.end();
			}
		}
	}

	@Override
	public void interrupt() {
		this.interrupt = true;
	}
	
	@Override
	public boolean isInterrupted() {
		return this.interrupt;
	}
	
	public void end(){
		System.exit(0);
	}
}
