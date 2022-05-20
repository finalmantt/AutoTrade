package autotrade;

import java.awt.Dimension;
import java.awt.List;
import java.awt.Rectangle;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.ib.client.Contract;
import com.ib.client.Types.BarSize;
import com.ib.client.Types.DurationUnit;
import com.ib.client.Types.WhatToShow;
import com.ib.controller.ApiController.IHistoricalDataHandler;
import com.ib.controller.Bar;

import samples.testbed.contracts.ContractSamples;

public class HistoryATS2 implements IHistoricalDataHandler {
//	private final Contract m_contract = new Contract();
	ArrayList<Bar> bars = new ArrayList<Bar>();
	ArrayList<Double> open = new ArrayList<Double>();
	Contract contract = new Contract();
	String symbol = "EUR";

	TableData hist = new TableData();
	HistoryATS2() {
		// from Historical to placeOrder
//		API.tb_bar.clear();
		System.out.println("Create Historical");

	}

	HistoryATS2(String symbol) {
		this.symbol = symbol;
	}

	public void reqHistorical() {

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -6);
		SimpleDateFormat form = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		String formatted = form.format(cal.getTime());

//		Contract contract = new Contract();
		contract.symbol(symbol);
		contract.secType("CASH");
		contract.currency("USD");
		contract.exchange("IDEALPRO");
//		20220510 17:41:00 1 DAY 15 mins
		String endDateTime = "20220510 17:00:00";
//		int duration = 2;
//		DurationUnit durationUnit = DurationUnit.WEEK;
//		BarSize barSize = BarSize._1_day;
		int duration = 2;
		DurationUnit durationUnit = DurationUnit.DAY;
		BarSize barSize = BarSize._15_mins;

		WhatToShow whatToShow = WhatToShow.MIDPOINT;
		boolean rthOnly = false;
		boolean keepUpToDate = false;
		
		System.out.println(">>>>" + endDateTime + " " + duration + " " + durationUnit + " " + barSize);
		API.INSTANCE.m_controller.reqHistoricalData(ContractATS.getContractStock("AAPL"), endDateTime, duration,
				durationUnit, barSize, whatToShow, rthOnly, keepUpToDate, this);

	}

	public void reqHistorical(Contract contract, String endDateTime, int duration, DurationUnit durationUnit,
			BarSize barSize) {

		WhatToShow whatToShow = WhatToShow.MIDPOINT;
		boolean rthOnly = false;
		boolean keepUpToDate = false;

		System.out.println(">>>>" + endDateTime + " " + duration + " " + durationUnit + " " + barSize);

		API.INSTANCE.m_controller.reqHistoricalData(contract, endDateTime, duration, durationUnit, barSize, whatToShow,
				rthOnly, keepUpToDate, this);

	}

	public void setBar() {
		for (Bar bar : bars) {
//			API.tb_bar.addRow(new String[] { bar.formattedTime(), "" + bar.open(), "" + bar.high(), "" + bar.low(),
//					"" + bar.close() });

		}
		System.out.println(">>>>>>>>>>>>>>>>>>>>Calculate indicators");
		DataFrame df = new DataFrame();

		df.setHeader(Arrays.asList("time", "open", "high", "low", "close"));
		df.setDataBar(bars);

		df.showTable();
////////////////
		Series close = df.getColBar("close");
		IndySMA s5 = new IndySMA();
		s5.setSMA(5, close);
		
		df.addCol(s5.getSMA(), "SMA5");
		
		//////////////////////////////////////

	
	}

	@Override
	public void historicalData(Bar bar) {
		// TODO Auto-generated method stub
		bars.add(bar);

	}

	@Override
	public void historicalDataEnd() {
		// TODO Auto-generated method stub
		System.out.println("historicalDataEnd");
		setBar();
//		strategy();
//		API.INSTANCE.m_controller.cancelHistoricalData(API.hist);

	}
	
	public void strategy() {
		Strategy s = new Strategy();
		s.setBar(bars);
	}

}
