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
	private long waittime=200;

	/**
	 * @param args
	 */
	/**public static void main(String[] args) {
		Calibrate cali = new Calibrate();

	}
        **/
	/*public void Calibrate(int calout, int calin) {
		while (!Button.ESCAPE.isDown()) {
			LCD.drawString("Test", 0, 0);
			LCD.drawInt(this.outColorSensor.getColorID(), 0, 1);
			LCD.drawInt(this.inColorSensor.getColorID(), 0, 2);
			if (Button.ENTER.isDown()) {

				calout = this.outColorSensor.getColorID();
				calin = this.inColorSensor.getColorID();
				LCD.drawInt(calout, 0, 3);
				LCD.drawInt(calin, 0, 4);

			}
		}
	}*/
	
	

	public int getoutColor() {
		return this.outColorSensor.getColorID();
		
	}
	

	public int getinColor() {

		return this.inColorSensor.getColorID();
	}
}
