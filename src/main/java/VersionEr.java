import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDateTime;

/**
 * @author @EdoardoVignati
 */
public class VersionEr extends JFrame implements MouseListener {

    public VersionEr() {
        super();
    }

    private static DragDropFrame dndFrame = null;
    Logger logger = Logger.getLogger(Main.class);

    public void build() {

        logger.info("[" + LocalDateTime.now() + "] Building main frame");

        String title = "VersionEd";
        JFrame mainFrame = new JFrame(title);
        mainFrame.setSize(700, 300);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        GridLayout layout = new GridLayout(3, 1);
        mainFrame.setLayout(layout);

        String description = "Usage of this tool";
        JLabel descr = new JLabel(description);
        descr.setLayout(new GridLayout(1, 1));
        mainFrame.add(descr);

        logger.info("[" + LocalDateTime.now() + "] Creating drag and drop area");

        dndFrame = new DragDropFrame();

        mainFrame.add(dndFrame);
        dndFrame.setLayout(new GridLayout(1, 1));
        dndFrame.setVisible(true);

        JButton saveButton = new JButton("Save version");
        saveButton.addMouseListener(this);
        mainFrame.add(saveButton);
        logger.info("[" + LocalDateTime.now() + "] Showing up window");
        mainFrame.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        logger.info("[" + LocalDateTime.now() + "] New MouseEvent - Save click");
        JButton o = (JButton) mouseEvent.getSource();
        logger.info("[" + LocalDateTime.now() + "] Calling Git manager");
        GitManager.manage();
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
