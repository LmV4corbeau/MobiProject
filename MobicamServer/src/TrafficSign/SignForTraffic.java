package TrafficSign;

import java.rmi.RemoteException;
import org.neuroph.core.NeuralNetwork;


public abstract class SignForTraffic {
	
	private NeuralNetwork nnet;
	
	
	
	public abstract boolean signImage();
	public abstract void signDrive() throws RemoteException, InterruptedException;
        
        
}
 