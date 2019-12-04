package it.edoardovignati.versioner;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.time.LocalDateTime;

/**
 * @author @EdoardoVignati
 */

public class CustomFocusListener implements java.awt.event.FocusListener {

    static Logger logger = Logger.getLogger(Main.class);

    @Override
    public void focusGained(FocusEvent focusEvent) {
        try {
            JTextArea jt = (JTextArea) focusEvent.getSource();
            if (jt.getName().equals("descr"))
                Versioner.descr.setText("");
        } catch (Exception e) {
            logger.error("[" + LocalDateTime.now() + "] Error on focus event");
        }
    }

    @Override
    public void focusLost(FocusEvent focusEvent) {
        try {
            JTextArea jt = (JTextArea) focusEvent.getSource();
            if (jt.getName().equals("descr"))
                if (Versioner.descr.getText().equals(""))
                    Versioner.descr.setText("Write here a version message");
        } catch (Exception e) {
            logger.error("[" + LocalDateTime.now() + "] Error on focus event");
        }
    }
}
