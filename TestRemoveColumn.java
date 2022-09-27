package autotrade;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class TestRemoveColumn{
	DefaultTableModel model;
	JTable table;
	public static void main(String[] args) {
		new TestRemoveColumn();
	}

	public TestRemoveColumn(){
		JFrame frame = new JFrame("Remove a column from a JTable");
		JPanel panel = new JPanel();
		String data[][] = {{"Vinod","MCA","Computer"},{"Deepak","PGDCA","History"},{"Ranjan","M.SC.","Biology"},{"Radha","BCA","Computer"}};
		String col[] = {"Name","Course","Subject"};		
		model = new DefaultTableModel(data, col);
		table = new JTable(model);
		JTableHeader header = table.getTableHeader();
		header.setBackground(Color.yellow);
		//remove column at second position
		removeCol(table,1);
		JScrollPane pane = new JScrollPane(table);
		panel.add(pane);
		frame.add(panel);
		frame.setSize(500,150);
		frame.setUndecorated(true);
		frame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void removeCol(JTable table, int col_index){
		TableColumn tcol = table.getColumnModel().getColumn(col_index);
		table.removeColumn(tcol);
	}
}
