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
}
