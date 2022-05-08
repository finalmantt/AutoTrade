//https://filipmolcik.com/connecting-to-interactive-brokers-api-with-java/
//https://holowczak.com/ib-api-java-realtime/5/
//http://holowczak.com/ib-api-java-historical/7/
package autotrade;

import com.ib.controller.ApiController;
import com.ib.controller.Formats;
import com.ib.client.Contract;
import com.ib.client.Order;
import com.ib.controller.ApiConnection.ILogger;
import com.ib.controller.ApiController.IConnectionHandler;
import com.ib.controller.ApiController.IPositionHandler;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
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

	JPanel p_west = new JPanel();
	JPanel p_center = new JPanel();
	JPanel p_top = new JPanel();
	JPanel p_buttom = new JPanel();

	public static void main(String[] args) {
		INSTANCE.run();
	}

	public void run() {
		// make initial connection to local host, port 7496, client id 0

		JButton btn_connect = new JButton("connect");
		JButton btn_disconnect = new JButton("disconnect");
		JButton btn_account = new JButton("Account");
		JButton btn_showaccount = new JButton("showAccount");
		JButton btn_hist = new JButton("Historical");
		JButton btn_order = new JButton("Order");
		JButton btn_buy = new JButton("place order");
		JButton btn_cancel = new JButton("Cancel order");
		JButton btn_option = new JButton("Option contract");
		JButton btn_position = new JButton("get position");
		JButton btn_closePosition = new JButton("close postion");

		JButton btn_autotrade = new JButton("Auto Trade");
		JButton btn_stoptrade = new JButton("Stop Trade");

		TextBox txt_conn = new TextBox("conn");
//		implements IPositionHandler

		JScrollPane scroll_log = new JScrollPane(txt_log);
		scroll_log.setPreferredSize(new Dimension(500, 120));

		GridLayout gridLayout = new GridLayout(0, 5);

//		        compsToExperiment.setLayout(experimentLayout);
//
//		        compsToExperiment.add(new JButton("Button 1"));
//		        compsToExperiment.add(new JButton("Button 2"));
//		        compsToExperiment.add(new JButton("Button 3"));
//		        compsToExperiment.add(new JButton("Long-Named Button 4"));
//		        compsToExperiment.add(new JButton("5"));
//		        

		p_top.setLayout(gridLayout);
		p_top.add(btn_connect);
		p_top.add(btn_disconnect);
		p_top.add(btn_account);
		p_top.add(btn_showaccount);
		p_top.add(btn_hist);
		p_top.add(btn_buy);
		p_top.add(btn_cancel);
		p_top.add(btn_order);
		p_top.add(btn_option);
		p_top.add(btn_position);
		p_top.add(btn_closePosition);
		p_top.add(btn_autotrade);
		p_top.add(btn_stoptrade);
		ScrollableJTable st = new ScrollableJTable();

		// p_center.add(scroll_hist);
		p_center.add(txt_conn);

		p_buttom.add(scroll_log);

		frame.add(p_top, BorderLayout.NORTH);
		frame.add(p_center, BorderLayout.CENTER);
		frame.add(p_west, BorderLayout.WEST);
		frame.add(p_buttom, BorderLayout.SOUTH);
		frame.setSize(600, 600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

		btn_cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cancelOrder();

			}
		});

		btn_option.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		btn_position.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				showPosition();

			}
		});

		btn_closePosition.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				closePosition();

			}
		});

		btn_autotrade.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				autoTrade();
			}
		});

		btn_stoptrade.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				stepAuto = 0;
			}
		});

		connect();
		// Your implementation
		///
//		try {
//			Thread.sleep(200);
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}

//		String accountID = getAccountID();
//		AccountATS2 a = new AccountATS2(accountID);
//		a.reqAccount();
//		
//		PositionATS p = new PositionATS();
//		p.reqPosition();

//		try {
//			Thread.sleep(200);
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		a.showTable();
	}

	public void connect() {
		// go to connected() if completed

		m_controller.connect("127.0.0.1", 7496, 0, "");

	}

	static int stepAuto = 0;

	public void autoTrade() {

//		while(true) {
		System.out.println("start trade");
		stepAuto = 1;

//		for(int i= 0; i < 5; i++) {
//			
//			
//			PlaceOrderATS pp = new PlaceOrderATS();
//			pp.reqPlaceOrderExample();
//			
//		}

		HistoryATS hist = new HistoryATS();
		hist.reqHistorical();

//			PositionATS p = new PositionATS();
//			p.reqPosition();
//			String  accId= accountList().get(0).toString();

//			try {
//				Thread.sleep(3000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}

//		System.out.println(accountList());
//		AccountATS2 acc = new AccountATS2(accId);
//		acc.reqAccount();
//		System.out.println(acc.df.getData().size());
//		acc.getDF();
//		initialize();
	}

	AccountATS account;

	public void initialize() {

		String accountID = accountList().get(0).toString();
//		System.out.println(accountID);
		account = new AccountATS();
		p_west.add(account);

	}

//	public String getAccountID() {
//		while (accountList().size() == 0) {
//			System.out.print("");
//		}
//		return accountList().get(0).toString();
//	}

	public void closePosition() {
		Contract c = new Contract();
		Position p = pos.getPositon();
		c = p.contract();
		c.exchange("SMART");

		if (c != null) {
			double qty = pos.getPositon().pos();

			System.out.println(c.toString());

//		placeOrder.placeOrder(contract, placeOrder.sellLimit(10, qty));
			PlaceOrderATS pp = new PlaceOrderATS();
			OrderATS oo = new OrderATS();
			if (qty > 0)
				pp.placeOrder(c, oo.sellMarket(qty));
			else
				pp.placeOrder(c, oo.buyMarket(qty * -1));
			///
		}

	}

	PositionATS pos = new PositionATS();

	public void showPosition() {

		pos.reqPosition();
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		pos.setPosition();
	}
//	AccountATS a = new AccountATS();

	public void showAccout() {
		AccountATS.showAccount();

	}

	OrderDetail detail = new OrderDetail();

	public void checkOrder() {

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

	public void cancelOrder() {
//		m_controller.cancelOrder(234015635);
		m_controller.cancelAllOrders();

	}

	public void buyOder() {
		PlaceOrderATS p = new PlaceOrderATS();
		OrderATS oo = new OrderATS();
		p.placeOrder(ContractATS.getContractStock("IBM"), oo.buyLimit(2, 1));
	}

	public List<String> accountList() {
		return m_acctList;
	}

	public static void delay(int seconds) {
		delay(seconds, "");
	}

	public static void delay(int seconds, String txt) {
		System.out.println(txt);
		try {
			for (int i = 0; i < seconds; i++) {
				System.out.print(" " + (i + 1));
				Thread.sleep(1000);
			}
			System.out.println();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void connected() {
		// TODO Auto-generated method stub
		System.out.println("Connected");
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
		System.out.println("disconnected");
		show("disconnected");
	}

	@Override
	public void accountList(List<String> list) {
		// TODO Auto-generated method stub
		System.out.println("Account List:" + list.toString());

		show("Received account list");
		m_acctList.clear();
		m_acctList.addAll(list);

//		initialize();
	}

	@Override
	public void error(Exception e) {
		// TODO Auto-generated method stub
		System.out.println("Error" + e);
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