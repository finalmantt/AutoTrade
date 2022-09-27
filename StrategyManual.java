package autotrade;

import java.util.Arrays;

public class StrategyManual {

	AutoTrade autoTrade = null;
	DataFrame df = new DataFrame();
	double sig = 0;
	String data= "";
	StrategyManual(AutoTrade autoTrade) {
		this.autoTrade = autoTrade;

	}

	public void runStrategy() {
		System.out.println(">>>>>>>>>>>>>>>>>>>>Initialize bars");
		DataFrame df = new DataFrame();
		df.setHeader(Arrays.asList("time", "open", "high", "low", "close"));
		df.setDataBar(autoTrade.barsAdj);

		/// Define indicators
		Series close = df.getColBar("close");
		IndySMA sma1 = new IndySMA();
		sma1.setSMA(5, close);
		df.addCol(sma1.getSMA(), "SMA5", autoTrade.tableData);

		IndySMA sma2 = new IndySMA();
		sma2.setSMA(10, close);
		df.addCol(sma2.getSMA(), "SMA10", autoTrade.tableData);

		IndyOperation ino = new IndyOperation();
		Series signal = ino.cross(sma1.getSMA(), sma2.getSMA());
		df.addCol(signal, "cross", autoTrade.tableData);
		/// calculate signal

		IndyDonchain donchain = new IndyDonchain();
		donchain.setIndy(48, autoTrade.bars);
		df.addCol(donchain.getUpper(), "Upper", autoTrade.tableData);
		df.addCol(donchain.getLower(), "Lower", autoTrade.tableData);

		autoTrade.tableData.setScrollToButtom();

		double sig = signal.get(signal.size() - 2); // change -1 to -2 because -1 is not final price
		System.out.println("Signal = " + sig);
		System.out.println("Contract = " + autoTrade.contract.symbol());
		System.out.println("==== position =====");

		double net = Double.parseDouble(API.txtMoney.getText());

		System.out.println("net liquty = " + net);

		// fix sig from textbox
		sig = Integer.parseInt(autoTrade.autoPanel.txtSig.getText());
		
		// data for write in LogFile
		data = df.getData().get(df.getData().size() - 1).toString();
		System.out.println(data);

		/// send order
		double posN = 0;
		OrderSend orderSend = new OrderSend(autoTrade);
		posN = orderSend.getPosition();
		orderSend.createOrder(data, sig, posN, Double.parseDouble(autoTrade.qty));
		df.showChart(autoTrade.barsAdj);
	}

	
}
