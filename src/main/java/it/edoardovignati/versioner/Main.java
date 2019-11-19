package it.edoardovignati.versioner;

import org.apache.log4j.Logger;

import java.time.LocalDateTime;

public class Main {
    public static Logger logger;

    public static void main(String[] args) {

        logger = Logger.getLogger(Main.class);
        logger.info("[" + LocalDateTime.now() + "] New application started");

        logger.info("[" + LocalDateTime.now() + "] Building application");
        VersionEr ve = new VersionEr();
        ve.build();
    }
}
