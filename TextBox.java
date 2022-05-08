package autotrade;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TextBox extends JPanel {
	private JTextField txt = new JTextField();
	private JLabel label = new JLabel();
	
	public void setText(String txt) {
		this.txt.setText(txt);
	}
	public void setLabel(String label) {
		this.label.setText(label);
	}
	
	TextBox(String label){
		setLabel(label);
		
	}
}
