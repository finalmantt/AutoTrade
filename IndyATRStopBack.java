package autotrade;

import java.util.ArrayList;

import com.ib.controller.Bar;

public class IndyATRStopBack {
	ArrayList<Bar> bars;
	Series ATR = new Series();
	Series ATRlongStop = new Series(); /// price is below close
	Series ATRshortStop = new Series(); /// price is top close
	Series ATRstop = new Series();
//		Series price = new Series();
	Series trend = new Series();
	Series a = new Series();
	Series cPos = new Series();
	Series signal = new Series(); // buy 1, sell -1, do not thing 0

	public void setIndy(int period, ArrayList<Bar> bars) {
		IndyATR s = new IndyATR();
		s.setATR(bars);
		ATR = s.getATR(period, "rma");

		int i = 0;
		for (Bar bar : bars) {
//				System.out.print((i+1)+ " " );
			double high = bar.high();
			double low = bar.low();
			double close = bar.close();
			double HL2 = (high + low) / 2; // 5+4/2 =4.5
			double longStop = HL2 - ATR.get(i);
			double shortStop = HL2 + ATR.get(i);

			boolean cNeg;
			boolean cPos;

//				System.out.println( HL2 + " "+ longStop + " " + shortStop  );
			if (i < period - 1) { // before first bar
				signal.add(0);
				trend.add(-1);
				ATRlongStop.add(-1);
				ATRshortStop.add(-1);
			} else if (i == period - 1) { // first bar
				signal.add(0);
				trend.add(1);
				ATRlongStop.add(longStop);
				ATRshortStop.add(shortStop);
			} else { // other bars

				if (longStop > ATRlongStop.get(i - 1)) {
					ATRlongStop.add(longStop);
				} else {
					ATRlongStop.add(ATRlongStop.get(i - 1));
				}

				if (shortStop < ATRshortStop.get(i - 1)) {
					ATRshortStop.add(shortStop);
				} else {
					ATRshortStop.add(ATRshortStop.get(i - 1));
				}

				if (close < ATRlongStop.get(i)) {
					cNeg = true;
				} else {
					cNeg = false;
				}

				if (close > ATRshortStop.get(i)) {
					cPos = true;

				} else {
					cPos = false;
				}

//					trend

				if (trend.get(i - 1) == 1) {
					if (cNeg) {
						trend.add(0);
					} else {
						trend.add(1);
					}
				} else {
					if (cPos) {
						trend.add(1);
					} else {
						trend.add(0);
					}
				}

				/// signal
				if (trend.get(i) == 1 && trend.get(i - 1) == 0) {
					signal.add(1);
				}
				if (trend.get(i) == 0 && trend.get(i - 1) == 1) {
					signal.add(-1);
				} else {

					signal.add(0);

				}

			}

			if (trend.get(i) == 1) {
				ATRstop.add(ATRlongStop.get(i));
			} else {
				ATRstop.add(ATRshortStop.get(i));
			}

			i++;
		}
//			System.out.println(trend.getSeries());
//			System.out.println(ATRstop.getSeries());

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

}
