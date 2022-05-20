package autotrade;

import java.util.ArrayList;

import com.ib.controller.Bar;

public class IndyHeikenAshi {
	private final ArrayList<Double> open = new ArrayList<Double>();
	private final ArrayList<Double> high = new ArrayList<Double>();
	private final ArrayList<Double> low = new ArrayList<Double>();
	private final ArrayList<Double> close = new ArrayList<Double>();

	private final ArrayList<Double> HAO = new ArrayList<Double>();
	private final ArrayList<Double> HAC = new ArrayList<Double>();
	private final ArrayList<Double> HAH = new ArrayList<Double>();
	private final ArrayList<Double> HAL = new ArrayList<Double>();

	IndyHeikenAshi() {

	}

	public void setHeiken(ArrayList<Bar> bars) {
		for (Bar bar : bars) {
			addData(bar);
		}

	}

	public ArrayList<String> toStringArray(ArrayList<Double> data) {
		ArrayList<String> out = new ArrayList<String>();
		for (double d : data) {
			out.add("" + d);
		}
		return out;
	}
	


	public Series HAO() {
		return DataFrame.toSeries(HAO);
	}

	public Series HAC() {
		return DataFrame.toSeries(HAC);
	}

	public Series HAH() {
		return DataFrame.toSeries(HAH);
	}

	public Series HAL() {
		return DataFrame.toSeries(HAL);
	}

	public void addData(Bar bar) {
		open.add(bar.open());
		high.add(bar.high());
		low.add(bar.low());
		close.add(bar.close());

		HAC.add(DataFrame.round((open.get(open.size() - 1) + high.get(open.size() - 1) + low.get(open.size() - 1)
				+ close.get(open.size() - 1)) / 4));

		if (HAO.size() == 0) {
			HAO.add(DataFrame.round((open.get(open.size() - 1) + close.get(open.size() - 1)) / 2));
			HAH.add(DataFrame.round(high.get(high.size() - 1)));
			HAL.add(DataFrame.round(low.get(low.size() - 1)));
		} else {

			HAO.add(DataFrame.round((HAO.get(HAO.size() - 1) + HAC.get(HAC.size() - 1)) / 2));
			HAH.add(DataFrame.round(
					Math.max(Math.max(high.get(open.size() - 1), HAO.get(open.size() - 1)), HAC.get(open.size() - 1))));
			HAL.add(DataFrame.round(
					Math.min(Math.min(low.get(open.size() - 1), HAO.get(open.size() - 1)), HAC.get(open.size() - 1))));
//			
		}

	}

	

}
