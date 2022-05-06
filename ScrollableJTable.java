package autotrade;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;

public class ScrollableJTable extends JPanel {
    public ScrollableJTable() {
        initializeUI();
    }

    private static void showFrame() {
        JPanel panel = new ScrollableJTable();
        panel.setOpaque(true);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Scrollable JTable");
        frame.setContentPane(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ScrollableJTable::showFrame);
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(500, 250));

        JTable table = new JTable(20, 20);

        // Turn off JTable's auto resize so that JScrollPane will show a horizontal
        // scroll bar.
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        JScrollPane pane = new JScrollPane(table);
        add(pane, BorderLayout.CENTER);
    }
}