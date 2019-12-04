package it.edoardovignati.versioner;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDateTime;

import static it.edoardovignati.versioner.Versioner.*;

/**
 * @author @EdoardoVignati
 */

public class VersionListener implements MouseListener {
    static Logger logger = Logger.getLogger(Main.class);


    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

        JButton o = (JButton) mouseEvent.getSource();

        logger.info("[" + LocalDateTime.now() + "] New MouseEvent - " + o.getName() + " click");

        if (o.getName().equals("saveButton"))
            saveTriggered();
        else if (o.getName().equals("restoreButton"))
            buildConfirmationDialog();
        else if (o.getName().equals("restoreConfirmation"))
            restoreConfirmed();
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }


    static void saveTriggered() {
        String message = descr.getText();
        logger.info("[" + LocalDateTime.now() + "] Calling Git manager");

        GitManager.addAndCommit(message);

        logger.info("[" + LocalDateTime.now() + "] Commit done: " + message);

        descr.setText("Write here a version message");
        refreshVersions();
    }
}
