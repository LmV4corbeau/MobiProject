package roboter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import lejos.hardware.lcd.LCD;

public class Connection extends Thread {

	private BufferedReader inputstream;
	private PrintWriter output;
	private Socket server;

	public Connection() {
		try {
			this.server = new Socket("10.0.1.2", 6789);
			this.server.setKeepAlive(true);
			this.inputstream = new BufferedReader(new InputStreamReader(
					this.server.getInputStream()));
			this.output = new PrintWriter(this.server.getOutputStream());
		} catch (IOException e) {
			LCD.drawString(e.getMessage(), 0, 2);
		}
	}

	public String sendMessage(String message) {
		LCD.clear();
		LCD.drawString(message, 0, 0);
		this.output.println(message);
		this.output.flush();
		return this.getMessage();

	}

	public String getMessage() {
		String answer = null;
		while (true) {
			try {
				answer = this.inputstream.readLine();
				if (answer.contains("Sign")) {
					break;
				}
				if (answer.contains("go")) {
					break;
				}
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			}
		}
		return answer;
	}

}