package autotrade;

import java.util.ArrayList;
import java.util.Arrays;

import com.ib.controller.Bar;

public class Strategy {
	public void setBar(ArrayList<Bar> bars) {

		for (Bar bar : bars) {
//			API.tb_bar.addRow(new String[] { bar.formattedTime(), "" + bar.open(), "" + bar.high(), "" + bar.low(),
//					"" + bar.close() });

		}
		System.out.println(">>>>>>>>>>>>>>>>>>>>Calculate indicators");
		DataFrame df = new DataFrame();

		df.setHeader(Arrays.asList("time", "open", "high", "low", "close"));
		df.setDataBar(bars);

////////////////
		Series close = df.getColBar("close");
		IndySMA s5 = new IndySMA();
		s5.setSMA(5, close);
		df.addCol(s5.getSMA(), "SMA5");
		//////////////////////////////////////

		/////////////// SMA //////////////////
		// 1. set value

		// 2. call indicator
		IndySMA s21 = new IndySMA();
		s21.setSMA(21, close);

		// 3. add to dataframe
		df.addCol(s21.getSMA(), "SMA21");

		///// heieken
//		indyHeikenAshi h = new indyHeikenAshi();
//		h.setHeiken(df.bars);
//		df.addCol(h.HAO(), "HAO");
//		df.addCol(h.HAH(), "HAH");
//		df.addCol(h.HAL(), "HAL");
//		df.addCol(h.HAC(), "HAC");
		///////

		/// ATR
//		indyATR atr = new indyATR();
//		atr.setATR(df.bars);
//		df.addCol(atr.getATR(), "ATR");
//		df.addCol(atr.getTR(), "TR");

		//////////////// write condition here //////////////

		///// calulate sig

//		ArrayList<Double> s = new ArrayList<Double>();
//		boolean sig  = false ;
//		for (int i = 0; i < df.getData().size(); i++) {
//			df.getCol("close")
//		}
//		df.addCol(s, name);
		//////////////// if condition = true then place order here /////

		boolean sigbuy = false;
		sigbuy = s5.getBar(1) < s21.getBar(1) && s5.getBar(0) > s21.getBar(0);
		System.out.println("sig = " + sigbuy);
		if (sigbuy) {

		}
//		s5.getBar(1);
//		System.out.println("SMA5 = "+s5);

		/////////////// end////////////////////////////
//		df.showChart();
	}
}
