package autotrade;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.Scrollable;

import com.ib.controller.Bar;

public class DataFrame {
	private ArrayList<String> header = new ArrayList<String>();
	private ArrayList<ArrayList<String>> data = new ArrayList<>();

	ArrayList<Bar> bars;
	

	public void setHeader(List<String> lists) {
		ArrayList<String> header = new ArrayList<String>();
		for (String list : lists) {
			header.add(list);
		}
		this.header = header;

	}
//	public <T> void test(ArrayList<T> data) {
//		this.data = (ArrayList<ArrayList<String>>) data;
//		
//	}

	public void setData(ArrayList<ArrayList<String>> data) {
		this.data = data;
	}

	public void setDataBar(ArrayList<Bar> bars) {
		this.bars = bars;
		int n_row = bars.size();
		ArrayList<ArrayList<String>> outterArraylist = new ArrayList<>();

		for (int i = 0; i < n_row; i++) {
			ArrayList<String> innerArraylist = new ArrayList<String>();
			for (String head : header) {
				if (head == BarATS.time)
					innerArraylist.add("" + bars.get(i).formattedTime());
				if (head == BarATS.open)
					innerArraylist.add("" + bars.get(i).open());
				if (head == BarATS.high)
					innerArraylist.add("" + bars.get(i).high());
				if (head == BarATS.low)
					innerArraylist.add("" + bars.get(i).low());
				if (head == BarATS.close)
					innerArraylist.add("" + bars.get(i).close());
				if (head == BarATS.volume)
					innerArraylist.add("" + bars.get(i).volume());
				if (head == BarATS.wap)
					innerArraylist.add("" + bars.get(i).wap());
			}
			outterArraylist.add(innerArraylist);
		}
		data = outterArraylist;
	}

	public static double round(double d) {
		DecimalFormat df = new DecimalFormat(".##");
		return Double.parseDouble(df.format(d));
		
	}

	public static void printSeries(Series s) {
		ArrayList<Double> data = s.getSeries();
		int i = 0;

		for (double d : data) {

			System.out.println(d);
			i++;
		}
	}
	
	
	public static void printSeries(ArrayList<String> data) {
		// ArrayList<Double> data = s.getSeries();
		int i = 0;

		for (String d : data) {

			System.out.println(d);
			i++;
		}
	}

	public String[][] to2DArray() {

		String[][] out = new String[data.size()][];
		int i = 0;
		for (ArrayList<String> l : data) {
			out[i++] = l.toArray(new String[l.size()]);
		}
		return out;
	}

	public ArrayList<String> getHeader() {
		return header;
	}

	public ArrayList<ArrayList<String>> getData() {
		return data;
	}

	public ArrayList<Bar> getBar() {
		return bars;
	}

	public Series getColBar(String name) {
		Series s = new Series();

		if (name == "open") {
			for (Bar bar : bars) {
				s.add(bar.open());
			}
		} else if (name == "high") {
			for (Bar bar : bars) {
				s.add(bar.high());
			}
		} else if (name == "low") {
			for (Bar bar : bars) {
				s.add(bar.low());
			}
		} else if (name == "close") {
			for (Bar bar : bars) {
				s.add(bar.close());
			}
		}

		return s;
//		if(name == "close") {
//			
//		}
	}

	public ArrayList<String> getColBar(int index) {
		ArrayList<String> s = new ArrayList<String>();

		for (ArrayList<String> d : data) {
			s.add(d.get(index));
		}
		return s;
	}
	public Vector<String> getCol(String name) {
//		ArrayList<String> s = new ArrayList<String>();
		
		int i=0;
		for(String head:header) {
			if(head.equals(name)) {				
				break;
			}
			i++;
		}
		
		Vector<String> s = new Vector<String>();

		for (ArrayList<String> d : data) {
			s.add(d.get(i));
		}
		return s;
	}
	public int getColIndex(String name) {

		int i=0;
		for(String head:header) {
			if(head.equals(name)) {				
				break;
			}
			i++;
		}
		
		
		return i;
	}
	private final ArrayList<Double> HAO = new ArrayList<Double>();
	private final ArrayList<Double> HAC = new ArrayList<Double>();
	private final ArrayList<Double> HAH = new ArrayList<Double>();
	private final ArrayList<Double> HAL = new ArrayList<Double>();

	public void clear() {
		header.clear();
		data.clear();
	}
	public HeikenAshi indy_HeikinAshi() {
		HeikenAshi hei = new HeikenAshi();
		for (Bar bar : bars) {
			hei.addData(bar);
		}

		return hei;
	}

	public void addCol(ArrayList<String> col, String name) {
		for (int i = 0; i < data.size(); i++) {
			data.get(i).add(col.get(i));
		}

		header.add(name);
	}
	public void addCol(ArrayList<String> col, String name, TableData tb_bar) {
		for (int i = 0; i < data.size(); i++) {
			data.get(i).add(col.get(i));
		}

		header.add(name);
		tb_bar.addColumn(name, getCol(name));
		tb_bar.setScrollToButtom();

	}

	public void addCol(Series series, String name, TableData tb_bar) {
		for (int i = 0; i < data.size(); i++) {
			data.get(i).add("" + series.get(i));
		}
		tb_bar.addColumn(name, getCol(name));
		tb_bar.setScrollToButtom();

		header.add(name);
	}
	
	
//	String indy1 = "SMA5";
//	String indy2 = "SMA21";
//	API.tb_bar.addColumn(indy1, df.getCol(indy1));
//	API.tb_bar.addColumn(indy2,df.getCol(indy2));
//	API.tb_bar.addColumn("ATR",df.getCol("ATR"));
//	API.tb_bar.setScrollToButtom();
	
	
	public JScrollPane getTable() {
		String[][] data_item = to2DArray();
		ArrayList<String> header = getHeader();
		TableData tb = new TableData(data_item, header);
		return tb.getScroll(); /// note
	}
	
	public void showTable() {
		String[][] data_item = to2DArray();
		ArrayList<String> header = getHeader();
		TableData tb = new TableData(data_item, header);
//		tb.showTable();
	}

	public void showChart(ArrayList<Bar> bars) {

		API.chart.removeAll();
		ChartATS c = new ChartATS(bars);
		API.chart.add(c);

	}

	public  static <T> Series toSeries(ArrayList<T> data) {
		Series s = new Series();
		for (T d :data) {
			s.add(Double.parseDouble(""+d));
		}
		
		return s;
	}
//	
//	public Series toSeries(ArrayList<Double> data) {
//		Series s = new Series();
//		
//		for(double d : data) {
//			s.add(d);
//		}
//		return s;
//	}

//	public Series toSeries(ArrayList<String> data) {
//		Series s = new Series();
//		
//		for(String d : data) {
//			s.add(Double.parseDouble(d));
//		}
//		return s;
//	}
	public ArrayList<Bar> getBarSample() {

		ArrayList<Bar> bars = new ArrayList<>();
		bars.add(new Bar(1, 164.48, 163.6, 163.99, 164.46, 0, 0, 0));
		bars.add(new Bar(2, 164.85, 164.22, 164.49, 164.81, 0, 0, 0));
		bars.add(new Bar(3, 165.33, 164.8, 164.84, 165.12, 0, 0, 0));
		bars.add(new Bar(4, 165.58, 165.05, 165.21, 165.57, 0, 0, 0));
		bars.add(new Bar(5, 165.55, 165.14, 165.55, 165.14, 0, 0, 0));
		bars.add(new Bar(6, 165.33, 164.91, 165.19, 165.33, 0, 0, 0));
		bars.add(new Bar(7, 165.72, 165.33, 165.33, 165.57, 0, 0, 0));
		bars.add(new Bar(8, 165.72, 165.43, 165.58, 165.58, 0, 0, 0));
		bars.add(new Bar(9, 165.62, 165.21, 165.58, 165.42, 0, 0, 0));
		bars.add(new Bar(10, 165.46, 165.06, 165.46, 165.06, 0, 0, 0));
		bars.add(new Bar(11, 165.18, 164.83, 165.02, 165.03, 0, 0, 0));
		bars.add(new Bar(12, 165.04, 164.6, 165.01, 164.76, 0, 0, 0));
		bars.add(new Bar(13, 165.26, 164.8, 164.8, 165.23, 0, 0, 0));
		bars.add(new Bar(14, 165.45, 165.19, 165.25, 165.37, 0, 0, 0));
		bars.add(new Bar(15, 165.46, 165.22, 165.35, 165.33, 0, 0, 0));
		bars.add(new Bar(16, 165.41, 165.08, 165.26, 165.31, 0, 0, 0));
		return bars;
	}

	public void addRow(String account, String string) {
		// TODO Auto-generated method stub
		
	}
	public void addRow(ArrayList<String> data) {
		this.data.add(data);		
	}
}
