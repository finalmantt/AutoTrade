package autotrade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ib.controller.ApiController.IAccountHandler;
import com.ib.controller.Position;

public class AccountATS2  implements IAccountHandler {
	ArrayList<Account> account = new ArrayList<Account>();
	DataFrame df = new DataFrame();
	String accountID = "";
	public AccountATS2(String accountID) {
		
		// from AccountATS2 to Historical
		System.out.println("Create Account");
		System.out.println("Account ID: "+accountID);
		this.accountID=accountID;
		

	}
	public void clear() {
		df.clear();
		account.clear();
	}
	public void reqAccount() {
		clear();		
		API.INSTANCE.m_controller.reqAccountUpdates(true, accountID, this);
	}

	public void setAccount() {
		
		df.setHeader(Arrays.asList("account","key","value","currency"));
		for (int i = 0; i < account.size(); i++) {
			ArrayList<String> detail = new ArrayList<String>();
			detail.add(account.get(i).account());
			detail.add(account.get(i).key());
			detail.add(account.get(i).value());
			detail.add(account.get(i).currency());
			
			df.addRow(detail);
		}
		API.INSTANCE.p_west.add(df.getTable());
		API.INSTANCE.frame.setVisible(true);
		
		if(API.stepAuto ==1) {
			HistoryATS hist = new HistoryATS();
			hist.reqHistorical();
			
		}
//		end = false;
	}
//	public void checkEnd() {
//		while (end) {
//			System.out.print("");
//		}
//	}
//	public void showTable() {
////		checkEnd();
//		
//		
//		df.showTable();
//	}
	public DataFrame getDF() {
//		checkEnd();
		return df;
	}

	@Override
	public void accountValue(String account, String key, String value, String currency) {
		// TODO Auto-generated method stub
		
		this.account.add(new Account(account, key, value, currency));
	}

	@Override
	public void accountTime(String timeStamp) {
		// TODO Auto-generated method stub

	}
//	boolean end = true;
	@Override
	public void accountDownloadEnd(String account) {
		// TODO Auto-generated method stub
		
		setAccount();
		
	}

	@Override
	public void updatePortfolio(Position position) {
		// TODO Auto-generated method stub

	}

}
