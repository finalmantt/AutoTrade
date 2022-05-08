package autotrade;

public class Account {
	private String account;
	private String key;
	private String value;
	private String currency;
	
	public String account() {return account;}
	public String key() {return key;}
	public String value() {return value;}
	public String currency() {return currency;}
	
	Account(String account, String key, String value, String currency){
		this.account = account;
		this.value = value;
		this.key = key;
		this.currency = currency;
	}
}
