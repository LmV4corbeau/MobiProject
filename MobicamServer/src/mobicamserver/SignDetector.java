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

}
