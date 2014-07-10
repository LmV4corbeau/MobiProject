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
import TrafficSign.SignOnlyForward;

/**
 *
 * @author corbeau
 */
public class SignDetector {

    private SignController signController;
    private SignForTraffic signOnlyForward;

    public SignDetector() {
        this.signController = new SignController();
        this.signOnlyForward = new SignOnlyForward();
        this.signOnlyForward.init();
    }

    public String detektTrafficSign(File picture) {
        LinkedList<SignForTraffic> schilderliste = this.signController.getSignList();
        System.out.println(schilderliste.toString());
        HashMap<String, Double> schilderWertPaare = new HashMap<>();
        for (SignForTraffic current : schilderliste) {
            Double currentValue = current.neuralValue(picture);
            schilderWertPaare.put(current.getClass().getSimpleName(), currentValue);
            System.out.println(current + ": " + currentValue);
            System.out.println("");
        }
        String detected = null;
        double detectedValue = Double.MIN_VALUE;
        if (schilderWertPaare != null) {
            for (String currentPicture : schilderWertPaare.keySet()) {
                if (detectedValue < schilderWertPaare.get(currentPicture)) {
                    detectedValue = schilderWertPaare.get(currentPicture);
                    detected = currentPicture;
                } else if (detectedValue == schilderWertPaare.get(currentPicture)) {
                    return "new picture please";
                }
            }
            if(detected.equals("SignDeadEnd")){
                if(this.signOnlyForward.neuralValue(picture) > detectedValue){
                    detected = this.signOnlyForward.getClass().getSimpleName();
                }
            }
        }
        return detected;
    }
}
