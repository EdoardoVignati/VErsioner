package it.edoardovignati.versioner;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.io.*;


/**
 * @author @EdoardoVignati
 */

public class OperatingSystem {

    public static String OS;
    static Logger logger = Logger.getLogger(Main.class);


    public static String detect() {
        OS = System.getProperty("os.name").toLowerCase();
        return OS;
    }

    public static boolean prerequisites() {


        String gitVersion = null;
        String gitRegex = "git version [0-9]+\\.[0-9]+\\.[0-9]+.+";


        ProcessBuilder processBuilder = new ProcessBuilder();

        if (isUnix())
            processBuilder.command("bash", "-c", "git --version");
        else if (isWindows())
            processBuilder.command("cmd.exe", "/c", "git --version");

        try {

            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.matches(gitRegex))
                    gitVersion = line;
            }

            int exitVal = process.waitFor();
            if (exitVal == 0) {
                logger.info("Exit ok from git version check");
            } else {
                logger.info("Error from git version check");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // Uncomment to trigger to install git frame
        //gitVersion=null;
        if (gitVersion != null)
            logger.info("[" + LocalDateTime.now() + "] Git version detected: " + gitVersion);
        else {
            logger.info("[" + LocalDateTime.now() + "] Git not found");
            return false;
        }

        return true;
    }

    public static boolean isWindows() {
        return (OS.indexOf("win") >= 0);

    }

    public static boolean isMac() {
        return (OS.indexOf("mac") >= 0);

    }

    public static boolean isUnix() {
        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);

    }

    public static boolean isSolaris() {
        return (OS.indexOf("sunos") >= 0);
    }
}
