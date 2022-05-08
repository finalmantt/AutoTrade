package autotrade;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class TableData extends JFrame {
	
	DataFrame df = new DataFrame();
	JScrollPane scrollPane;

	public TableData() {
		// TODO Auto-generated constructor stub
		init();
	}

	public TableData(DataFrame df) {
		// TODO Auto-generated constructor stub
		this.df = df;
	}
	
	public TableData(String[][] data_item, ArrayList<String> header ) {
		// TODO Auto-generated constructor stub
		JTable table = new JTable(data_item, header.toArray());

		scrollPane= new JScrollPane(table);
		
		scrollPane.setPreferredSize(new Dimension(400, 200));
		add(scrollPane);
		
	}

	public void init() {
		setSize(600, 400);
		setVisible(true);
	}
	public JScrollPane getTable() {
		return scrollPane;
	}
	
	public void showTable() {
		init();
	}
}
