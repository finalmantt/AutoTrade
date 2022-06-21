package autotrade;
//https://stackoverflow.com/questions/33576358/how-to-use-java-swing-layout-manager-to-make-this-gui
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TestPanel extends JPanel  {
	private JTextField datasourceName;
    private JTextField desciption;

    public TestPanel() {
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

    public static void main(String[] args) {
    	JFrame frame = new JFrame();
    	
    	JPanel p_main  = new JPanel();
    	TestPanel panel = new TestPanel();
    	
    	p_main.setLayout(new GridBagLayout());
    	 GridBagConstraints gbc = new GridBagConstraints();
         gbc.gridx = 0;
         gbc.gridy = 0;
         gbc.weightx = 1;
         gbc.weighty = 0.33;
         gbc.anchor = GridBagConstraints.WEST;
         gbc.fill = GridBagConstraints.BOTH;
         gbc.insets = new Insets(4, 4, 4, 4);
         p_main.add(panel, gbc);
         gbc.gridy++;
         p_main.add(panel, gbc);
    	
    	frame.add(p_main);
		frame.setSize(900, 400);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
//public class TestPanel {
//	public static void main(String[] args) {
//		JFrame frame = new JFrame();
//		
//		TableData tb_account = new TableData(new String[] { "account", "key", "value", "currency" });
//		ContractPanel cp = new ContractPanel();
//		ContractPanel cp2 = new ContractPanel();
//		JTextField txt = new JTextField("Hello");
//		
//		
//		 GridBagLayout layout = new GridBagLayout();
//		 
//		JPanel panel = new JPanel();
//		panel.setLayout(layout);
//		GridBagConstraints gbc = new GridBagConstraints();
//		
//		gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        panel.add(cp, gbc);
//        
//        gbc.gridx = 1;
//        gbc.gridy = 0;
//        panel.add(tb_account.getScroll(), gbc);
//        
//        gbc.gridx = 0;
//        gbc.gridy = 2;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
////        gbc.gridwidth = 2;
//        panel.add(txt, gbc);
//        
//		frame.add(panel);
//		frame.setSize(900, 400);
//		frame.setVisible(true);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	}
//}
