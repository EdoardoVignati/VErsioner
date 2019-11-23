package it.edoardovignati.versioner;

import org.apache.log4j.Logger;

import java.time.LocalDateTime;

public class Main {
    public static Logger logger;

    public static void main(String[] args) {

        logger = Logger.getLogger(Main.class);
        logger.info("[" + LocalDateTime.now() + "] New application started");

        logger.info("[" + LocalDateTime.now() + "] Detected OS: " + OperatingSystem.detect());


        logger.info("[" + LocalDateTime.now() + "] Building application");
        VErsioner ve = new VErsioner();
        ve.build();
    }
}
