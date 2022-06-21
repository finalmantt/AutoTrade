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

public class RealTimeBack implements IRealTimeBarHandler {
	ArrayList<Bar> bars = new ArrayList<Bar>();
	WhatToShow whatToShow = WhatToShow.MIDPOINT;
	boolean rthOnly = false;
	RealTimeBack bid;
	RealTimeBack ask;
	
	Contract contract = new Contract();

	RealTimeBack(Contract contract) {
		this.contract = contract;

		reqRealTimeBar();
	}

	
	RealTimeBack(WhatToShow whatToShow) {
		this.whatToShow = whatToShow;
	}

	public void reqRealTimeBar() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -6);
		SimpleDateFormat form = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		String formatted = form.format(cal.getTime());

//		contract.symbol("EUR");
//		contract.secType("CASH");
//		contract.currency("USD");
//		contract.exchange("IDEALPRO");

		
//		IDEALPRO
//		System.out.println(contract);
//		System.out.println(contract);
		System.out.println("Start Real Ask");

		bid = new RealTimeBack(WhatToShow.BID);
		ask = new RealTimeBack(WhatToShow.ASK);

//		bid.req();
		ask.req();

	}

	public void req( ) {
//		System.out.println(contract);
		API.INSTANCE.m_controller.reqRealTimeBars(contract, whatToShow, rthOnly, this);
	}

	public void reqStop() {
		API.INSTANCE.m_controller.cancelRealtimeBars(bid);
		API.INSTANCE.m_controller.cancelRealtimeBars(ask);

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
		if (whatToShow.equals(WhatToShow.BID)) {
//			System.out.println("BIDDDDDDDDDDDDDDDD");
		}
		if (whatToShow.equals(WhatToShow.ASK)) {


			
//
//			if (second.equals("10") || second.equals("30") || second.equals("50")) {
//				gethist(year,month,day,hour,minute,second);
//				System.out.println("place order");
//
//				PlaceOrderATS place = new PlaceOrderATS();
//				place.placeOrder(contract, OrderATS.buyMarket(20000));
			}
//			if (second.equals("00")){
			if (second.equals("20") || second.equals("40") || second.equals("00")) {
				
				
				gethist(year,month,day,hour,minute,second);
				System.out.println("place order");

//				PlaceOrderATS place = new PlaceOrderATS();
//				place.placeOrder(contract, OrderATS.sellMarket(20000));
//			}

		}
	}

	public void gethist(String year, String month, String day, String hour, String minute, String second) {
		HistoryATS hist = new HistoryATS();
		
		
		String bartime = year + month + day + " " + hour + ":" + minute + ":"+"00"; // "20120101 12:00:00";
		System.out.println("bartime "+bartime);
		int duration = 5;
		DurationUnit durationUnit = DurationUnit.DAY;
		BarSize barSize = BarSize._15_mins;

		System.out.println(bartime);

//		hist.reqHistorical(contract,bartime,duration,durationUnit,barSize);
		hist.reqHistorical() ;

		System.out.println("req Historical");
	}
	@Override
	public void realtimeBar(Bar bar) {
		// TODO Auto-generated method stub

		System.out.println(whatToShow.toString() + "> " + bar);
		bars.add(bar);
		
		setRealTime(bar);
	}
	

}
