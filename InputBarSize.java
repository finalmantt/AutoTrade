package autotrade;

import javax.swing.JLabel;
import javax.swing.JPanel;
import com.ib.client.Types.BarSize;
import apidemo.util.TCombo;

public class InputBarSize extends JPanel{
	
	TCombo <BarSize> m_list = new TCombo<>( BarSize.values() );
	InputBarSize( ){
		JLabel label = new JLabel("Bar Size");
		setSelectedItem(BarSize._15_mins);
		add(label);
		add(m_list);
	}
	
	public BarSize getSelectedItem() {
		return m_list.getSelectedItem();// get select item
	}
	
	public void setSelectedItem(BarSize barSize) {
		m_list.setSelectedItem(barSize);
	}
	
	
	
}
