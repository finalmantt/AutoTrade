package autotrade;

import com.ib.controller.ApiController.IAccountHandler;

import java.util.HashMap;
import java.util.Map;

import com.ib.controller.Position;

public class AccountData implements IAccountHandler {
	String accountID = "";
	Map<String, Account> m_account = new HashMap<>();

	AccountData(String accountID) {
		System.out.println("Create Account");
		System.out.println("Account ID: " + accountID);
		this.accountID = accountID;
	}

	public void reqAccount() {
		API.INSTANCE.m_controller.reqAccountUpdates(true, accountID, this);
	}
	
	public String getAccountDetail(String key) {
		return  m_account.get(key + ",USD").value().toString();
	}
	
	public void setAccount(String key) {
		if(key.equals("NetLiquidation"+",USD")) {
			System.out.println("NetLiquidation = "+ getAccountDetail("NetLiquidation"));
		}
		else if (key.equals("BuyingPower"+",USD")) {
			System.out.println("BuyingPower = "+ getAccountDetail("BuyingPower"));
		}		
	}
	


	@Override
	public void accountValue(String account, String key, String value, String currency) {
		// TODO Auto-generated method stub
//		System.out.println("accountValue = " + account + " key " + key + " value " + value + " currency " + currency);
		String MapKey = key + "," + currency;
		m_account.put(MapKey, new Account(account, key, value, currency));
		setAccount(key + ",USD");
	}

	@Override
	public void accountTime(String timeStamp) {
		// TODO Auto-generated method stub
//		System.out.println("accountTime = " + timeStamp);
	}

	@Override
	public void accountDownloadEnd(String account) {
		// TODO Auto-generated method stub
		System.out.println("accountDownloadEnd = " + account);
	}

	@Override
	public void updatePortfolio(Position position) {
		// TODO Auto-generated method stub
//		System.out.println("updatePortfolio = " + position.contract().toString());
	}

}
