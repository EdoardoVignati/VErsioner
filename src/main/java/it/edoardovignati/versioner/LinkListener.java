package it.edoardovignati.versioner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URI;

public class LinkListener implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        JLabel url = (JLabel) mouseEvent.getSource();
        if (url.getName().equals("gitDownload")) {
            try {
                Desktop.getDesktop().browse(new URI("https://git-scm.com/downloads"));
            } catch (Exception e) {
                e.printStackTrace();
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
