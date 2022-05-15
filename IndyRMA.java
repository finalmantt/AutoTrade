package autotrade;

import java.util.LinkedList;
import java.util.Queue;

public class IndyRMA {
	Series rma = new Series();
	Series data = null;

	// queue used to store list so that we get the average

	private int period;
	private double sum = 0;

	void setRMA(int period, Series data) {
		// TODO Auto-generated method stub
		this.data = data;
		this.period = period;
//		System.out.println(data.size());

		for (int i = 0; i < data.size(); i++) {
			if (i < period - 1) {
				sum += data.get(i);
				rma.add(0);
			} else if (i == period - 1) {
				sum += data.get(i);
				rma.add(sum / (i + 1));
			} else {
				rma.add(((rma.get(i - 1) * (period - 1)) + data.get(i)) / period);
			}
		}
//		System.out.println(rma.toStringArray());
	}

	public Series getRMA() {
		return rma;
	}

	public double getBar(int index) {
		return rma.get(rma.size() - index - 1);
	}

}
