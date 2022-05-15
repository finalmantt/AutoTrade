package autotrade;

import com.ib.controller.ApiController.IAccountHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.ib.controller.Bar;
import com.ib.controller.Position;

public class AccountATSBack extends JPanel implements IAccountHandler {
	private JLabel m_lastUpdated = new JLabel();
	
	private String m_selAcct = "";
	
	private ArrayList<String> header = new ArrayList<String>();	
//	private ArrayList<HashMap<String, String>> data = new ArrayList<>();
	private ArrayList<ArrayList<String>> data = new ArrayList<>();
	
	static DataFrame df = new DataFrame();
	boolean active = false;
	AccountATSBack(){

		String m_selAcct = API.INSTANCE.accountList().get(0);
		System.out.println(">>"+m_selAcct);

		API.INSTANCE.m_controller.reqAccountUpdates(true, m_selAcct, this);

	}
	
	
	public void getAccount() {
		String m_selAcct = API.INSTANCE.accountList().get(0);
		System.out.println(m_selAcct);

		API.INSTANCE.m_controller.reqAccountUpdates(true, m_selAcct, this);
	}
	public void setAccount() {

		
		
		
		df.setHeader(Arrays.asList("account","key","value","currency"));
		df.setData(data);
//		df.addCol();
		df.showTable();
//		df.add(data2,);
		JScrollPane scroll = df.getTable();
		add(scroll);
		
		
		
	}
	public static void showAccount() {
		System.out.println("show");
		df.showTable();
	}
	@Override
	public  void accountValue(String account, String key, String value, String currency) {
		// TODO Auto-generated method stub

		
		ArrayList<String> aa = new ArrayList<String>();
		aa.add(account);
		aa.add(key);
		aa.add(value);
		aa.add(currency);
		data.add(aa);
		
	}

	@Override
	public  void accountTime(String timeStamp) {
		// TODO Auto-generated method stub
		System.out.println("accountTime"+ timeStamp);
//		m_lastUpdated.setText( "Last updated: " + timeStamp + "       ");
//		showAccount();
	}

	@Override
	public  void accountDownloadEnd(String account) {
		// TODO Auto-generated method stub
		System.out.println("accountDownloadEnd");
		System.out.println(account);
		setAccount();
		
//		if(active = false)
//		{
//			showAccount();
//			active= true;
//		}
	}

	@Override
	public  void updatePortfolio(Position position) {
		// TODO Auto-generated method stub
		System.out.println("updatePortfolio" +position);
//		showAccount();
	}

}
