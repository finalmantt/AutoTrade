package autotrade;

import java.util.ArrayList;
import com.ib.controller.Bar;

public class IndyDonchain {
	Series donchain = new Series();
	Series upper = new Series();
	Series lower = new Series();
	Series temp_up = new Series();
	Series temp_down = new Series();
	
	ArrayList<Bar> data = null;
	
	
	// queue used to store list so that we get the average
	private ArrayList<Double> Dataset = new ArrayList<Double>();
	private int period;
	private double sum;

	void setIndy(int period, ArrayList<Bar> bars) {
		int i = 0;
		for (Bar bar : bars) {

			double high = bar.high();
			double low = bar.low();

			if (i < period - 1) { // before first bar
				temp_up.add(high);
				upper.add(0);

				temp_down.add(low);
				lower.add(0);
			} else if (i == period - 1) { // first bar
				temp_up.add(high);
				upper.add(highest(temp_up));
				
				temp_down.add(low);
				lower.add(highest(temp_down));
				
			} else { // other bars

				temp_up.add(high);
				upper.add(highest(temp_up));
				temp_up.remove(0);
				
				
				temp_down.add(low);
				lower.add(highest(temp_down));
				temp_down.remove(0);
			}

			i++;
		}

	}

	double highest(Series upper) {
		
		double max = lower.get(0);
		for (int i = 1; i < upper.size(); i++) {
			
			if (upper.get(i) > max) {
				max = upper.get(i);
			}
		}
		return max;
	}
	
	double lowest(Series lower) {
		
		double min = lower.get(0);
		for (int i = 1; i < lower.size(); i++) {
			
			if (lower.get(i) < min ) {
				min = lower.get(i);
			}
		}
		return min;
	}	

	public Series getUpper() {

		return upper;
	}

	public Series getLower() {

		return lower;
	}

}
