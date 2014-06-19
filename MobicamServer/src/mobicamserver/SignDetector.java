/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobicamserver;

import TrafficSign.SignController;
import TrafficSign.SignForTraffic;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import roboter.Calibrate;
import roboter.NewDriver;
import TrafficSign.SignController;
import TrafficSign.SignForTraffic;

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
    	LinkedList<SignForTraffic> schilderliste = this.signController.getSignList();
    	HashMap<String, Double> schilderWertPaare = new HashMap<>();
    	for(SignForTraffic current : schilderliste){
    		Double currentValue = current.neuralValue(picture);
    		schilderWertPaare.put(current.getClass().getSimpleName(), currentValue);
    	}
    	String detected = null;
    	double detectedValue = 0.0;
    	if (schilderWertPaare != null) {
			for (String currentPicture : schilderWertPaare.keySet()) {
				if(detectedValue < schilderWertPaare.get(currentPicture)) {
					detectedValue = schilderWertPaare.get(currentPicture);
					detected = schilderWertPaare.getClass().getSimpleName();
				}
			}
		}
        return detected;  
    }
}
