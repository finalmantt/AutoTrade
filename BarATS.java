package autotrade;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.ib.controller.Bar;

public class BarATS {
	public static String time = "time";
	public static String open = "open";
	public static String high = "high";
	public static String low = "low";
	public static String close = "close";
	public static String volume = "volume";
	public static String wap = "wap";
	public static String sma = "sma";
	
	ArrayList<Bar> raw_data;
	
	private ArrayList<String> header = new ArrayList<String>();	
	private ArrayList<ArrayList<String>> data = new ArrayList<>();


	BarATS() {

	}

//	BarATS(ArrayList<Bar> rows) {
//		setBar(rows);
//		set_header(BarOHLC());
//	}

//	public void setBar(ArrayList<Bar> rows) {
//
//		this.raw_data = rows;
//	}
//
//	public void set_header(List<String> lists) {
//		ArrayList<String> header = new ArrayList<String>();	
//		for (String list : lists) {
//			header.add(list);
//		}
//		this.header = header;
//		set_data() ; // set data item after set header
//	}
//
//	public void add_header(String name) {
//		
//		header.add(name);
////		set_data() ; // set data item after set header
//	}

//	public void set_data() {
//	
//		int n_row = raw_data.size();
//		ArrayList<ArrayList<String>> outterArraylist = new ArrayList<>();
//
//		for (int i = 0; i < n_row; i++) {
//			ArrayList<String> innerArraylist = new ArrayList<String>();
//			for (String head : header) {
//				if (head == BarATS.time)
//					innerArraylist.add("" + raw_data.get(i).formattedTime());
//				if (head == BarATS.open)
//					innerArraylist.add("" + raw_data.get(i).open());
//				if (head == BarATS.high)
//					innerArraylist.add("" + raw_data.get(i).high());
//				if (head == BarATS.low)
//					innerArraylist.add("" + raw_data.get(i).low());
//				if (head == BarATS.close)
//					innerArraylist.add("" + raw_data.get(i).close());
//				if (head == BarATS.volume)
//					innerArraylist.add("" + raw_data.get(i).volume());
//				if (head == BarATS.wap)
//					innerArraylist.add("" + raw_data.get(i).wap());
//			}
//			outterArraylist.add(innerArraylist);
//		}
//		data = outterArraylist;
//		
//	}

	public List<String> BarOHLC() {
		return Arrays.asList(BarATS.time, BarATS.open, BarATS.high, BarATS.low, BarATS.close);
	}


//	
//	public String [][] to2DArray(){
//
//		String[][] out = new String[data.size()][];
//		int i = 0;
//		for (ArrayList<String> l : data) {
//			out[i++] = l.toArray(new String[l.size()]);
//		}
//		return out;
//	}

	
	
//	public void addSMA() {
//		addSMA(5);
//	}
	
//	public void addSMA(int preriod) {
//		SMA sma = new SMA(preriod, raw_data);
//		
//		
//		
//		
//		for(int i =0; i< data.size();i++) {
//			double num = raw_data.get(i).close();
//			sma.addData(num);
//			
//			if( i < preriod-1 ) {
//				sma.sma(0);
//			}
//			else
//				sma.sma(0);
//		}
//		add_header("sma");
//		
//	}
//	public void addSMA(int preriod) {
//		SimpleMovingAverage sma = new SimpleMovingAverage(preriod);
//		
//		for(int i =0; i< data.size();i++) {
//			double num = raw_data.get(i).close();
//			sma.addData(num);
//			
//			if( i < preriod-1 ) {
//				data.get(i).add("0");
//			}
//			else
//				data.get(i).add(""+sma.getMean());
//		}
//		add_header("sma");
//		
//	}

//	public ArrayList<ArrayList<String>> getData()
//	{
//		return data;
//	}

//	public ArrayList<String> getHeader() {
//		return header;
//	}
//	public void addHeikinAshi() {
//		HeikenAshi hei = new HeikenAshi();
//		for(int i =0; i< data.size();i++) {
//			hei.addData(raw_data.get(i));
//				data.get(i).add(""+hei.getHAO());
//				data.get(i).add(""+hei.getHAC());
//				data.get(i).add(""+hei.getHAH());
//				data.get(i).add(""+hei.getHAL());
//
//		}
//		
//		add_header("HAO");
//		add_header("HAC");
//		add_header("HAH");
//		add_header("HAL");
//		
//	}
}
