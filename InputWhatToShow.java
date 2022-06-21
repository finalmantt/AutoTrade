package autotrade;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.ib.client.Types.DurationUnit;
import com.ib.client.Types.WhatToShow;

import apidemo.util.TCombo;

public class InputWhatToShow extends JPanel{
	TCombo <WhatToShow> m_list = new TCombo<>( WhatToShow.values() );
	InputWhatToShow( ){
		JLabel label = new JLabel("Show");
		setSelectedItem(WhatToShow.TRADES);
//		add(label);
		add(m_list);
	}
	
	public WhatToShow getSelectedItem() {
		return m_list.getSelectedItem();// get select item
	}
	
	public void setSelectedItem(WhatToShow barSize) {
		m_list.setSelectedItem(barSize);
	}
}
