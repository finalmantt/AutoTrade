package autotrade;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.ib.client.Contract;
import com.ib.controller.Bar;

public class TestIndy {
	public static void main(String args[]) {

		// ArrayList<ArrayList<Double>> data = new ArrayList<ArrayList<Double>>();
		ArrayList<Bar> bars = new ArrayList<>();

		DataFrame df = new DataFrame();
		bars = df.getBarSample();
		df.setDataBar(bars);

		///// sma
//		 Series close = df.getColBar("close");
//		 indySMA sma = new indySMA();
//		 sma.setSMA(5, close);
//		 df.printSeries(sma.getSMA());

//		Series close = df.getColBar("close");
//		IndyRMA rma = new IndyRMA();
//		rma.setRMA(5, close);
//		DataFrame.printSeries(rma.getRMA());
		// atr///
//		 IndyATR atr = new IndyATR();
//		 atr.setATR(bars);
//		 df.printSeries(atr.getATR(10,"rma"));
		//////
		
		IndyATRStop atrstop = new IndyATRStop();
		atrstop.setIndy(10, bars);
		System.out.println(atrstop.getSignal().toStringArray());
//		df.printSeries(atrstop.getSignal());
		
	
		/// heiken ashi

//		indyHeikenAshi h = new indyHeikenAshi();
//		h.setHeiken(bars);
//		df.printSeries(h.HAO());

//		Contract c = new Contract();
//		String s = c.lastTradeDateOrContractMonth();
//		System.out.println(s);

	}

}
