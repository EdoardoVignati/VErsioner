import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DropTarget;

/**
 * Dropping area
 *
 * @author EdoardoVignati
 */

public class DragDropFrame extends JPanel {

    private static final long serialVersionUID = 1L;

    public DragDropFrame() {

        super();

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new GridLayout(0, 1));
        myPanel.setBackground(Color.WHITE);
        JLabel putFile = new JLabel("Drag your file here", SwingConstants.CENTER);
        myPanel.add(putFile);
        putFile.setVisible(true);

        DragDropListener myDragDropListener = new DragDropListener();
        new DropTarget(myPanel, myDragDropListener);
        this.add(BorderLayout.CENTER, myPanel);

        this.setVisible(true);

    }
}