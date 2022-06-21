package autotrade;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class TestTable {
	JFrame frame = new JFrame();
	JPanel p = new JPanel();
	JTable table;
	JScrollPane scrollPane;
	DefaultTableModel model;

	TestTable() {

		JButton btn = new JButton("go");
		JButton btn_add = new JButton("add col");
		JButton btn_del = new JButton("del col");

//		String[] columnNames = { "First Name", "Last Name", "Sport", "# of Years", "Vegetarian" };
		String[] columnNames = new String[5];
		columnNames[0] = "First Name";
		columnNames[1] = "Last Name";
		columnNames[2] = "Sport";
		columnNames[3] = "# of Years";
		columnNames[4] = "Vegetarian";

		model = new DefaultTableModel(columnNames, 0);
		model.addRow(new Object[] { "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) });
		table = new JTable(model);

		scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(400, 200));
//		table.setFillsViewportHeight(true);

		p.add(scrollPane);
		p.add(btn);
		p.add(btn_add);
		p.add(btn_del);
		frame.add(p);
		frame.setSize(600, 300);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				go();
			}
		});

		btn_add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				addCol();
			}
		});
		btn_del.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				delCol();
			}
		});

	}

	public void go() {
		model.addRow(new Object[] { "John", "Doe", "Rowing", new Integer(3), new Boolean(true) });

	}

	public void addCol() {

//	
		model.addColumn("test", new String[] { "dd", "as", "ss" });

	}

	public void delCol() {
		removeCol(table, 1);

	}

	public void removeCol(JTable table, int col_index) {
		TableColumn tcol = table.getColumnModel().getColumn(col_index);
		table.removeColumn(tcol);

		String[] columnNames = new String[table.getColumnCount()];
		for (int i = 0; i < table.getColumnCount(); i++) {
			columnNames[i] = table.getColumnName(i);
		}
		
		System.out.println(table.getRowCount()+" "+table.getColumnCount());
		String[][] rows = new String[table.getRowCount()][table.getColumnCount()];
		for (int i = 0; i < table.getRowCount(); i++) {
			for (int j = 0; j < table.getColumnCount(); j++) {
				rows[i][j] = table.getValueAt(i, j).toString();
			}
		}
		
//		System.out.println(rows[0][0]);
		
		/// remove row
		for (int i = 0; i < rows.length; i++) {
			model.removeRow(0);
		}
		DefaultTableModel model = new DefaultTableModel(columnNames,table.getRowCount());
		for (int i = 0; i < rows.length; i++) {
			model.addRow(rows[i]);
		}
		
		
		table.setModel(model);

		
//		scrollPane.setPreferredSize(new Dimension(400, 200));
	}

	public static void main(String[] args) {
		TestTable test = new TestTable();

		TableDataModel td = new TableDataModel();
		JFrame frame = new JFrame();
	}

}
