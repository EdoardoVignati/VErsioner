package it.edoardovignati.versioner;

import org.apache.log4j.Logger;
import org.eclipse.jgit.revwalk.RevCommit;

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
    private static JFrame mainFrame;
    private JList versionsFrame;
    private static Logger logger = Logger.getLogger(Main.class);
    ArrayList<RevCommit> commits;
    JDialog confirmDialog;

    public void build() {

        logger.info("[" + LocalDateTime.now() + "] Building main frame");
        String title = "VErsioner";
        mainFrame = new JFrame(title);
        mainFrame.setSize(700, 300);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Setup GridBagLayout of mainFrame
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        mainFrame.setLayout(layout);

        // Menu bar
        JMenuBar menubar = new JMenuBar();
        JMenu help = new JMenu("Help");
        menubar.add(help);
        JMenu about = new JMenu("About");
        menubar.add(about);
        mainFrame.setJMenuBar(menubar);


        // Display version list
        versionsFrame = new JList();
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
        gbc.gridheight = 2;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        mainFrame.add(scrollPane, gbc);

        // restore button
        JButton restoreButton = new JButton("Restore version");
        restoreButton.setLayout(new GridLayout(1, 1));
        restoreButton.setName("restoreButton");
        restoreButton.addMouseListener(this);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        gbc.weighty = 0.4;
        mainFrame.add(restoreButton, gbc);

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
        saveButton.setName("saveButton");
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

        JButton o = (JButton) mouseEvent.getSource();

        logger.info("[" + LocalDateTime.now() + "] New MouseEvent - " + o.getName() + " click");

        if (o.getName().equals("saveButton")) {

            String message = descr.getText();
            logger.info("[" + LocalDateTime.now() + "] Calling Git manager");

            GitManager.addAndCommit(message);

            logger.info("[" + LocalDateTime.now() + "] Commit done: " + message);

            descr.setText("Write here a version message");
            refreshVersions();

        } else if (o.getName().equals("restoreButton"))
            buildConfirmationDialog();
        else if (o.getName().equals("restoreConfirmation")) {

            int versionIndex = versionsFrame.getSelectedIndex();
            confirmDialog.setVisible(false);
            int j = 0;

            for (RevCommit h : commits) {
                if (versionIndex == j) {
                    GitManager.checkout(h);
                    break;
                }
                j++;
            }
            refreshVersions();
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

    private void buildConfirmationDialog() {

        logger.info("[" + LocalDateTime.now() + "] Building confirmation dialog");

        confirmDialog = new JDialog(mainFrame, "Confirm");
        confirmDialog.setLayout(new GridLayout(2, 1));
        JLabel confirmLabel = new JLabel("Confirm restore?");
        confirmLabel.setLayout(new GridLayout(0, 1));
        confirmDialog.add(confirmLabel);

        JButton restoreConfirmation = new JButton("Restore");
        restoreConfirmation.setLayout(new GridLayout(0, 1));

        restoreConfirmation.addMouseListener(this);
        restoreConfirmation.setName("restoreConfirmation");
        confirmDialog.add(restoreConfirmation);

        confirmDialog.setSize(300, 150);

        confirmDialog.setVisible(true);
    }

    public void refreshVersions() {

        logger.info("[" + LocalDateTime.now() + "] Refreshing versions");

        commits = GitManager.getCommits();
        listModel.removeAllElements();

        long epoch;
        Date dateTime;

        String listElement;
        int i = 0;
        for (RevCommit c : commits) {
            epoch = c.getCommitTime();
            dateTime = new Date(epoch * 1000);
            listElement = "<html>" + dateTime.toString() + "<br />&nbsp;&nbsp;&nbsp;&nbsp;";
            listElement += c.getShortMessage() + "<br /><br /></html>";
            listModel.add(i, listElement);
            i++;
        }

    }
}
