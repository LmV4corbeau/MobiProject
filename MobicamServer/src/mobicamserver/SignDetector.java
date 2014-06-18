/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobicamserver;

import TrafficSign.SignController;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.imgrec.ImageRecognitionPlugin;
import roboter.Calibrate;
import roboter.Driver;
import roboter.NewDriver;

/**
 *
 * @author corbeau
 */
public class SignDetector {
    
    private SignController signController;

    public SignDetector() {
        this.signController = new SignController(new NewDriver(new Calibrate()));
    }

    public String detektTrafficSigne(File picture) {
        
        
    }

    public void bullshitr() {
        // load trained neural network saved with Neuroph Studio (specify some existing neural network file here)
        NeuralNetwork nnet = NeuralNetwork.load("MyImageRecognition.nnet"); // load trained neural network saved with Neuroph Studio
        // get the image recognition plugin from neural network
        ImageRecognitionPlugin imageRecognition = (ImageRecognitionPlugin) nnet.getPlugin(ImageRecognitionPlugin.class); // get the image recognition plugin from neural network

        try {
            // image recognition is done here (specify some existing image file)
            HashMap<String, Double> output = imageRecognition.recognizeImage(new File("someImage.jpg"));
            System.out.println(output.toString());
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }
}
