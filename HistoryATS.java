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
import com.ib.client.Types.SecType;
import com.ib.client.Types.WhatToShow;
import com.ib.controller.ApiController.IHistoricalDataHandler;
import com.ib.controller.Bar;

import samples.testbed.contracts.ContractSamples;

public class HistoryATS implements IHistoricalDataHandler {
//	private final Contract m_contract = new Contract();
	ArrayList<Bar> bars = new ArrayList<Bar>();
	ArrayList<Bar> barsAdj = new ArrayList<Bar>();
	ArrayList<Double> open = new ArrayList<Double>();
	Contract contract = new Contract();
	String symbol = "EUR";
	WhatToShow whatToShow = WhatToShow.TRADES;
	BarSize barSize;
	
//	HistoryATS() {
//		// from Historical to placeOrder
//		API.tb_bar.clear();
//
//		System.out.println("Create Historical");
//
//	}

	HistoryATS(ContractPanel contractPanel){
		this.contract = contractPanel.getContact();
		this.barSize = contractPanel.get_barSize();
		int duration = contractPanel.get_duration();
		DurationUnit durationUnit = contractPanel.get_durationUnit();
		
//		HistoryATS hist = new HistoryATS();

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat form = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		String formatted = form.format(cal.getTime());
		System.out.println("Date time :::: " + formatted);
		String endDateTime = formatted; // "20220518 09:46:20";
		


		System.out.println("==============req hist contract============");
		System.out.println(contract);

		reqHistorical(contract, endDateTime, duration, durationUnit, barSize);
	}
//	HistoryATS(Contract contract, BarSize barSize) {
//		this.contract = contract;
//		this.barSize = barSize;
////		HistoryATS hist = new HistoryATS();
//
//		Calendar cal = Calendar.getInstance();
//		SimpleDateFormat form = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
//		String formatted = form.format(cal.getTime());
//		System.out.println("Date time :::: " + formatted);
//		String endDateTime = formatted; // "20220518 09:46:20";
//		int duration = 2;
//		DurationUnit durationUnit = DurationUnit.DAY;
////		BarSize barSize = BarSize._15_mins;
//
//		System.out.println("==============req hist contract============");
//		System.out.println(contract);
//
//		reqHistorical(contract, endDateTime, duration, durationUnit, barSize);
//	}


	public void reqHistorical(Contract contract, String endDateTime, int duration, DurationUnit durationUnit,
			BarSize barSize) {
		this.contract = contract;
		if (contract.secType().equals(SecType.CASH)) {
			whatToShow = whatToShow.MIDPOINT;
		}
		if (contract.secType().equals(SecType.STK)) {
			whatToShow = whatToShow.TRADES;
		}

		boolean rthOnly = false;
		boolean keepUpToDate = false;

		System.out.println(">>>>" + endDateTime + " " + duration + " " + durationUnit + " " + barSize);
		API.INSTANCE.m_controller.reqHistoricalData(contract, endDateTime, duration, durationUnit, barSize, whatToShow,
				rthOnly, keepUpToDate, this);

	}

	public void setBarFX() {
		System.out.println("FXXXXXXXXXXXXXXXXXXX");
		for (Bar bar : bars) {
			API.tb_bar.addRow(new String[] { bar.formattedTime(), "" + bar.open(), "" + bar.high(), "" + bar.low(),
					"" + bar.close() });

		}
		System.out.println(">>>>>>>>>>>>>>>>>>>>Calculate indicators");
		DataFrame df = new DataFrame();

		df.setHeader(Arrays.asList("time", "open", "high", "low", "close"));
		df.setDataBar(bars);

////////////////
//		Series close = df.getColBar("close");
//		IndySMA s5 = new IndySMA();
//		s5.setSMA(5, close);
//		df.addCol(s5.getSMA(), "SMA5");
//		//////////////////////////////////////
//
//		/////////////// SMA //////////////////
//		// 1. set value
//
//		// 2. call indicator
//		IndySMA s21 = new IndySMA();
//		s21.setSMA(21, close);
//
//		// 3. add to dataframe
//		df.addCol(s21.getSMA(), "SMA21");

		IndyATRStop atrstop = new IndyATRStop();
		atrstop.setIndy(10, bars);
//		System.out.println(atrstop.getSignal().toStringArray());
		df.addCol(atrstop.getLongStop(), "longstop",API.tb_bar);
		df.addCol(atrstop.getShortgStop(), "shortstop",API.tb_bar);

//		df.addCol(atrstop.getTrend(), "trend");
		df.addCol(atrstop.getATRStop(), "atrstop",API.tb_bar);
		df.addCol(atrstop.getTrend(), "trend",API.tb_bar);
		df.addCol(atrstop.getSignal(), "signal",API.tb_bar);

		/// donchain
		IndyDonchain donchian = new IndyDonchain();
		donchian.setIndy(10, bars);
//
		df.addCol(donchian.getUpper(), "upper",API.tb_bar);
		df.addCol(donchian.getLower(), "lower",API.tb_bar);
		
		
		System.out.println(atrstop.getTrend().size());
//		boolean sigbuy = false;
//		sigbuy = s5.getBar(1) < s21.getBar(1) && s5.getBar(0) > s21.getBar(0);
//		System.out.println("sig = " + sigbuy);

//		if(position != 0 && liveorder == 0 ) {
//			trade
//		}
		double postion = API.INSTANCE.pos.getPostion();
		System.out.println("postion :" + postion);

//		double liveorder = API.INSTANCE.liveOrder.getLiveOrder();
		if (atrstop.getSignal().get(atrstop.getSignal().size() - 1) == 1) {
			System.out.println("buy");
		} else if (atrstop.getSignal().get(atrstop.getSignal().size() - 1) == -1) {
			System.out.println("sell");
		} else {
			System.out.println("Wait");
		}
//		s5.getBar(1);
//		System.out.println("SMA5 = "+s5);

		/////////////// end////////////////////////////

//		System.out.println("df.getBar().size() :: " + df.getBar().size());
//		df.showChart(bars);
	}

	public void setBar() {
		for (Bar bar : bars) {
//			API.tb_bar.addRow(new String[] { bar.formattedTime(), "" + bar.open(), "" + bar.high(), "" + bar.low(),
//					"" + bar.close() });

//			"20220518 09:46:20"
			String datetime = bar.formattedTime();
			String time = datetime.split(" ")[1];
			String hh = time.split(":")[0];
			String mm = time.split(":")[1];
			String ss = time.split(":")[2];
//			System.out.println(datetime);
//			System.out.println(Double.parseDouble(hh) + " ::: " + Double.parseDouble(mm));

			if (Double.parseDouble(hh) == 20 && Double.parseDouble(mm) >= 30) { // start at 20.30
				barsAdj.add(bar);
				API.tb_bar.addRow(new String[] { bar.formattedTime(), "" + bar.open(), "" + bar.high(), "" + bar.low(),
						"" + bar.close() });
			} else if (Double.parseDouble(hh) > 20) {

				barsAdj.add(bar);
				API.tb_bar.addRow(new String[] { bar.formattedTime(), "" + bar.open(), "" + bar.high(), "" + bar.low(),
						"" + bar.close() });
			} else if (Double.parseDouble(hh) == 2 && Double.parseDouble(mm) <= 45) {
				barsAdj.add(bar);
				API.tb_bar.addRow(new String[] { bar.formattedTime(), "" + bar.open(), "" + bar.high(), "" + bar.low(),
						"" + bar.close() });
			} else if (Double.parseDouble(hh) < 2) {
				barsAdj.add(bar);
				API.tb_bar.addRow(new String[] { bar.formattedTime(), "" + bar.open(), "" + bar.high(), "" + bar.low(),
						"" + bar.close() });
			}

		}
		System.out.println(">>>>>>>>>>>>>>>>>>>>Calculate indicators");
		DataFrame df = new DataFrame();

		df.setHeader(Arrays.asList("time", "open", "high", "low", "close"));
		df.setDataBar(barsAdj);

////////////////
//		Series close = df.getColBar("close");
//		IndySMA s5 = new IndySMA();
//		s5.setSMA(5, close);
//		df.addCol(s5.getSMA(), "SMA5");
//		//////////////////////////////////////
//
//		/////////////// SMA //////////////////
//		// 1. set value
//
//		// 2. call indicator
//		IndySMA s21 = new IndySMA();
//		s21.setSMA(21, close);
//
//		// 3. add to dataframe
//		df.addCol(s21.getSMA(), "SMA21");

		IndyATRStop atrstop = new IndyATRStop();
		atrstop.setIndy(10, barsAdj);
//		System.out.println(atrstop.getSignal().toStringArray());
		df.addCol(atrstop.getLongStop(), "longstop",API.tb_bar);
		df.addCol(atrstop.getShortgStop(), "shortstop",API.tb_bar);

//		df.addCol(atrstop.getTrend(), "trend");
		df.addCol(atrstop.getATRStop(), "atrstop",API.tb_bar);
		df.addCol(atrstop.getTrend(), "trend",API.tb_bar);
		df.addCol(atrstop.getSignal(), "signal",API.tb_bar);

		System.out.println(atrstop.getTrend().size());
//		boolean sigbuy = false;
//		sigbuy = s5.getBar(1) < s21.getBar(1) && s5.getBar(0) > s21.getBar(0);
//		System.out.println("sig = " + sigbuy);

//		if(position != 0 && liveorder == 0 ) {
//			trade
//		}
		double postion = API.INSTANCE.pos.getPostion();
		System.out.println("postion :" + postion);

//		double liveorder = API.INSTANCE.liveOrder.getLiveOrder();
		if (atrstop.getSignal().get(atrstop.getSignal().size() - 1) == 1) {
			System.out.println("buy");
		} else if (atrstop.getSignal().get(atrstop.getSignal().size() - 1) == -1) {
			System.out.println("sell");
		} else {
			System.out.println("Wait");
		}
//		s5.getBar(1);
//		System.out.println("SMA5 = "+s5);

		/////////////// end////////////////////////////
		System.out.println("df.getBar().size() :: " + df.getBar().size());
		df.showChart(barsAdj);
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
		System.out.println("contract.secType() " + contract.secType());
		if (contract.secType().equals(SecType.CASH)) {
			setBarFX();
			
		} else if (contract.secType().equals(SecType.STK)) {
			setBar();
		}

		API.INSTANCE.m_controller.cancelHistoricalData(this);

	}

}
