package autotrade;

import com.ib.controller.ApiController.IRealTimeBarHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.ib.client.Contract;
import com.ib.client.Types.BarSize;
import com.ib.client.Types.DurationUnit;
import com.ib.client.Types.WhatToShow;
import com.ib.controller.Bar;

public class RealTime implements IRealTimeBarHandler {
//	ArrayList<Bar> bars = new ArrayList<Bar>();
	WhatToShow whatToShow = WhatToShow.MIDPOINT;
	boolean rthOnly = false;
	RealTime bid;
	RealTime ask;

	Contract contract;
	BarSize barSize;

	
	ContractPanel contractPanel ;
	RealTime(Contract contract, BarSize barSize ) {
		this.barSize = barSize;
		this.contract = contract;
		reqRealTimeBar();
	}

	RealTime(ContractPanel contractPanel){
		this.contractPanel = contractPanel;
		reqRealTimeBar();
	}

	public void reqRealTimeBar() {
//		System.out.println("===============Start Real time==============");
//		System.out.println(contract);
//		System.out.println(whatToShow);
//		System.out.println("============================================");
		API.INSTANCE.m_controller.reqRealTimeBars(contractPanel.getContact(), contractPanel.get_whatToShow(), rthOnly, this);

	}

	public void reqStop() {
		API.INSTANCE.m_controller.cancelRealtimeBars(this);
		System.out.println("Stop Real time");
	}

	public void setRealTime(Bar bar) {
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

//		if (second.equals("20") || second.equals("40") || second.equals("00")) {
//			gethist();
//		}
	}

	public void gethist() {
		HistoryATS hist = new HistoryATS(contractPanel);

		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat form = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		String formatted = form.format(cal.getTime());
		System.out.println("Date time :::: " + formatted);
		String endDateTime = formatted; // "20220518 09:46:20";
		int duration = contractPanel.get_duration();
		DurationUnit durationUnit = contractPanel.get_durationUnit();
//		BarSize barSize = BarSize._15_mins;
//		
		
	
		System.out.println("==============req hist contract============");
		System.out.println(contract);
		
		hist.reqHistorical(contract, endDateTime, duration, durationUnit, barSize);



		System.out.println("req Historical");
	}

	@Override
	public void realtimeBar(Bar bar) {
		// TODO Auto-generated method stub

		System.out.println(whatToShow.toString() + "> " + bar);
//		bars.add(bar);

		setRealTime(bar);
	}

}
