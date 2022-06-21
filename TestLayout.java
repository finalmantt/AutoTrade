package autotrade;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class TestLayout {

    public static void main(String[] args) {
        new TestLayout();
    }

    public TestLayout() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

                JFrame frame = new JFrame("Testing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new DatabasePropertiesPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public class DatabasePropertiesPane extends JPanel {

        private SourcePane sourcePane;
        private DatabasePane databasePane;
        private SystemDatabasePane systemDatabasePane;
        private ActionPane actionPane;

        public DatabasePropertiesPane() {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
//            gbc.weightx = 1;
//            gbc.weighty = 0.33;
            gbc.anchor = GridBagConstraints.NORTHWEST;
            
            gbc.insets = new Insets(4, 4, 4, 4);

            add((sourcePane = new SourcePane()), gbc);
            gbc.gridy++;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            add((databasePane = new DatabasePane()), gbc);
            gbc.gridy++;
            add((systemDatabasePane = new SystemDatabasePane()), gbc);

            gbc.gridy = 0;
            gbc.gridx++;
            gbc.gridheight = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.VERTICAL;
//            gbc.weighty = 1;
//            gbc.weightx = 0;
            add((actionPane = new ActionPane()), gbc);
        }

    }

    public class SourcePane extends JPanel {
        private JTextField datasourceName;
        private JTextField desciption;

        public SourcePane() {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;

            add(new JLabel("Data Source Name: "), gbc);
            gbc.gridy++;
            add(new JLabel("Description: "), gbc);

            gbc.gridx++;
            gbc.gridy = 0;
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            add((datasourceName = new JTextField(10)), gbc);
            gbc.gridy++;
            add((desciption = new JTextField(10)), gbc);
        }

        public String getDataSourceName() {
            return datasourceName.getText();
        }

        public String getDescription() {
            return desciption.getText();
        }

        public void setDataSourceName(String name) {
            datasourceName.setText(name);
        }

        public void setDescription(String description) {
            desciption.setText(description);
        }

    }

    public class DatabasePane extends JPanel {

        private JButton select, create, repair, compact;
        private JLabel database;

        public DatabasePane() {
            setLayout(new GridBagLayout());
            setBorder(new CompoundBorder(new TitledBorder("Database"), new EmptyBorder(12, 0, 0, 0)));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets = new Insets(0, 0, 0, 4);

            JPanel panel = new JPanel(new GridBagLayout());
            panel.add(new JLabel("Database: "), gbc);
            gbc.gridx++;
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(0, 0, 0, 0);
            panel.add((database = new JLabel()), gbc);

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(4, 4, 4, 4);
            add(panel, gbc);

            gbc.gridwidth = 1;
            gbc.weightx = 0.25;
            gbc.gridy++;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            add((select = new JButton("Select")), gbc);
            gbc.gridx++;
            add((create = new JButton("Create")), gbc);
            gbc.gridx++;
            add((repair = new JButton("Repair")), gbc);
            gbc.gridx++;
            add((compact = new JButton("Compact")), gbc);
        }

    }

    public class SystemDatabasePane extends JPanel {

        private JRadioButton none, database;
        private JLabel databaseLabel;
        private JButton systemDatabase;

        public SystemDatabasePane() {
            setLayout(new GridBagLayout());
            setBorder(new CompoundBorder(new TitledBorder("System Database"), new EmptyBorder(8, 0, 0, 0)));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(0, 0, 0, 4);
            gbc.anchor = GridBagConstraints.WEST;

            JPanel panel = new JPanel(new GridBagLayout());
            panel.add((none = new JRadioButton("None")), gbc);
            gbc.gridy++;
            panel.add((none = new JRadioButton("Database: ")), gbc);

            gbc.gridx++;
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            panel.add((databaseLabel = new JLabel("")), gbc);

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(4, 4, 4, 4);
            add(panel, gbc);

            gbc.gridy++;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.CENTER;
            add((systemDatabase = new JButton("System Database...")), gbc);
            systemDatabase.setEnabled(false);
        }

    }

    public class ActionPane extends JPanel {

        private JButton okay, cancel, help, advanced, options;

        public ActionPane() {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1;
            gbc.insets = new Insets(4, 4, 4, 4);

            add((okay = new JButton("Ok")), gbc);
            gbc.gridy++;
            add((cancel = new JButton("Cancel")), gbc);
            gbc.gridy++;
            add((help = new JButton("Help")), gbc);
            gbc.gridy++;
            add((advanced = new JButton("Advanced")), gbc);
            gbc.gridy++;
            gbc.weighty = 1;
            gbc.anchor = GridBagConstraints.SOUTH;
            add((options = new JButton("Options >>")), gbc);
        }

    }
}