package it.edoardovignati.versioner;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URI;
import java.time.LocalDateTime;

/**
 * @author @EdoardoVignati
 */

public class LinkListener implements MouseListener {
    static Logger logger = Logger.getLogger(Main.class);

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        JLabel url = (JLabel) mouseEvent.getSource();
        if (url.getName().equals("gitDownload")) {
            try {
                Desktop.getDesktop().browse(new URI("https://git-scm.com/downloads"));
            } catch (Exception e) {
                logger.error("[" + LocalDateTime.now() + "] Error on click event");
            }
        }
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
}
