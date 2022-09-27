package autotrade;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import javax.security.auth.callback.TextInputCallback;
import javax.swing.table.TableColumn;

import com.ib.client.Contract;
import com.ib.client.Types.BarSize;
import com.ib.client.Types.DurationUnit;
import com.ib.client.Types.SecType;
import com.ib.client.Types.WhatToShow;
import com.ib.controller.ApiController.IHistoricalDataHandler;
import com.ib.controller.Bar;

public class BackTest implements IHistoricalDataHandler {

	TableData tb_pl;
	ArrayList<Bar> bars = new ArrayList<Bar>();
	ContractPanel contractPanel;
	InputText textPL = new InputText("", "");
	BackTestPL pl;

	WhatToShow whatToShow = WhatToShow.TRADES;
	Contract contract = new Contract();

	ArrayList<Bar> barsAdj = new ArrayList<Bar>();

	TableData tableData;

	BarSize barSize;
	int duratation = 2;
	DurationUnit durationUnit;

	BackTest() {

	}

	BackTest(TableData table, TableData tb_pl, ContractPanel contractPanel) {
		this.tableData = table;
		this.contractPanel = contractPanel;
		this.contract = contractPanel.getContact();
		this.duratation = contractPanel.get_duration();
		this.barSize = contractPanel.get_barSize();
		this.durationUnit = contractPanel.get_durationUnit();

		this.tb_pl = tb_pl;
		this.contractPanel = contractPanel;

		this.tableData.setTableSize(400, 250);

		System.out.println("backtest");

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

	public void setTextPL(InputText textPL) {
		this.textPL = textPL;
	}

	public void CalulateIndicator() {

		System.out.println(">>>>>>>>>>>>>>>>>>>>Calculate indicators");
		DataFrame df = new DataFrame();
		df.setHeader(Arrays.asList("time", "open", "high", "low", "close"));
		df.setDataBar(barsAdj);

		// add indicatore with sinal
//		IndyATRStop atrstop = new IndyATRStop();
//		atrstop.setIndy(10, barsAdj);
//		df.addCol(atrstop.getLongStop(), "longstop",tableData);
//		df.addCol(atrstop.getShortgStop(), "shortstop",tableData);
//
//
//		df.addCol(atrstop.getATRStop(), "atrstop",tableData);
//		df.addCol(atrstop.getTrend(), "trend",tableData);
//		df.addCol(atrstop.getSignal(), "signal",tableData);

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

		IndyDonchain donchain = new IndyDonchain();
		donchain.setIndy(48, bars);
		df.addCol(donchain.getUpper(), "Upper", tableData);
		df.addCol(donchain.getLower(), "Lower", tableData);

//		IndyOperation ino2 = new IndyOperation();
//		Series signal2 = ino2.cross(close, donchain.getUpper());
//		df.addCol(signal2, "c>Up", tableData);
		
		
		pl = new BackTestPL(df, tb_pl);

		pl.showPL();
		sum = pl.getSum();
		textPL.setText("" + sum);
//		System.out.println("SUMMMMMMMMMMMMM "+ this.sum);
		tableData.setScrollToButtom();

		df.showChart(barsAdj);
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

	double sum = 0;

	public double getSum() {
		return sum;
	}

	@Override
	public void historicalData(Bar bar) {
		// TODO Auto-generated method stub
		bars.add(bar);
	}

	@Override
	public void historicalDataEnd() {
		// TODO Auto-generated method stub
		System.out.println("historicalDataEnd " + bars.size());
//		setHistorical();
		if (contract.secType().equals(SecType.CASH)) {
			setBarFX();
//			setBarStock();

		} else if (contract.secType().equals(SecType.STK)) {
			setBarStock();

		}

	}

}
