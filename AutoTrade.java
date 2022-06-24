package autotrade;

import com.ib.controller.ApiController.IHistoricalDataHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;

import com.ib.client.Contract;
import com.ib.client.Types.BarSize;
import com.ib.client.Types.DurationUnit;
import com.ib.client.Types.SecType;
import com.ib.client.Types.WhatToShow;
import com.ib.controller.Bar;

public class AutoTrade implements IHistoricalDataHandler {

	Contract contract = new Contract();
	BarSize barSize;
	int duratation = 2;
	DurationUnit durationUnit;
	WhatToShow whatToShow = WhatToShow.TRADES;

	ArrayList<Bar> bars = new ArrayList<Bar>();
	ArrayList<Bar> barsAdj = new ArrayList<Bar>();
	ContractPanel contractPanel;

	TableData tableData;
	String qty = "";

	AutoTrade(ContractPanel contractPanel, TableData tableData, String qty) {
		this.qty = qty;
		this.tableData = tableData;
		this.contractPanel = contractPanel;
		this.contract = contractPanel.getContact();
		this.duratation = contractPanel.get_duration();
		this.barSize = contractPanel.get_barSize();
		this.durationUnit = contractPanel.get_durationUnit();
		tableData.setTableSize(600, 300);

		reqHist();
	}

	public void reqHist() {

		if (contract.secType().equals(SecType.CASH)) {
			whatToShow = whatToShow.MIDPOINT;
//			whatToShow = whatToShow.BID_ASK;
		}
		if (contract.secType().equals(SecType.STK)) {
			whatToShow = whatToShow.TRADES;
		}

		boolean rthOnly = false;
		boolean keepUpToDate = false;

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat form = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		String endDateTime = form.format(cal.getTime());

		System.out.println(">>>>" + contract.symbol() + " " + endDateTime + " " + duratation + " " + durationUnit + " "
				+ barSize + " whatToShow " + whatToShow.toString());
		API.INSTANCE.m_controller.reqHistoricalData(contract, endDateTime, duratation, durationUnit, barSize,
				whatToShow, rthOnly, keepUpToDate, this);
	}

	public void setBarFX() {

		tableData.clearRows();
		for (Bar bar : bars) {
			barsAdj.add(bar);
			tableData.addRow(new String[] { bar.formattedTime(), "" + bar.open(), "" + bar.high(), "" + bar.low(),
					"" + bar.close() });
		}
		CalulateIndicator();
	}

	public void setBarStock() {
		tableData.clearRows();
		for (Bar bar : bars) {

			String datetime = bar.formattedTime();
			String time = datetime.split(" ")[1];
			String hh = time.split(":")[0];
			String mm = time.split(":")[1];
			String ss = time.split(":")[2];

			if (Double.parseDouble(hh) == 20 && Double.parseDouble(mm) >= 30) { // start at 20.30
				addbar(bar);
			} else if (Double.parseDouble(hh) > 20) {
				addbar(bar);
			} else if (Double.parseDouble(hh) == 2 && Double.parseDouble(mm) <= 45) {
				addbar(bar);
			} else if (Double.parseDouble(hh) < 2) {
				addbar(bar);
			}
		}

		CalulateIndicator();
	}

	public void addbar(Bar bar) {
		barsAdj.add(bar);
		tableData.addRow(new String[] { bar.formattedTime(), "" + bar.open(), "" + bar.high(), "" + bar.low(),
				"" + bar.close() });
	}

	public void CalulateIndicator() {
		strategy_SMA();
//		choose one of them
//		strategy_ATR()
	}

	public void strategy_SMA() {
		System.out.println(">>>>>>>>>>>>>>>>>>>>Initialize bars");
		DataFrame df = new DataFrame();
		df.setHeader(Arrays.asList("time", "open", "high", "low", "close"));
		df.setDataBar(barsAdj);

		/// Define indicators
		Series close = df.getColBar("close");
		IndySMA sma1 = new IndySMA();
		sma1.setSMA(5, close);
		df.addCol(sma1.getSMA(), "SMA5", tableData);

		IndySMA sma2 = new IndySMA();
		sma2.setSMA(10, close);
		df.addCol(sma2.getSMA(), "SMA40", tableData);

		IndyOperation ino = new IndyOperation();
		Series signal = ino.cross(sma1.getSMA(), sma2.getSMA());
		df.addCol(signal, "cross", tableData);
		/// calculate signal

		tableData.setScrollToButtom();

		double sig = signal.get(signal.size() - 2); // change -1 to -2 because -1 is not final price
		System.out.println("Signal = " + sig);
		System.out.println("Contract = " + contract.symbol() );
		System.out.println("==== position =====");
		PositionATS postion = API.INSTANCE.pos;

		String sym = contract.symbol();
		
		
		double net = Double.parseDouble(API.txtMoney.getText());
		
		System.out.println("net liquty = " + net);
		
		String data = df.getData().get(df.getData().size()-1).toString();
		System.out.println(data);
		
		double posN = 0;
		for (Map.Entry<String, Position> entry : postion.m_postion.entrySet()) {
			
			posN = entry.getValue().pos();			
			
			Contract con = entry.getValue().contract();
			// choose AAPL or EUR and ckeck secType

//			System.out.println("check "+con.symbol() + " "+  con.currency() +" "+ con.secType() + " "+ qty);
//			System.out.println("with "+contract.symbol() + " "+  contract.currency() +" "+ contract.secType()+" " +posN);
			if(con.symbol().equals(contract.symbol()) && 
					con.currency().equals(contract.currency()) &&
					con.secType().equals(contract.secType())
					) {
				break;
//				System.out.println("Trade "+con.symbol() +" "+con.currency() +" "+ con.secType() + qty);
//				sendOrder(data, sig,posN,Double.parseDouble(qty));
			}

		}
		sendOrder(data, sig,posN,Double.parseDouble(qty));
		
		df.showChart(barsAdj);

	}

	public void sendOrder(String data, double sig,double posN, double qty) {
		System.out.println("== Trade == "+contract.symbol() +" "+contract.currency() +" "+ contract.secType()+ " "+ posN+" " +qty);
		
		System.out.println("=====check condition before send order======");
		
		if (sig == 1 ) {
			PlaceOrderATS p = new PlaceOrderATS();
			
			
			if(posN == 0 ) { // no position then buy to open
				System.out.println("Buy to open");
				p.placeOrder(contract, OrderATS.buyMarket(qty));	
				
				
				LogFile w = new LogFile();
				w.appendPlaceOrder(data+", Buy to Open, "+contract.symbol()+", "+contract.currency()+", "+posN +", "+qty);
				
			}
			else if (posN > 0) { // buy already
				System.out.println("Buy alreay ");

			}
			else if (posN < 0) { // sell to close position
				
				System.out.println("Buy to close");
				p.placeOrder(contract, OrderATS.buyMarket(qty));
				
				LogFile w = new LogFile();
				w.appendPlaceOrder(data+", Buy to Close, "+contract.symbol()+", "+contract.currency()+", "+posN +", "+qty);
				
			}
		}
		else if (sig == -1 ) {
			PlaceOrderATS p = new PlaceOrderATS();
			
			
			if(posN == 0 ) { // no position then sell to open
				System.out.println("sell  to open");
				p.placeOrder(contract, OrderATS.sellMarket(qty));
				
				LogFile w = new LogFile();
				w.appendPlaceOrder(data+", Sell to Open, "+contract.symbol()+", "+contract.currency()+", "+posN +", "+qty);
				
			}
			else if (posN > 0) { // buy to close 
				System.out.println("Sell to close ");
				p.placeOrder(contract, OrderATS.sellMarket(qty));
				
				LogFile w = new LogFile();
				w.appendPlaceOrder(data+", Sell to Close, "+contract.symbol()+", "+contract.currency()+", "+posN +", "+qty);
				
				
			}
			else if (posN < 0) { // sell already
				System.out.println("sell already");
			}
		}
		else {
			System.out.println("============wait==============");
		} 
	}
	
	public void strategy_ATR() {
		System.out.println(">>>>>>>>>>>>>>>>>>>>Initialize bars");
		DataFrame df = new DataFrame();
		df.setHeader(Arrays.asList("time", "open", "high", "low", "close"));
		df.setDataBar(barsAdj);

		System.out.println(">>>>>>>>>>>>>>>>>>>>Calculate indicators");
		IndyATRStop atrstop = new IndyATRStop();
		atrstop.setIndy(10, barsAdj);

		df.addCol(atrstop.getLongStop(), "longstop", tableData);
		df.addCol(atrstop.getShortgStop(), "shortstop", tableData);
		df.addCol(atrstop.getATRStop(), "atrstop", tableData);
		df.addCol(atrstop.getTrend(), "trend", tableData);
		df.addCol(atrstop.getSignal(), "signal", tableData);

		System.out.println(atrstop.getTrend().size());

		tableData.setScrollToButtom();

		/// Check condition

		System.out.println("======== Live order ==========");
		Map<String, LiveOrder> liveorder = API.INSTANCE.liveOrder.getLiveOrder();
		for (Map.Entry<String, LiveOrder> entry : liveorder.entrySet()) {
			System.out.println(entry.getKey() + " :" + entry.getValue().permId + " " + entry.getValue().remaining);
		}

		System.out.println("======== Live open order ==========");
		Map<String, LiveOpenOrder> liveOpenorder = API.INSTANCE.liveOrder.getLiveOpenOrder();

		for (Map.Entry<String, LiveOpenOrder> entry : liveOpenorder.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue().contract.symbol() + " "
					+ entry.getValue().order.permId() + " ");// + liveorder.get(entry.getKey()).remaining);
		}

		System.out.println("==== position =====");
		PositionATS postion = API.INSTANCE.pos;

		String sym = contract.symbol();
		for (Map.Entry<String, Position> entry : postion.m_postion.entrySet()) {
			String posSymbol = entry.getValue().contract().symbol();
			double posN = entry.getValue().pos();

			if (posSymbol.equals(contract.symbol())) {

				if (posN == 0) { // wait for open a position
					System.out.println("NO position ");

					double nOrder = getLiveOpenOrder(sym);
					if (nOrder == 0) {
						System.out.println("  No Live order xxx");
						System.out.println("    Open order");

						System.out.println("====== place open order ======");
						if (atrstop.getSignal().get(atrstop.getSignal().size() - 1) == 1) {
							System.out.println("    buy");

//							PlaceOrderATS p = new PlaceOrderATS();
//							OrderATS oo = new OrderATS();
//							p.placeOrder(contract, OrderATS.));
						} else if (atrstop.getSignal().get(atrstop.getSignal().size() - 1) == -1) {
//							System.out.println("sell");
//							PlaceOrderATS p = new PlaceOrderATS();
//							OrderATS oo = new OrderATS();
//							p.placeOrder(contract, OrderATS.sellMarket(20000));
						} else {
							System.out.println("       Wait");
						}
					} else {
						System.out.println("  Have Live order");
						System.out.println("    there are some Live order " + nOrder);
						System.out.println("      Wait");
					}
				} else { // position already open, then wait for close the position
					System.out.println("Have position xxxx");

					double nOrder = getLiveOpenOrder(sym);
					if (nOrder == 0) {
						System.out.println("  No Live order");
						System.out.println("    close order");

						System.out.println("====== place close order ======");
						if (atrstop.getSignal().get(atrstop.getSignal().size() - 1) == 1) {
//							System.out.println("buy");
//							PlaceOrderATS p = new PlaceOrderATS();
//							OrderATS oo = new OrderATS();
//							p.placeOrder(contract, OrderATS.buyMarket(20000));
						} else if (atrstop.getSignal().get(atrstop.getSignal().size() - 1) == -1) {
//							System.out.println("sell");
//							PlaceOrderATS p = new PlaceOrderATS();
//							OrderATS oo = new OrderATS();
//							p.placeOrder(contract, OrderATS.sellMarket(20000));
						} else {
							System.out.println("       Wait");
						}
					} else {
						System.out.println("  Have Live order");
						System.out.println("    there are some Live order " + nOrder);
						System.out.println("      Wait");
					}
				}

			}
		}

		///
		df.showChart(barsAdj);
	}

	public double getLiveOpenOrder(String symbol) {
		Map<String, LiveOrder> liveorder = API.INSTANCE.liveOrder.getLiveOrder();
		Map<String, LiveOpenOrder> liveOpenorder = API.INSTANCE.liveOrder.getLiveOpenOrder();

		if (liveorder.size() > 0) {
			for (Map.Entry<String, LiveOpenOrder> entry : liveOpenorder.entrySet()) {

				if (entry.getValue().contract.symbol().equals(symbol)) {

					return liveorder.get(entry.getKey()).remaining;

				}

			}
		}
		return 0;
	}

	public void getorder() {

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

		if (contract.secType().equals(SecType.CASH)) {
			setBarFX();
//			setBarStock();

		} else if (contract.secType().equals(SecType.STK)) {
			setBarStock();

		}

	}
}
