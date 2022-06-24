package autotrade;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AutotradePanel extends JPanel {
	public TableData tb_autoTrade = new TableData(new String[] { "time", "open", "high", "low", "close" });
	ContractPanel contractPanel = new ContractPanel();
			
	JButton btn_autoStart = new JButton("Start Trade");
	JButton btn_autoStop = new JButton("Stop Trade");
	InputText txtQty = new InputText("QTY","");
	String symbol = "";
	String qty = "";
	AutotradePanel(String symbol, String qty){
		this.qty = qty;
		contractPanel.set_symbol(symbol);
		
		txtQty.setText(qty);
		GridLayout grid_autotrade = new GridLayout(5, 1);		
		JPanel sub_autotrade = new JPanel();
		sub_autotrade.setLayout(grid_autotrade);
		
		
		btn_autoStop.setEnabled(false);
			
		sub_autotrade.add(btn_autoStart);
		sub_autotrade.add(btn_autoStop);	
		sub_autotrade.add(txtQty);	
		
		add(tb_autoTrade.getScroll());
		add(contractPanel);	
		add(sub_autotrade);
		
		
		
		btn_autoStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				autoStart();
			}
		});
		btn_autoStop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				autoStop();
			}
		});
		
		
	}
	
	AutoTreadGO autogo ;
	public void autoStart() {
		contractPanel.set_symbol();
		autogo = new AutoTreadGO(contractPanel,tb_autoTrade, txtQty.getText());
		
		btn_autoStop.setEnabled(true);
		btn_autoStart.setEnabled(false);
	}

	public void autoStop() {
		autogo.reqStop();
		
		btn_autoStop.setEnabled(false);
		btn_autoStart.setEnabled(true);
	}
}
