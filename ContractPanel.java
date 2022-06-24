package autotrade;

import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.ib.client.Contract;
import com.ib.client.Types.BarSize;
import com.ib.client.Types.DurationUnit;
import com.ib.client.Types.WhatToShow;

import apidemo.util.TCombo;
import apidemo.util.VerticalPanel;

public class ContractPanel extends JPanel {
	
	String symbol = "EUR/USD";
	JTextField txt_symbol = new JTextField(symbol);
//	TCombo <String> symbol_list = new  TCombo<String>();
	JTextField duration = new JTextField("2");
	TCombo <BarSize> barSize = new TCombo<>( BarSize.values() );
	TCombo <DurationUnit> durationUnit = new TCombo<>( DurationUnit.values() );
	TCombo <WhatToShow> whatToShow = new TCombo<>( WhatToShow.values() );
//	JRadioButton btn1 = new JRadioButton("Select");
	
	Contract contract =new Contract();
	public String get_symbol() {
		return symbol;
	}
	public void set_symbol(String symbol) {
		this.symbol = symbol;
		txt_symbol.setText(symbol);
	}
	
	public void set_symbol() {
		System.out.println();
		symbol = txt_symbol.getText();
		
		System.out.println("Set >>>>>> "+symbol);
		txt_symbol.setText(symbol);
	}
	public Integer get_duration() {
		return Integer.parseInt(duration.getText());
	}
	public BarSize get_barSize() {
		return barSize.getSelectedItem();
	}
	public DurationUnit get_durationUnit() {
		return durationUnit.getSelectedItem();
	}
	public WhatToShow get_whatToShow() {
		return whatToShow.getSelectedItem();
	}
	public void setSymbol(String symbol) {
		
	}
//	public JRadioButton getRadioButton() {
//		return btn1;
//	}
	ContractPanel(){
		
		
		barSize.setSelectedItem(BarSize._15_mins);
		durationUnit.setSelectedItem(DurationUnit.DAY);
		whatToShow.setSelectedItem(WhatToShow.TRADES);
		
		VerticalPanel p_barDetail = new VerticalPanel();	
//		p_barDetail.add(btn1);
		p_barDetail.add("Symbol",txt_symbol);
		p_barDetail.add("Bar size",barSize);
		p_barDetail.add("Duration",duration);
		p_barDetail.add("Suration Unit",durationUnit);
		p_barDetail.add("What to show",whatToShow);
		
		setLayout(new GridLayout(0, 1));
		add(p_barDetail);
		
		
	}
	public Contract getContact() {
		String symbol = get_symbol();
		symbol = symbol.toUpperCase();
		System.out.println(symbol);
		Contract contract;
		if (symbol.contains("/")) {
			String symbol1 = symbol.split("/")[0];
			String symbol2 = symbol.split("/")[1];
			contract = ContractATS.getContractFX(symbol1, symbol2);

		} else {
			contract = ContractATS.getContractStock(symbol);

		}
		return  contract;
	}
	
	
	
}
