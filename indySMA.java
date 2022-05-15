package autotrade;

import java.util.LinkedList;
import java.util.Queue;

public class indySMA {
	Series sma = new Series();
	Series data = null;

	// queue used to store list so that we get the average
	private Queue<Double> Dataset = new LinkedList<Double>();
	private int period;
	private double sum;
//


	void setSMA(int period, Series data) {
		for (int i = 0; i < data.size(); i++) {
//		System.out.print(i+ "   ");
			sum+=data.get(i);
			if (i < period - 1) {
				Dataset.add(data.get(i));
				sma.add(0);				
//				System.out.println("    0");
			} else if (i == period - 1) {
				Dataset.add(data.get(i));
				sma.add(sum/period);
//				System.out.println("    "+sum+ "/ " +period +" =  " +DataFrame.round(sum/period));
			} else {
				Dataset.add(data.get(i));
				sum-=Dataset.remove();
				sma.add(sum/period);
//				System.out.println("    "+sum+ "/ " +period+" =  " +DataFrame.round(sum/period));
			}
		}
		
	}
	public Series getSMA() {

		return sma;
	}
	public  double getBar(int index) {
		return sma.get(sma.size()- index-1);
	}
	

}	
	//// calculate sma
//	void setSMA(int period, Series data) {
//	// TODO Auto-generated method stub
////	this.close = close;
//	this.data = data;
//	this.period = period;
//
//	for (int i = 0; i < data.size(); i++) {
//		double num = data.get(i);// .close();
//		addData(num);
//
//		if (i < period - 1) {
//			sma.add(DataFrame.round(0));
//		} else
//			sma.add(DataFrame.round(getMean()));
//		
//	}
//
//}
	// function to add new data in the
	// list and update the sum so that
	// we get the new mean
//	public void addData(double num) {
//		sum += num;
//		Dataset.add(num);
//
//		// Updating size so that length
//		// of data set should be equal
//		// to period as a normal mean has
//		if (Dataset.size() > period) {
//			sum -= Dataset.remove();
//		}
//	}

//	// function to calculate mean
//	public double getMean() {
//		return sum / period;
//	}
//	



