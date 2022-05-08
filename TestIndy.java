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
		// Series close = df.getColBar("close");
		// indySMA sma = new indySMA();
		// sma.setSMA(3, close);
		// df.printSeries(sma.getSMA());
		// df.printSeries(close);

		//// atr///
		// indyATR atr = new indyATR();
		// atr.setATR(bars);
		//// DataFrame.printSeries(atr.getTR());
		// df.printSeries(atr.getATR(10,"sma"));
		////////

		/// heiken ashi

//		indyHeikenAshi h = new indyHeikenAshi();
//		h.setHeiken(bars);
//		df.printSeries(h.HAO());
		
		
		 Contract c = new Contract();
		 String s = c.lastTradeDateOrContractMonth();
		 System.out.println(s);

	}

	
}
