package it.edoardovignati.versioner;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URI;

public class MenuListener implements javax.swing.event.MenuListener, MouseListener {
    @Override
    public void menuSelected(MenuEvent menuEvent) {
        JMenu jm = (JMenu) menuEvent.getSource();
        if (jm.getName().equals("help")) {
            JDialog dialog = new JDialog(VErsioner.mainFrame, "Help");
            dialog.setSize(new Dimension(300, 300));
            dialog.setVisible(true);

        } else if (jm.getName().equals("about")) {
            JDialog dialog = new JDialog(VErsioner.mainFrame, "About");
            dialog.setSize(new Dimension(300, 300));
            dialog.setLayout(new GridLayout(2,0));

            String about = "<html><h1>VErsioner</h1></html>";

            JLabel aboutLabel = new JLabel(about);
            dialog.add(aboutLabel);
            aboutLabel.setLayout(new GridLayout());
            aboutLabel.setHorizontalAlignment(SwingConstants.CENTER);

            String link = "<html><a href='https://github.com/EdoardoVignati/VErsioner/'>More on Github</a></html>";
            JLabel externalLink = new JLabel(link);
            externalLink.addMouseListener(this);
            externalLink.setName("externalLink");
            dialog.add(externalLink);
            externalLink.setLayout(new GridLayout());
            externalLink.setHorizontalAlignment(SwingConstants.CENTER);
            externalLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


            dialog.setVisible(true);
        }
    }

    @Override
    public void menuDeselected(MenuEvent menuEvent) {

    }

    @Override
    public void menuCanceled(MenuEvent menuEvent) {

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        JLabel clicked = (JLabel) mouseEvent.getSource();
        if (clicked.getName().equals("externalLink")) {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/EdoardoVignati/VErsioner/"));
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
