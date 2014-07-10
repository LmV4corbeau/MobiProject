
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.imgrec.ImageRecognitionPlugin;

public class Test {

    public static void main(String[] args) {
        String[] name = new String[5];
        name[0] = "SignStopNetwork.nnet";
        name[1] = "SignStandardSpeedNetwork.nnet";
        name[2] = "SignOnlyForwardNetwork.nnet";
        name[3] ="SignDeadEndNetwork.nnet";
        name[4] = "SignPlayNetwork.nnet";
        String[][] data = new String[5][5];
        for (String name1 : name) {
            System.out.println(name1);
            System.out.println("----------------------------------------------");
            NeuralNetwork nnet = NeuralNetwork.load(name1);
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
                HashMap<String, Double> bullshitBild = imageRecognition
                        .recognizeImage(new File("bullshitBild.jpg"));

                System.out.println("zone:" + summe(zone));
                System.out.println("play:" + summe(play));
                System.out.println("stop:" + summe(stop));
                System.out.println("deadend:" + summe(deadend));
                System.out.println("standard:" + summe(standard));
                System.out.println("forward:" + summe(forward));
                System.out.println("bullshitBild:" + summe(bullshitBild));
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
    
    public void output(String[][] data, int zeilen, int spalten){
        for(int i = 0; i < zeilen; i++){
            for(int j = 0; j < spalten; j++){
                System.out.print("\t"+data[i][j]);
            }
            System.out.println("");
        }
    }
    
}
