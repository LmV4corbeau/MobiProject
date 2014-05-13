package mobiproject;

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
	private int cali_value_out;
	private int cali_value_in;
	private EV3ColorSensor ausererLichtSensor = new EV3ColorSensor(
			ausererLichtSensorPort);
	private EV3ColorSensor innererLichtSensor = new EV3ColorSensor(
			innererLichtSensorPort);

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Calibrate cali = new Calibrate();
		cali.run();
	}

	public void run() {
		while (!this.isInterrupted()) {
			LCD.drawString("Test", 0, 0);
			LCD.drawInt(this.ausererLichtSensor.getColorID(), 0, 1);
			LCD.drawInt(this.innererLichtSensor.getColorID(), 0, 2);
			if (Button.ENTER.isDown()) {
				
				cali_value_out = this.ausererLichtSensor.getColorID();
				cali_value_in = this.innererLichtSensor.getColorID();
				LCD.drawInt(cali_value_out, 0, 3);
				LCD.drawInt(cali_value_in, 0, 4);
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
}
