package autotrade;

import java.util.ArrayList;

import com.ib.controller.Bar;

public class IndyATRStop {
	ArrayList<Bar> bars;
	Series ATR = new Series();
	Series ATRlongStop = new Series(); /// price is below close
	Series ATRshortStop = new Series(); /// price is top close
	Series ATRstop = new Series();
//	Series price = new Series();
	Series trend = new Series();
	Series a = new Series();
	Series cPos = new Series();
	Series signal = new Series(); // buy 1, sell -1, do not thing 0
	Series longStop = new Series();
	Series shortStop = new Series();
	double multiple = 1;
	public void setIndy(int period, ArrayList<Bar> bars) {
		IndyATR s = new IndyATR();
		s.setATR(bars);
		ATR = s.getATR(period, "rma");
//		System.out.println(ATR.toStringArray());
		int i = 0;
		for (Bar bar : bars) {

			double high = bar.high();
			double low = bar.low();
			double close = bar.close();
			double HL2 = (high + low) / 2; // 5+4/2 =4.5


//			System.out.println( HL2 + " "+ longStop + " " + shortStop  );
			if (i < period - 1) { // before first bar
				signal.add(0);
				trend.add(-1);
				ATRlongStop.add(-1);
				ATRshortStop.add(-1);

				longStop.add(-1);
				shortStop.add(-1);
			} else if (i == period - 1) { // first bar
				signal.add(0);
				trend.add(1);

				longStop.add(HL2 - ATR.get(i)*multiple);
				shortStop.add(HL2 + ATR.get(i)*multiple);

				ATRlongStop.add(longStop.get(i));
				ATRshortStop.add(shortStop.get(i));

			} else { // other bars
				longStop.add(HL2 - ATR.get(i)*multiple);
				shortStop.add(HL2 + ATR.get(i)*multiple);

				if (trend.get(i - 1) == 1) {
					if (longStop.get(i) > ATRlongStop.get(i - 1)) {
						ATRlongStop.add(longStop.get(i));
					} else {
						ATRlongStop.add(ATRlongStop.get(i - 1));
					}
					ATRshortStop.add(shortStop.get(i));
				} else {
					if (shortStop.get(i) < ATRshortStop.get(i - 1)) {
						ATRshortStop.add(shortStop.get(i));
					} else {
						ATRshortStop.add(ATRshortStop.get(i - 1));
					}

					ATRlongStop.add(longStop.get(i));
				}
//				trend.add(i-1);
//				
				if(trend.get(i-1) == 1 && close < ATRlongStop.get(i)) {
					trend.add(0);
				}
				else if(trend.get(i-1) == 0 && close >ATRshortStop.get(i)) {
					trend.add(1);
				}
				else {
					trend.add(trend.get(i-1));
				}

//				/// signal 
				if(trend.get(i) == 1 && trend.get(i-1) == 0) {
					signal.add(1);
				}else if(trend.get(i) == 0 && trend.get(i-1) == 1) {
					signal.add(-1);
				}
				else {
					
						signal.add(0);
				
				}

			}

			if(trend.get(i) ==1 ) {
				ATRstop.add(ATRlongStop.get(i));
			}
			else {
				ATRstop.add(ATRshortStop.get(i));
			}

			i++;
		}
		System.out.println("trend "+trend.size());
		System.out.println("ATRstop "+ATRstop.size());
		System.out.println("signal "+signal.size());
		System.out.println("ATRlongStop "+ATRlongStop.size());
		System.out.println("ATRshortStop "+ATRshortStop.size());
		

	}

	public Series getTrend() {
		return trend;
	}

	public Series getATRStop() {
		return ATRstop;
	}

	public Series getSignal() {
		return signal;
	}

	public Series getLongStop() {
		return ATRlongStop;
	}

	public Series getShortgStop() {
		return ATRshortStop;
	}

	public Series getlongSP() {
		return longStop;
	}

	public Series getshortSP() {
		return shortStop;
	}

}
