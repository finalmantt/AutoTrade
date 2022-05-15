package autotrade;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

public class TestTable {
	JFrame frame = new JFrame();
	JPanel p = new JPanel();
	JTable table;

	private DefaultTableModel model ;

	TestTable() {

		JButton btn = new JButton("go");
		
		String[] columnNames = { "First Name", "Last Name", "Sport", "# of Years", "Vegetarian" };
		
		
		model = new DefaultTableModel(columnNames, 0);
		model.addRow( new Object[]{ "Kathy", "Smith", "Snowboarding", new Integer(5), new Boolean(false) });
		table = new JTable(model);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(500, 200));
//		table.setFillsViewportHeight(true);

		p.add(scrollPane);
		p.add(btn);
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

	}

	public void go() {
		model.addRow( new Object[]{ "John", "Doe", "Rowing", new Integer(3), new Boolean(true) });
		
	}

	public static void main(String[] args) {
//		TestTable test = new TestTable();

//		TableDataModel td = new TableDataModel();
//		JFrame frame = new JFrame();
	}

}
