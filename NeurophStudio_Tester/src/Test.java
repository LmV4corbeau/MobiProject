
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.imgrec.ImageRecognitionPlugin;

public class Test {

    public static void main(String[] args) {
        String[] name = new String[3];
        name[0] = "SignStopNetworkV5.nnet";
        name[1] = "SignStandardSpeedNetworkV5.nnet";
        name[2] = "SignOnlyForwardNetwork.nnet";

        for (int i = 0; i < name.length; i++) {
            System.out.println(name[i]);
            System.out.println("----------------------------------------------");
            NeuralNetwork nnet = NeuralNetwork.load(name[i]);
            ImageRecognitionPlugin imageRecognition = (ImageRecognitionPlugin) nnet
                    .getPlugin(ImageRecognitionPlugin.class);
            try {
                HashMap<String, Double> zone = imageRecognition
                        .recognizeImage(new File("01SignZone.jpg"));
                HashMap<String, Double> forward = imageRecognition
                        .recognizeImage(new File("01SignForward.jpg"));
                HashMap<String, Double> stop = imageRecognition
                        .recognizeImage(new File("01SignStop.jpg"));
                HashMap<String, Double> play = imageRecognition
                        .recognizeImage(new File("01SignPlay.jpg"));
                HashMap<String, Double> standard = imageRecognition
                        .recognizeImage(new File("01SignStandardSpeed.jpg"));
                HashMap<String, Double> deadend = imageRecognition
                        .recognizeImage(new File("01SignDeadEnd.jpg"));

                System.out.println("zone:" + summe(zone));
                System.out.println("play:" + summe(play));
                System.out.println("stop:" + summe(stop));
                System.out.println("deadend:" + summe(deadend));
                System.out.println("standard:" + summe(standard));
                System.out.println("forward:" + summe(forward));
                System.out.println("----------------------------------------------");
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    public static Double summe(HashMap<String, Double> name) {
        Double summe = 0.000000;
        for (String current : name.keySet()) {
            summe += name.get(current);
        }
        return summe;
    }
}
