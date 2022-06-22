package autotrade;

import com.ib.client.Contract;

public class ContractATS {

	public ContractATS() {
		// TODO Auto-generated constructor stub
	}
	
	public static Contract getContractStock(String Symbol) {
		Contract contract = new Contract();
        contract.symbol(Symbol);
        contract.secType("STK");
        contract.currency("USD");
        contract.exchange("SMART");
		return contract; 
	}
	public static Contract getContractFX(String Symbol1,String Symbol2) {
		Contract contract = new Contract();
		contract.symbol(Symbol1);
		contract.secType("CASH");
		contract.currency(Symbol2);
		contract.exchange("IDEALPRO");
		
		
		return contract; 
	}
	
	public static Contract getContractFX(String Symbol) {
		String Symbol1 = Symbol.split("/")[0];
		String Symbol2 = Symbol.split("/")[1];
		
		Contract contract = new Contract();
		contract.symbol(Symbol1);
		contract.secType("CASH");
		contract.currency(Symbol2);
		contract.exchange("IDEALPRO");
		
		
		return contract; 
	}
	public static Contract getContractFXExample() {
		Contract contract = new Contract();
		contract.symbol("EUR");
		contract.secType("CASH");
		contract.currency("USD");
		contract.exchange("IDEALPRO");
		return contract; 
	}
	public static Contract getContractStockExample() {
        Contract contract = new Contract();
        contract.symbol("SPY");
        contract.secType("STK");
        contract.currency("USD");
        contract.exchange("SMART");
		return contract; 
	}
	
	public static Contract getContractOption(String symbol, String right, double strike, String date) {
		Contract contract = new Contract();
        contract.symbol(symbol);
        contract.secType("OPT");
        contract.lastTradeDateOrContractMonth(date);
        contract.strike(strike);
        contract.right(right);
        contract.multiplier("100");        
        contract.currency("USD");
        contract.exchange("SMART");
		return contract; 
	}
	public static Contract getContractOptionExample() {
//		conid	558277737
//		symbol	AAPL
//		secType	OPT
//		lastTradeDateOrContractMonth	20220513
//		strike	157.5
//		right	P
//		multiplier	100
//		exchange	SMART
//		currency	USD
//		localSymbol	AAPL  220513P00157500
//		tradingClass	AAPL
		
		 Contract contract = new Contract();
        contract.symbol("AAPL");
        contract.secType("OPT");
        contract.lastTradeDateOrContractMonth("20220624");
        contract.strike(136);
        contract.right("C");
        contract.multiplier("100");        
        contract.currency("USD");
        contract.exchange("SMART");
		return contract; 
	}
}
