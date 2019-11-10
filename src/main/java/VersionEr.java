import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author @EdoardoVignati
 */
public class VersionEr extends JFrame implements MouseListener {

    public VersionEr() {
        super();
    }

    private static DragDropFrame dndFrame = null;

    public void build() {
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

        dndFrame = new DragDropFrame();

        mainFrame.add(dndFrame);
        dndFrame.setLayout(new GridLayout(1, 1));
        dndFrame.setVisible(true);

        JButton saveButton = new JButton("Save version");
        saveButton.addMouseListener(this);
        mainFrame.add(saveButton);
        mainFrame.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        JButton o = (JButton) mouseEvent.getSource();
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
