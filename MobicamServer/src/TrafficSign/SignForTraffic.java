package TrafficSign;

import java.rmi.RemoteException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.imgrec.ImageRecognitionPlugin;

public abstract class SignForTraffic {

    private NeuralNetwork nnet;

    public void init() {
        System.out.println(this.getClass().getSimpleName() + ": load network");
        this.nnet = NeuralNetwork.load("/home/pi/NeurolaNetwork/" + this.getClass().getSimpleName() + "Network.nnet");
        System.out.println(this.getClass().getSimpleName() + ": network loaded");
    }

    public double neuralValue(File picture) {
        ImageRecognitionPlugin imageRecognition = (ImageRecognitionPlugin) this.nnet.getPlugin(ImageRecognitionPlugin.class);
        HashMap<String, Double> output = null;
        try {
            output = imageRecognition.recognizeImage(picture);
            System.out.println(this.getClass().getSimpleName());
            //System.out.println(output.toString());
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        double value = 0.0;
        if (output != null) {
            for (String currentPicture : output.keySet()) {
                value +=output.get(currentPicture);
            }
        }
        return value;
    }

    public abstract boolean signImage();

    public abstract void signDrive() throws RemoteException,
            InterruptedException;

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
