package autotrade;

import java.awt.Dimension;
import java.awt.List;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.ib.client.Contract;
import com.ib.client.Types.BarSize;
import com.ib.client.Types.DurationUnit;
import com.ib.client.Types.WhatToShow;
import com.ib.controller.ApiController.IHistoricalDataHandler;
import com.ib.controller.Bar;

import samples.testbed.contracts.ContractSamples;

public class HistoryATS implements IHistoricalDataHandler {
//	private final Contract m_contract = new Contract();
	ArrayList<Bar> rows = new ArrayList<Bar>();
	ArrayList<Double> open = new ArrayList<Double>();

	HistoryATS() {
		// from Historical to placeOrder
		System.out.println("Create Historical");

	}

	public void reqHistorical() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -6);
		SimpleDateFormat form = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		String formatted = form.format(cal.getTime());

		Contract contract = new Contract();
		contract.symbol("EUR");
		contract.secType("CASH");
		contract.currency("USD");
		contract.exchange("IDEALPRO");

		String endDateTime = "20120101 12:00:00";
		int duration = 2;
		DurationUnit durationUnit = DurationUnit.WEEK;
		BarSize barSize = BarSize._1_day;
		WhatToShow whatToShow = WhatToShow.MIDPOINT;
		boolean rthOnly = false;
		boolean keepUpToDate = false;
//	        handler = this;
		API.INSTANCE.m_controller.reqHistoricalData(contract, endDateTime, duration, durationUnit, barSize, whatToShow,
				rthOnly, keepUpToDate, this);

	}

	public void setBar() {
		DataFrame df = new DataFrame();

		df.setHeader(Arrays.asList("time", "open", "high", "low", "close"));
		df.setDataBar(rows);

		/////////////// SMA //////////////////
		// 1. set value
		Series close = df.getColBar("close");

		// 2. call indicator
		indySMA s = new indySMA();
		s.setSMA(2, close);

		// 3. add to dataframe
		df.addCol(s.getSMA(), "SMA");
		//////////////////////////////////////

		///// heieken
		indyHeikenAshi h = new indyHeikenAshi();
		h.setHeiken(df.bars);
		df.addCol(h.HAO(), "HAO");
		df.addCol(h.HAH(), "HAH");
		df.addCol(h.HAL(), "HAL");
		df.addCol(h.HAC(), "HAC");
		///////

		/// ATR
		indyATR atr = new indyATR();
		atr.setATR(df.bars);
		df.addCol(atr.getATR(), "ATR");
//		df.addCol(atr.getTR(), "TR");
		////

//		df.showTable();
//		df.showChart();
		
		if (API.stepAuto == 1) {
			API.delay(10,"wait for 10 secons before placed order");
			PlaceOrderATS place = new PlaceOrderATS();
			place.reqPlaceOrderExample();
			
			API.delay(5,"wait for 5 secons after placed order");

//				PositionATS p = new PositionATS();
//				p.reqPosition();
		}

	}

	@Override
	public void historicalData(Bar bar) {
		// TODO Auto-generated method stub
		open.add(bar.open());
//		System.out.println("" + bar);
		rows.add(bar);
//		Chart c = new Chart(rows);
	}

	@Override
	public void historicalDataEnd() {
		// TODO Auto-generated method stub
		System.out.println("historicalDataEnd");
		setBar();
		///// auto trade

	}
}
