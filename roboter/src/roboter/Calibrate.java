package roboter;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;

public class Calibrate {

	
	
	private Port ausererSensorPort = LocalEV3.get().getPort("S1");
	private Port innererColorSensorPort = LocalEV3.get().getPort("S4");
	private EV3ColorSensor outColorSensor = new EV3ColorSensor(
			ausererSensorPort);
	private EV3ColorSensor inColorSensor = new EV3ColorSensor(
			innererColorSensorPort);
	/**
	 * @param args
	 */
	
	public int getoutColor() {
		return this.outColorSensor.getColorID();
		
	}
	

	public int getinColor() {

		return this.inColorSensor.getColorID();
	}
}
