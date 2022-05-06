package autotrade;

import java.util.ArrayList;

import javax.swing.JFrame;

import com.ib.controller.Bar;

public class ChartData {
	JFrame f = new JFrame();
	ChartData(){
		
	}
	public void setData(ArrayList<Bar> bars) {
		ChartATS c =new ChartATS(bars);
		
		f.add(c);
	}
	public void show() {
		f.setSize(400, 400);
		f.setVisible(true);
		
	}
}
