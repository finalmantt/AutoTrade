package autotrade;

import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;



public class PanelAccount extends JPanel{
	public TableData tb_account = new TableData(new String[] { "account", "key", "value", "currency" });
	AccountATS acc = null;
	AccountData accData = null;
	
	JLabel label = new JLabel("Account ID:");
	JTextField txt = new JTextField("Accoount",10);
	JButton btn_account = new JButton("Account ID");
	PanelAccount(){
		add(label);
		add(txt);
		add(btn_account);
		
		btn_account.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
//				showAccount();
	
			}
		});
	}
	
	public JPanel getPanelAccount() {
//		add(API.INSTANCE.tb_account.getScroll());
		add(tb_account.getScroll());
		return this;
	}
	public void setAccoutID(String str){
		txt.setText(str);
		
	}
	
	
	public void showAccount() {
		String accID = API.INSTANCE.accountList().get(0);
//		acc = new AccountATS(accID, API.INSTANCE.tb_account);
		acc = new AccountATS(accID, tb_account);
		acc.reqAccount();
		
		API.INSTANCE.p_account.setAccoutID(accID);

	}
}
