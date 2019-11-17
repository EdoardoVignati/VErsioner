import org.apache.log4j.Logger;
import org.bouncycastle.util.CollectionStore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author @EdoardoVignati
 */
public class VersionEr extends JFrame implements MouseListener {

    public VersionEr() {
        super();
    }

    private static DefaultListModel listModel;
    private static DragDropFrame dndFrame = null;
    private static JTextArea descr = null;
    private static Logger logger = Logger.getLogger(Main.class);

    public void build() {

        // Main frame
        logger.info("[" + LocalDateTime.now() + "] Building main frame");
        String title = "VErsioned";
        JFrame mainFrame = new JFrame(title);
        mainFrame.setSize(700, 300);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Setup GridBagLayout of mainFrame
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        mainFrame.setLayout(layout);

        // Display version list
        JList versionsFrame = new JList();
        versionsFrame.setSize(450, 300);
        versionsFrame.setBackground(Color.WHITE);
        versionsFrame.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listModel = new DefaultListModel();
        versionsFrame.setModel(listModel);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(versionsFrame);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        mainFrame.add(scrollPane, gbc);

        // Drag and drop area
        logger.info("[" + LocalDateTime.now() + "] Creating drag and drop area");
        dndFrame = new DragDropFrame();
        dndFrame.setLayout(new GridLayout(1, 1));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        mainFrame.add(dndFrame, gbc);

        // Message area
        String description = "Write here a version message";
        descr = new JTextArea(description);
        descr.setWrapStyleWord(true);
        descr.setLineWrap(true);
        descr.setLayout(new GridLayout(1, 1));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        mainFrame.add(descr, gbc);

        // Save button
        JButton saveButton = new JButton("Save version");
        saveButton.setLayout(new GridLayout(1, 1));
        saveButton.addMouseListener(this);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.weighty = 0.4;
        mainFrame.add(saveButton, gbc);


        logger.info("[" + LocalDateTime.now() + "] Showing up window");
        mainFrame.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        logger.info("[" + LocalDateTime.now() + "] New MouseEvent - Save click");
        JButton o = (JButton) mouseEvent.getSource();
        String message = descr.getText();
        logger.info("[" + LocalDateTime.now() + "] Calling Git manager");
        logger.info("[" + LocalDateTime.now() + "] " + message);
        GitManager.manage(message);

        HashMap<Integer, String> commits = GitManager.getCommits();
        ArrayList<Integer> commitList = new ArrayList<>();
        for (Integer s : commits.keySet())
            commitList.add(s);
        Collections.sort(commitList);
        listModel.removeAllElements();

        long epoch;
        Date expiry;

        String listElement;
        for (int i = 0; i < commitList.size(); i++) {
            epoch = Long.parseLong(String.valueOf(commitList.get(i)));
            expiry = new Date(epoch * 1000);
            listElement = "<html>" + expiry.toString() + "<br />&nbsp;&nbsp;&nbsp;&nbsp;";
            listElement += commits.get(commitList.get(i)) + "<br /><br /></html>";
            listModel.add(i, listElement);
        }
        descr.setText("Write here a version message");

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
