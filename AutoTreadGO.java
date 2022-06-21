package autotrade;

import com.ib.controller.ApiController.IRealTimeBarHandler;
import com.ib.client.Contract;
import com.ib.client.Types.SecType;
import com.ib.client.Types.WhatToShow;
import com.ib.controller.Bar;

public class AutoTreadGO implements IRealTimeBarHandler {
	ContractPanel contractPanel;
	WhatToShow whatToShow = WhatToShow.TRADES;
	Contract contract = new Contract();
	TableData tableData;
	Bar bar;

	public AutoTreadGO(ContractPanel contractPanel, TableData tableData) {
		// TODO Auto-generated constructor stub
		this.tableData = tableData;
		this.contractPanel = contractPanel;
		this.contract = contractPanel.getContact();
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
			AutoTrade auto = new AutoTrade(contractPanel, tableData);
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
