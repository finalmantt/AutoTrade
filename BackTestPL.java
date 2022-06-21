package autotrade;

import javax.swing.JTextField;

import com.ib.controller.Bar;

public class BackTestPL {
	DataFrame df;
	TableData table;
	
	double sum = 0;
	InputText textPL ;
	public BackTestPL(DataFrame df, TableData table) {
		// TODO Auto-generated constructor stub
		this.df = df;
		this.table = table;
		table.clearRows();

	}
	public void setTextPL(InputText textPL) {
		this.textPL = textPL;
	}
	public double getSum() {
		return sum;
	}
	public void showPL() {
		// { "time","signal","buy price","sell price" ,"PL"}
		System.out.println(df.getData().get(0));

		for (int i = 0; i < df.getData().size(); i++) {
			String time = "" + df.getData().get(i).get(0); // time
			double signal = Double.parseDouble(df.getData().get(i).get(df.getColIndex("signal")));
			double price = Double.parseDouble(df.getData().get(i).get(df.getColIndex("close"))); // close

			if (signal != 0) {
				double buy = price * 1;
				table.addRow(new String[] { time, "" + signal, "" + buy, "" + "", "1" });

			}

		}
//		table.setValueAt("dd" , table.getRowCount()-1, 3);
		table.setValueAt("" + df.getData().get(df.getData().size() - 1).get(df.getColIndex("close")),
				table.getRowCount() - 1, 3);
		
		for (int i = 0; i < table.getRowCount(); i++) {

			double signal = Double.parseDouble(table.getValueAt(i, 1).toString());
			double buy = Double.parseDouble(table.getValueAt(i, 2).toString());

			double sell = 0;

			if (i + 1 < table.getRowCount()) {
				sell = Double.parseDouble(table.getValueAt(i + 1, 2).toString());
			} else {
				sell = Double.parseDouble(df.getData().get(df.getData().size() - 1).get(df.getColIndex("close")));
			}
			table.setValueAt("" + sell, table.getRowCount() - 1, 3);
//			System.out.println(sell + " " + buy + " " + (sell - buy));

			table.setValueAt("" + sell, i, 3); // at sell
			double pl = 0;
			if (signal == 1) {
				pl = sell - buy;

			} else {
				pl = (sell - buy) * -1;
			}
			sum += pl;
			table.setValueAt("" + pl, i, 4);

		}
		System.out.println("PL = " + sum);
		

		table.setScrollToButtom();

	}
}
