package it.edoardovignati.versioner;

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

class DragDropFrame extends JPanel {

    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(Main.class);
    static JLabel putFile;

    protected DragDropFrame() {

        super();

        JPanel dropPanel = new JPanel();
        GridLayout gd = new GridLayout(1, 1);
        dropPanel.setLayout(gd);
        putFile = new JLabel("Drag & drop here your file");
        putFile.setHorizontalAlignment(JLabel.CENTER);
        dropPanel.add(putFile);

        logger.info("[" + LocalDateTime.now() + "] Initializing  drag and drop listener");
        DragDropListener dragDropListener = new DragDropListener();
        new DropTarget(dropPanel, dragDropListener);

        this.add(dropPanel);

    }
}