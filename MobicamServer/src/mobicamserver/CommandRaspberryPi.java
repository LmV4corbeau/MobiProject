package mobicamserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CommandRaspberryPi {

    private File pictureFolder;

    public void makeAPicture(String name) {
        try {
            String path = System.getProperty("user.dir");
            this.pictureFolder = new File(path, "pictures");
            if (!pictureFolder.exists()) {
                System.out.println("Folder don't exists");
                pictureFolder.mkdir();
            } else {
                System.out.println("Folder exists");
            }
            String output = this.runCommand("raspistill -o " + pictureFolder.getAbsolutePath() + "/" + name + ".jpg -w 700 -h 700");

            System.out.println(output);
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public boolean connectToEV3() {
        if (this.checkIfConnectionIsAlreadyRunnig()) {
            return true;
        }
        try {
            //usbverbindung überprüfen
            String checkUSBConnettion = this.runCommand("ls /sys/class/net | grep 'usb0'");
            System.out.println(checkUSBConnettion);
            if (checkUSBConnettion.contains("usb0")) {
                //netwerkverbingung aufbauen
                String enableNetworking = this.runCommand("sudo ifconfig usb0 10.0.1.2");
                System.out.println(enableNetworking);
                String checkNetwork = this.runCommand("ping -c4 10.0.1.1 | grep 'packet loss'");
                System.out.println(checkNetwork);
                if (checkNetwork.contains("0% packet loss")) {
                    System.out.println("Network is working");
                    return true;
                }
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            return false;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return false;

    }

    public boolean checkIfConnectionIsAlreadyRunnig() {
        try {
            String checkNetwork = this.runCommand("ping -c4 10.0.1.1 | grep 'packet loss'");
            if (checkNetwork.contains("100% packet loss")) {

                return false;
            }
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
            return false;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        System.out.println("Network is already working");
        return true;
    }

    public String runCommand(String cmd) throws InterruptedException, IOException {
        // this is the command to execute in the Unix shell
        // create a process for the shell
        String output = "";
        ProcessBuilder pb = new ProcessBuilder("bash", "-c", cmd);
        pb.redirectErrorStream(true); // use this to capture messages sent to stderr
        Process shell = pb.start();
        InputStream shellIn = shell.getInputStream(); // this captures the output from the command
        BufferedReader reader = new BufferedReader(new InputStreamReader(shellIn));
        int shellExitStatus = shell.waitFor(); // wait for the shell to finish and get the return code
        // at this point you can process the output issued by the command
        // for instance, this reads the output and writes it to System.out:
        String tmp;
        while ((tmp = reader.readLine()) != null) {
            output += "\n" + tmp;
        }
        // close the stream
        try {
            shellIn.close();
        } catch (IOException ignoreMe) {
            System.out.println(ignoreMe.getMessage());
        }
        return output;
    }

    public File getpictureFolder() {
        if (this.pictureFolder != null) {
            if (this.pictureFolder.exists()) {
                return this.pictureFolder;
            }
        }
        return null;
    }
}
