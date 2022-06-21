package autotrade;

import javax.swing.JLabel;
import javax.swing.JPanel;
import com.ib.client.Types.DurationUnit;
import apidemo.util.TCombo;

public class InputDurationUnit extends JPanel{
	TCombo <DurationUnit> m_list = new TCombo<>( DurationUnit.values() );
	InputDurationUnit( ){
		JLabel label = new JLabel("DurationUnit");
		setSelectedItem(DurationUnit.DAY);
		add(label);
		add(m_list);
	}
	
	public DurationUnit getSelectedItem() {
		return m_list.getSelectedItem();// get select item
	}
	
	public void setSelectedItem(DurationUnit barSize) {
		m_list.setSelectedItem(barSize);
	}
}
