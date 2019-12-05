package it.edoardovignati.versioner;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

/**
 * @author @EdoardoVignati
 */

public class OperatingSystem {

    public static String OS;
    static Logger logger = Logger.getLogger(Main.class);


    public static String detect() {
        OS = System.getProperty("os.name");
        return OS;
    }

    public static boolean prerequisites() {
        String gitVersion = null;
        if (isUnix()) {
            Runtime rt = Runtime.getRuntime();
            try {
                Process pr = rt.exec("git --version");

                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(pr.getInputStream()));
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                    builder.append(System.getProperty("line.separator"));
                }
                String gitRegex = "git version [0-9]+.[0-9]+.[0-9]+";
                if (builder.toString().replace("\n", "").matches(gitRegex))
                    gitVersion = builder.toString().replace("\n", "");

            } catch (Exception e) {
                e.printStackTrace();
            }
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
