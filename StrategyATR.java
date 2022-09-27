package autotrade;

import java.util.Arrays;
import java.util.Map;

public class StrategyATR {

	AutoTrade autoTrade = null;
	DataFrame df = new DataFrame();
	double sig = 0;

	StrategyATR(AutoTrade autoTrade) {
		this.autoTrade = autoTrade;

	}

	public void runStrategy() {
		System.out.println(">>>>>>>>>>>>>>>>>>>>Initialize bars");

		df.setHeader(Arrays.asList("time", "open", "high", "low", "close"));
		df.setDataBar(autoTrade.barsAdj);

		System.out.println(">>>>>>>>>>>>>>>>>>>>Calculate indicators");
		IndyATRStop atrstop = new IndyATRStop();
		atrstop.setIndy(10, autoTrade.barsAdj);

		df.addCol(atrstop.getLongStop(), "longstop", autoTrade.tableData);
		df.addCol(atrstop.getShortgStop(), "shortstop", autoTrade.tableData);
		df.addCol(atrstop.getATRStop(), "atrstop", autoTrade.tableData);
		df.addCol(atrstop.getTrend(), "trend", autoTrade.tableData);
		df.addCol(atrstop.getSignal(), "signal", autoTrade.tableData);

		System.out.println(atrstop.getTrend().size());

		autoTrade.tableData.setScrollToButtom();

//		double sig = signal.get(signal.size() - 2); // change -1 to -2 because -1 is not final price
		Series signal = atrstop.getSignal();
		sig = signal.get(signal.size() - 2);
		System.out.println("Signal = " + sig);
		System.out.println("Contract = " + autoTrade.contract.symbol());
		System.out.println("==== position =====");

		// data for write in LogFile
		String data = df.getData().get(df.getData().size() - 1).toString();
		System.out.println(data);

		/// send order
		double posN = 0;
		OrderSend orderSend = new OrderSend(autoTrade);
		posN = orderSend.getPosition();
		orderSend.createOrder(data, sig, posN, Double.parseDouble(autoTrade.qty));
		df.showChart(autoTrade.barsAdj);
	}

}
