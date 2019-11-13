import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DropTarget;
import java.time.LocalDateTime;

/**
 * Dropping area
 *
 * @author EdoardoVignati
 */

public class DragDropFrame extends JPanel {

    private static final long serialVersionUID = 1L;
    Logger logger = Logger.getLogger(Main.class);


    public DragDropFrame() {

        super();

        JPanel dropPanel = new JPanel();
        dropPanel.setLayout(new GridLayout(0, 1));
        dropPanel.setBackground(Color.WHITE);

        JLabel putFile = new JLabel("Drag & drop here your file", SwingConstants.CENTER);
        dropPanel.add(putFile);
        putFile.setVisible(true);

        logger.info("[" + LocalDateTime.now() + "] Initializing  drag and drop listener");
        DragDropListener dragDropListener = new DragDropListener();
        new DropTarget(dropPanel, dragDropListener);
        this.add(BorderLayout.CENTER, dropPanel);

        this.setVisible(true);

    }
}