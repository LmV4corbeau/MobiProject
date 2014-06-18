package roboter;

import java.io.BufferedReader;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import lejos.hardware.lcd.LCD;

public class Connection extends Thread {

	private BufferedReader inputstream;
	private DataOutputStream output;
	private Socket server;

	public Connection() {
		try {
			this.server = new Socket("10.0.1.2", 6789);
			this.inputstream = new BufferedReader(new InputStreamReader(
					this.server.getInputStream()));
			this.output = new DataOutputStream(this.server.getOutputStream());
		} catch (IOException e) {
			LCD.drawString(e.getMessage(), 0, 2);
		}

	}

	@Override
	public void run() {
		while (true) {

		}

	}

	public String sendMessage(String message) {
		try {
			this.output.writeBytes(message);
			this.output.flush();
			
			return this.getMessage();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		return "Error";
	}

	public String getMessage() {
		String answer = "";
		while (answer.isEmpty()) {
			try {
				answer = this.inputstream.readLine();
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			}
		}
		return answer;
	}

}