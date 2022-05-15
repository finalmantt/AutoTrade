package autotrade;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TableDataModel{

	JPanel panel = new JPanel();
	JTable table;
	JScrollPane scrollPane;
	private DefaultTableModel model;

	DataFrame df = new DataFrame();

	TableDataModel() {

	}

	TableDataModel(String[] columnName) {

//		String[] columnNames = { "First Name", "Last Name", "Sport", "# of Years", "Vegetarian" };
//		
//		
		model = new DefaultTableModel(columnName, 0);
//		model.addRow( new Object[]{ "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) });

//		JTable table = new JTable(data_item, header.toArray());
		table = new JTable(model);
		scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(400, 200));
		panel.add(scrollPane);
	}
	public void addRow(String [] rowData) {
		model.addRow(rowData);
	}

	public Vector<Vector>  getData() {
		return model.getDataVector();
	}
	TableDataModel(String[][] data_item, ArrayList<String> header) {
		// TODO Auto-generated constructor stub

		model = new DefaultTableModel(data_item, header.toArray());
//		JTable table = new JTable(data_item, header.toArray());
		table = new JTable(model);
		scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(400, 200));
		panel.add(scrollPane);

//		for (int  i = 0;  < data_item.length; i++) {
//			df.addRow(data_item[i]);
//		}
	}

	public JPanel getTable() {
		return panel;
	}

	public DefaultTableModel getModel() {
		return model;
	}

	public JScrollPane getScroll() {
		return scrollPane;
	}

	public void clear() {
		if (model.getRowCount() > 0) {
			for (int i = model.getRowCount() - 1; i > -1; i--) {
				model.removeRow(i);
			}
		}
	}

	public void setScrollToTop() {
		scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMinimum());
	}

	public void setScrollToButtom() {
		scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
	}
}
