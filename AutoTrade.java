package autotrade;

import com.ib.controller.ApiController.IHistoricalDataHandler;
import com.ib.controller.ApiController.ILiveOrderHandler;
import com.ib.controller.ApiController.IPositionHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;

import com.ib.client.Contract;
import com.ib.client.Order;
import com.ib.client.OrderState;
import com.ib.client.OrderStatus;
import com.ib.client.Types.BarSize;
import com.ib.client.Types.DurationUnit;
import com.ib.client.Types.SecType;
import com.ib.client.Types.WhatToShow;
import com.ib.controller.Bar;
/// AutotradePanel -> AutoTradeGo -> AutoTrade
public class AutoTrade implements IHistoricalDataHandler, IPositionHandler , ILiveOrderHandler {

	public Contract contract = new Contract();
	BarSize barSize;
	int duratation = 2;
	DurationUnit durationUnit;
	WhatToShow whatToShow = WhatToShow.TRADES;

	ArrayList<Bar> bars = new ArrayList<Bar>();
	ArrayList<Bar> barsAdj = new ArrayList<Bar>();
	ContractPanel contractPanel;

	TableData tableData;
	String qty = "";

	public AutotradePanel autoPanel;
	AutoTrade(AutotradePanel autoPanel){
		this.autoPanel = autoPanel;
		this.qty = autoPanel.qty;
		this.tableData = autoPanel.tb_autoTrade;
		this.contractPanel = autoPanel.contractPanel;
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
//		SimpleDateFormat form = new SimpleDateFormat("yyyymmdd hh:mm:ss");
		
		String endDateTime = form.format(cal.getTime());

		System.out.println(">>>>" + contract.symbol() + " " + endDateTime + " " + duratation + " " + durationUnit + " "
				+ barSize + " whatToShow " + whatToShow.toString());
		// ===> historicalData(Bar bar) --> historicalEnd()
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
//		choose one of them
		StrategyManual sm = new StrategyManual(this);
//		StrategySMA sm = new StrategySMA(this);
//		StrategyATR sm = new StrategyATR(this);
		
		sm.runStrategy();



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
		System.out.println("historicalDataEnd\n\n");

		if (contract.secType().equals(SecType.CASH)) {
			setBarFX();
//			setBarStock();

		} else if (contract.secType().equals(SecType.STK)) {
			setBarStock();

		}

	}

	@Override
	public void position(String account, Contract contract, double pos, double avgCost) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void positionEnd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void openOrder(Contract contract, Order order, OrderState orderState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void openOrderEnd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void orderStatus(int orderId, OrderStatus status, double filled, double remaining, double avgFillPrice,
			int permId, int parentId, double lastFillPrice, int clientId, String whyHeld, double mktCapPrice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handle(int orderId, int errorCode, String errorMsg) {
		// TODO Auto-generated method stub
		
	}
}
