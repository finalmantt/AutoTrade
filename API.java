//https://filipmolcik.com/connecting-to-interactive-brokers-api-with-java/
//https://holowczak.com/ib-api-java-realtime/5/
//http://holowczak.com/ib-api-java-historical/7/
package autotrade;

import com.ib.controller.ApiController;
import com.ib.controller.Formats;
import com.ib.controller.ApiConnection.ILogger;
import com.ib.controller.ApiController.IConnectionHandler;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class API implements IConnectionHandler {
	//
	static API INSTANCE = new API();
	Logger m_inLogger = new Logger();
	Logger m_outLogger = new Logger();
	public ApiController m_controller = new ApiController(this, m_inLogger, m_outLogger);
	static JTextArea txt_log = new JTextArea("");
	public JScrollPane scroll_hist = null;
	JFrame frame = new JFrame("IB Auto trade Sytem");

	private final List<String> m_acctList = new ArrayList<>();

	public static void main(String[] args) {
		INSTANCE.run();
	}

	void run() {
		// make initial connection to local host, port 7496, client id 0

		JButton btn_connect = new JButton("connect");
		JButton btn_disconnect = new JButton("disconnect");
		JButton btn_account = new JButton("Account");
		JButton btn_showaccount = new JButton("showAccount");
		JButton btn_hist = new JButton("Historical");
		JButton btn_order = new JButton("Order");
		JButton btn_buy = new JButton("Buy");
		JScrollPane scroll_log = new JScrollPane(txt_log);
		scroll_log.setPreferredSize(new Dimension(500, 120));

		JPanel p_center = new JPanel();
		JPanel p_top = new JPanel();
		JPanel p_buttom = new JPanel();
		p_top.add(btn_connect);
		p_top.add(btn_disconnect);
		p_top.add(btn_account);
		p_top.add(btn_showaccount);
		p_top.add(btn_hist);
		p_top.add(btn_buy);
		p_top.add(btn_order);

		ScrollableJTable st = new ScrollableJTable();
		// p_center.add(scroll_hist);

		p_buttom.add(scroll_log);

		frame.add(p_top, BorderLayout.NORTH);
		frame.add(p_center, BorderLayout.WEST);
		frame.add(p_buttom, BorderLayout.SOUTH);
		frame.setSize(600, 400);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		connect();

		btn_connect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				connect();
			}
		});
		btn_disconnect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				m_controller.disconnect();
			}
		});

		btn_account.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				AccountATS a = new AccountATS();
//				showAccout();
				// comment

			}
		});
		
		btn_showaccount.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				showAccout();
			}
		});
		btn_hist.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				HistoryATS a = new HistoryATS();
			}
		});

		btn_buy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				buyOder();

			}
		});
		btn_order.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				checkOrder();

			}
		});
		// Your implementation
	}

//	AccountATS a = new AccountATS();

	public void showAccout() {
		AccountATS.showAccount();

	}

	OrderDetail detail = new OrderDetail();

	public synchronized void checkOrder() {

		detail.getLiveOrder();
//		try {
//			Thread.sleep(100);
//			
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		 detail.showTable();

	}

	public void buyOder() {
		PlaceOrderATS a = new PlaceOrderATS();
	}

	List<String> accountList() {
		return m_acctList;
	}

	public void connect() {
		// go to connected() if completed

		m_controller.connect("127.0.0.1", 7496, 0, "");

	}

	@Override
	public void connected() {
		// TODO Auto-generated method stub
		show("connected");

		m_controller.reqCurrentTime(time -> show("Server date/time is " + Formats.fmtDate(time * 1000)));

		m_controller.reqBulletins(true, (msgId, newsType, message, exchange) -> {
			String str = String.format("Received bulletin:  type=%s  exchange=%s", newsType, exchange);
			show(str);
			show(message);
		});
	}

	@Override
	public void disconnected() {
		// TODO Auto-generated method stub
		show("disconnected");
	}

	@Override
	public void accountList(List<String> list) {
		// TODO Auto-generated method stub
		show("Received account list");
		m_acctList.clear();
		m_acctList.addAll(list);
	}

	@Override
	public void error(Exception e) {
		// TODO Auto-generated method stub
		show(e.toString());
	}

	@Override
	public void message(int id, int errorCode, String errorMsg) {
		// TODO Auto-generated method stub
		show(id + " " + errorCode + " " + errorMsg);
	}

	@Override
	public void show(String string) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(() -> {
			txt_log.append(string);
			txt_log.append("\n\n");

			Dimension d = txt_log.getSize();
			txt_log.scrollRectToVisible(new Rectangle(0, d.height, 1, 1));
		});
	}

	private static class Logger implements ILogger {
		@Override
		public void log(final String str) {
			// SwingUtilities.invokeLater(() -> {
			// txt_log.append("logger: "+str);
			// txt_log.append("\n");
			//
			// Dimension d = txt_log.getSize();
			// txt_log.scrollRectToVisible( new Rectangle( 0, d.height, 1, 1) );
			// });
		}
	}
}