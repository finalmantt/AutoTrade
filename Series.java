package autotrade;

import java.util.ArrayList;

public class Series {
	private ArrayList<Double> s =new ArrayList<Double>();

	public Series() {
		// TODO Auto-generated constructor stub
	}
	
	
	public void add(double d) {
		s.add(d);
	}
	public ArrayList<Double> getSeries() {
		return s; 
	}
	public double get(int index) {
		return s.get(index);
	}
	public void clear() {
		s.clear();
	}
	public void remove(int index) {
		s.remove(index);
	}
	public int size() {
		return s.size();
	}
	public ArrayList<String> toStringArray() {
		ArrayList<String> out =new ArrayList<String>();

		for(double d:s) {
			out.add(""+d);
		}
		return out;
	}
	
	public ArrayList<String> toStringArray(Series s) {
		ArrayList<String> out =new ArrayList<String>();

		for(double d:s.getSeries()) {
			out.add(""+d);
		}
		return out;
	}
	
	
}
