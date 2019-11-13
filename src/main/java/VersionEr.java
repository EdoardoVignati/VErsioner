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
    private static Logger logger = Logger.getLogger(Main.class);

    public void build() {

        logger.info("[" + LocalDateTime.now() + "] Building main frame");

        // Main frame
        String title = "VErsioned";
        JFrame mainFrame = new JFrame(title);
        mainFrame.setSize(700, 300);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Setup GridBagLayout of mainFrame
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        mainFrame.setLayout(layout);

        // Display version list
        String[] versionList = new String[]{"elemento1", "elemento2", "elemento3", "elemento4", "elemento5", "elemento1", "elemento2", "elemento3", "elemento4", "elemento5"};
        JList versionsFrame = new JList();
        versionsFrame.setSize(450,300);
        versionsFrame.setBackground(Color.WHITE);
        versionsFrame.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        DefaultListModel listModel = new DefaultListModel();
        for (String s : versionList)
            listModel.add(0, s);
        versionsFrame.setModel(listModel);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(versionsFrame);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridheight = 3;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        mainFrame.add(scrollPane, gbc);

        String description = "Usage of this tool";
        JLabel descr = new JLabel(description);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        mainFrame.add(descr, gbc);

        logger.info("[" + LocalDateTime.now() + "] Creating drag and drop area");

        dndFrame = new DragDropFrame();
        dndFrame.setLayout(new GridLayout(1,1));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weighty = 1;
        mainFrame.add(dndFrame, gbc);

        JButton saveButton = new JButton("Save version");
        saveButton.addMouseListener(this);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.weighty=0.4;
        gbc.anchor = GridBagConstraints.CENTER;
        mainFrame.add(saveButton, gbc);


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
