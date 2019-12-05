package it.edoardovignati.versioner;

import org.apache.log4j.Logger;
import org.eclipse.jgit.revwalk.RevCommit;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author @EdoardoVignati
 */
public class Versioner extends JFrame {

    static DefaultListModel listModel;
    static DragDropFrame dndFrame = null;
    static JTextArea descr = null;
    static JFrame mainFrame;
    static JList versionsFrame;
    static Logger logger = Logger.getLogger(Main.class);
    static ArrayList<RevCommit> commits;
    static JDialog confirmDialog;
    private static GridBagConstraints gbc;

    public static void build() {

        buildMainFrame();

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

        // Restore button
        JButton restoreButton = new JButton("Restore version");
        restoreButton.setLayout(new GridLayout(1, 1));
        restoreButton.setName("restoreButton");
        restoreButton.addMouseListener(new VersionListener());
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridheight = 1;
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
        descr = new JTextArea("Write here a version message");
        descr.addFocusListener(new CustomFocusListener());
        JScrollPane jScrollPane = new JScrollPane(descr);
        descr.setWrapStyleWord(true);
        descr.setLineWrap(true);
        descr.setName("descr");
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        mainFrame.add(jScrollPane, gbc);

        // Save button
        JButton saveButton = new JButton("Save version");
        saveButton.setLayout(new GridLayout(1, 1));
        saveButton.setName("saveButton");
        saveButton.addMouseListener(new VersionListener());
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        mainFrame.add(saveButton, gbc);


        logger.info("[" + LocalDateTime.now() + "] Showing up window");
        mainFrame.setVisible(true);
    }


    static void buildConfirmationDialog() {

        logger.info("[" + LocalDateTime.now() + "] Building confirmation dialog");

        confirmDialog = new JDialog(mainFrame, "Confirm");
        confirmDialog.setLayout(new GridLayout(2, 1));
        JLabel confirmLabel = new JLabel("Confirm restore?");
        confirmLabel.setLayout(new GridLayout(0, 1));
        confirmDialog.add(confirmLabel);

        JButton restoreConfirmation = new JButton("Restore");
        restoreConfirmation.setLayout(new GridLayout(0, 1));

        restoreConfirmation.addMouseListener(new VersionListener());
        restoreConfirmation.setName("restoreConfirmation");
        confirmDialog.add(restoreConfirmation);

        confirmDialog.setSize(300, 150);

        confirmDialog.setVisible(true);
    }

    static void refreshVersions() {

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

    static void restoreConfirmed() {
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



    static void installGit() {

        buildMainFrame();

        JPanel jp = new JPanel();
        jp.setLayout(new GridLayout(2, 1));

        String gitInfobox = "<html>This system requires Git.<br>";
        gitInfobox += "Please install it before to continue<br></html>";
        JLabel gitAlert = new JLabel(gitInfobox);
        jp.add(gitAlert);

        String gitDownload = "<html><a href=https://git-scm.com/downloads>https://git-scm.com/downloads</a></html>";
        JLabel gitUrlLabel = new JLabel(gitDownload);
        gitUrlLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        gitUrlLabel.setName("gitDownload");
        gitUrlLabel.addMouseListener(new LinkListener());
        jp.add(gitUrlLabel);

        mainFrame.add(jp);

        mainFrame.setVisible(true);
    }

    static void buildMainFrame() {
        logger.info("[" + LocalDateTime.now() + "] Building main frame");
        String title = "VErsioner";
        mainFrame = new JFrame(title);
        mainFrame.setSize(700, 300);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Setup GridBagLayout of mainFrame
        GridBagLayout layout = new GridBagLayout();
        gbc = new GridBagConstraints();
        mainFrame.setLayout(layout);

        // Menu bar
        MenuListener menuListener = new MenuListener();
        JMenuBar menubar = new JMenuBar();
        JMenu help = new JMenu("Help");
        help.addMenuListener(menuListener);
        help.setName("help");
        menubar.add(help);
        JMenu about = new JMenu("About");
        about.addMenuListener(menuListener);
        about.setName("about");
        menubar.add(about);
        mainFrame.setJMenuBar(menubar);
    }


}
