/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobicamserver;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import TrafficSign.SignController;
import TrafficSign.SignForTraffic;

/**
 *
 * @author corbeau
 */
public class SignDetector {
    
    private SignController signController;

    public SignDetector() {
        this.signController = new SignController();
    }

    public String detektTrafficSign(File picture) {
    	LinkedList<SignForTraffic> schilderliste = this.signController.getSignList();
        System.out.println(schilderliste.toString());
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
					detected = currentPicture;
				} else if(detectedValue == schilderWertPaare.get(currentPicture)){
                                    return"new picture please";
                                }
			}
		}
        return detected;  
    }
}
