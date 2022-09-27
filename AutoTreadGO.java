package autotrade;

import com.ib.controller.ApiController.IRealTimeBarHandler;
import com.ib.client.Contract;
import com.ib.client.Types.SecType;
import com.ib.client.Types.WhatToShow;
import com.ib.controller.Bar;
/// AutotradePanel -> AutoTradeGo -> AutoTrade
// We can use another time tick instead of RealTimeBarHandler because we DO NOT use bar information in this class 
// In AutoTrade, HistoricalBar can get data until current time bar. 
public class AutoTreadGO implements IRealTimeBarHandler {
	ContractPanel contractPanel;
	WhatToShow whatToShow = WhatToShow.TRADES;
	Contract contract = new Contract();
	TableData tableData;
	Bar bar;
	String qty = "";	
	AutotradePanel autoPanel=null;
	
//	public AutoTreadGO(ContractPanel contractPanel, TableData tableData, String qty) {
//		// TODO Auto-generated constructor stub
//		
//		this.qty = qty;
//		this.tableData = tableData;
//		this.contractPanel = contractPanel;
//		this.contract = contractPanel.getContact();
//		reqRealtime();
//	}
//	
	public AutoTreadGO(AutotradePanel autoPanel) {
		this.autoPanel = autoPanel;
		this.qty = autoPanel.qty;
		this.tableData = autoPanel.tb_autoTrade;
		this.contractPanel = autoPanel.contractPanel;
		this.contract = autoPanel.contractPanel.getContact();
		reqRealtime();
	}

	public void reqRealtime() {

		if (contract.secType().equals(SecType.CASH)) {
			whatToShow = whatToShow.MIDPOINT;
//			whatToShow = whatToShow.BID_ASK;
		}
		if (contract.secType().equals(SecType.STK)) {
			whatToShow = whatToShow.TRADES;
		}

		API.INSTANCE.m_controller.reqRealTimeBars(contract, whatToShow, false, this);
	}

	public void setRealTime() {

		String[] datetime = bar.formattedTime().split(" ");

		String date = datetime[0];
		String time = datetime[1];

		String year = date.split("-")[0];
		String month = date.split("-")[1];
		String day = date.split("-")[2];

		String hour = time.split(":")[0];
		String minute = time.split(":")[1];
		String second = time.split(":")[2];

		API.txt_time.setText(time);
//		if (second.equals("00") || second.equals("20") || second.equals("40")) {
//		if (second.equals("00") || second.equals("30")) {
//			AutoTrade auto = new AutoTrade(contractPanel, tableData, qty);
			AutoTrade auto = new AutoTrade(autoPanel);
//		}
	}

	public void reqStop() {
		API.INSTANCE.m_controller.cancelRealtimeBars(this);
		System.out.println("Stop Real time");
	}

	@Override
	public void realtimeBar(Bar bar) {
		// TODO Auto-generated method stub
		System.out.println(bar);
		this.bar = bar;
		setRealTime();
	}

}
