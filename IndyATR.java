package autotrade;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.ib.controller.Bar;

public class IndyATR {

	
	Series TR = new Series();
	Series ATR = new Series();
	public void setATR(ArrayList<Bar> bars) {

		for (int i = 0; i < bars.size(); i++) {
			double h = bars.get(i).high();
			double l = bars.get(i).low();
			if (i == 0) {
				double tr1 = h-l;
				TR.add(DataFrame.round(tr1));

			} else {
				double c1 = bars.get(i-1).close();
				double tr1 =  Math.abs(h-l);
				double tr2 = Math.abs(h-c1);
				double tr3 = Math.abs(c1-l);
				TR.add(DataFrame.round(Math.max(tr1,Math.max(tr2,tr3))));
			}			
		}			
	}
	
	public  Series getTR() {
		return TR;
	}
	
	public Series getATR() {
		return getATR(14,"sma");
	}
	
	public Series getATR(int period) {
		return getATR(period,"sma");
	}
	
	public Series getATR(int period, String avg) {
		if (avg == "sma") {
			IndySMA s = new IndySMA();
			s.setSMA(period, TR);
			ATR = s.getSMA();
			return ATR;
		}
		if (avg == "rma") {
			IndyRMA s = new IndyRMA();
			s.setRMA(period, TR);
			ATR = s.getRMA();
			return ATR;
		}
		return null;
	}

}
