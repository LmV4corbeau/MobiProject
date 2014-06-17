import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import lejos.hardware.lcd.LCD;

public class main {
	public static void main(String argv[]) throws Exception {
		String sentence = "Ich bin ein Lego";
		String modifiedSentence;
		Socket clientSocket = new Socket("10.0.1.2", 6789);
		DataOutputStream outToServer = new DataOutputStream(
				clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(
				clientSocket.getInputStream()));
		outToServer.writeBytes(sentence + '\n');
		modifiedSentence = inFromServer.readLine();
		LCD.drawString("FROM SERVER: " + modifiedSentence, 0, 0);
		clientSocket.close();

	}
}
