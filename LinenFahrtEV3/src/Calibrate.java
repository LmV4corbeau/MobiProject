package roboter;



import org.jfree.chart.plot.ThermometerPlot;

import lejos.*;
import lejos.ev3.*;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;

public class Calibrate {

	private boolean interrupt = false;
	private Port itouchButtonPort = LocalEV3.get().getPort("S3");
	private EV3TouchSensor  itouchButton = new EV3TouchSensor(itouchButtonPort);
	private Port ausererLichtSensorPort = LocalEV3.get().getPort("S1");
	private Port innererLichtSensorPort = LocalEV3.get().getPort("S4");
	private EV3ColorSensor outLightSensor = new EV3ColorSensor(
			ausererLichtSensorPort);
	private EV3ColorSensor inLightSensor = new EV3ColorSensor(
			innererLichtSensorPort);

	
	/**     
	 * @param args
	 */
	public static void main(String[] args) {
		Calibrate cali = new Calibrate();
		
	}

	public void Calibrate(int calout, int calin) {
		while (!this.isInterrupted()) {
			LCD.drawString("Test", 0, 0);
			LCD.drawInt(this.outLightSensor.getColorID(), 0, 1);
			LCD.drawInt(this.inLightSensor.getColorID(), 0, 2);
			if (Button.ENTER.isDown()) {
				
				calout = this.outLightSensor.getColorID();
				calin = this.inLightSensor.getColorID();
				LCD.drawInt(calout, 0, 3);
				LCD.drawInt(calin, 0, 4);
				if(Button.ESCAPE.isDown()) {
					this.end();
				}
			}
		}
	}

	public void interrupt() {
		this.interrupt = true;
	}

	public boolean isInterrupted() {
		return this.interrupt;
	}

	public void end() {
		System.exit(0);
	}
	public int getoutlight() {

		return this.outLightSensor.getColorID();
	}
	public int getinlight() {

		return this.inLightSensor.getColorID();
	}
}
