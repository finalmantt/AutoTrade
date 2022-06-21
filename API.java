//https://filipmolcik.com/connecting-to-interactive-brokers-api-with-java/
//https://holowczak.com/ib-api-java-realtime/5/
//http://holowczak.com/ib-api-java-historical/7/

//https://medium.com/swlh/algorithmic-trading-system-development-1a5a200af260
package autotrade;

import com.ib.controller.ApiController;
import com.ib.controller.Formats;
import com.ib.client.Contract;
import com.ib.client.Order;
import com.ib.client.Types.BarSize;
import com.ib.client.Types.WhatToShow;
import com.ib.controller.ApiConnection.ILogger;
import com.ib.controller.ApiController.IConnectionHandler;
import com.ib.controller.ApiController.IPositionHandler;

import apidemo.util.TCombo;
import apidemo.util.VerticalPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class API implements IConnectionHandler {
	//
	static API INSTANCE = new API();
	Logger m_inLogger = new Logger();
	Logger m_outLogger = new Logger();
	public ApiController m_controller = new ApiController(this, m_inLogger, m_outLogger);
	static JTextArea txt_log = new JTextArea("");
	public JScrollPane scroll_hist = null;
	JFrame frame = new JFrame("IB Auto trade System");

	private final List<String> m_acctList = new ArrayList<>();

	public TableData tb_account = new TableData(new String[] { "account", "key", "value", "currency" });
	public TableData tb_position = new TableData(new String[] { "account", "contract id", "symbol", "pos", "avg" });
	public static TableData tb_bar = new TableData(new String[] { "time", "open", "high", "low", "close" });
	public TableData tb_order = new TableData(new String[] { "orderId", "status", "filled", "remaining", "avgFillPrice",
			"permId", "parentId", "lastFillPrice", "clientId", "whyHeld", "mktCapPrice" });

	public TableData tb_dataTset = new TableData(new String[] { "time", "close" });

	public TableData tb_backtest = new TableData(new String[] { "time", "open", "high", "low", "close" });
	public TableData tb_pl = new TableData(new String[] { "time", "signal", "buy price", "sell price", "PL" });

	public TableData tb_autoTrade = new TableData(new String[] { "time", "open", "high", "low", "close" });

//
//	static JFrame ff = new JFrame("CHART");
	public static JPanel chart = new JPanel();
	JTabbedPane tabPanel = new JTabbedPane();
//	JTextField txt_symbol = new JTextField("EUR");
	JPanel p_west = new JPanel();
	JPanel p_east = new JPanel();
	JPanel p_center = new JPanel();
	JPanel p_top = new JPanel();
	JPanel p_buttom = new JPanel();

	JPanel p_postion = new JPanel();
	JPanel p_account = new JPanel();
	JPanel p_order = new JPanel();
	JPanel p_backtest = new JPanel();

	static JTextField txtMoney = new JTextField("1");
	static JTextField txtPosition = new JTextField("txtPosition");

	static JTextField txt_time = new JTextField("txt_time");
	static JTextField txtLiveOrder = new JTextField("txtLiveOrder");

	ContractPanel contractPanel = new ContractPanel();
//	ContractPanel contractPanel2 = new ContractPanel();
	PlaceOrderPanel p_placeOrder = new PlaceOrderPanel();
	InputText txt_pl = new InputText("PL", "0.0000");

	public static void main(String[] args) {
		INSTANCE.run();
	}

	public void run() {
		// make initial connection to local host, port 7496, client id 0

		JButton btn_connect = new JButton("connect");
		JButton btn_disconnect = new JButton("disconnect");
		JButton btn_account = new JButton("Account");

		JButton btn_hist = new JButton("Historical");
		JButton btn_order = new JButton("Order");
		JButton btn_buy = new JButton("place order");
		JButton btn_cancel = new JButton("Cancel order");
		JButton btn_option = new JButton("Option contract");
		JButton btn_position = new JButton("get position");
		JButton btn_closePosition = new JButton("close postion");
		JButton btn_backtest = new JButton("Backtest");
		JButton btn_autoTrade = new JButton("Auto Trade");		
		JButton btn_autoStart = new JButton("Start Trade");
		JButton btn_autoStop = new JButton("Stop Trade");
		JButton btn_autoSMA = new JButton("Auto SMA");
		

		JButton btn_realtime = new JButton("Realtime");
		JButton btn_stopRealtime = new JButton("StopRealtime");

		JScrollPane scroll_log = new JScrollPane(txt_log);
		scroll_log.setPreferredSize(new Dimension(500, 120));

		JPanel p_connect = new JPanel();
		p_connect.add(btn_connect);
		p_connect.add(btn_disconnect);

		GridLayout gridTop = new GridLayout(0, 5);
		p_top.setLayout(gridTop);
//		p_top.add(btn_connect);
//		p_top.add(btn_disconnect);
		p_top.add(txt_time);
		p_top.add(btn_account);
		p_top.add(btn_hist);
		p_top.add(btn_buy);
		p_top.add(btn_cancel);
		p_top.add(btn_order);
		p_top.add(btn_option);
		p_top.add(btn_position);
		p_top.add(btn_closePosition);
		p_top.add(txtMoney);
		p_top.add(txtPosition);
		p_top.add(btn_realtime);
		p_top.add(btn_stopRealtime);
		p_top.add(btn_backtest);
		p_top.add(btn_autoTrade);
		p_top.add(btn_autoSMA);
		///////////////// West
		GridLayout gridWest = new GridLayout(0, 1);
		p_west.setLayout(gridWest);
		p_west.add(tb_account.getScroll());
//		p_west.add(tb_position.getScroll());

		///////////// Center

		JPanel center_buttom = new JPanel();

		center_buttom.add(tb_position.getScroll());
		center_buttom.add(tb_order.getScroll());

		GridLayout gridCenter = new GridLayout(0, 1);
//		GridBagLayout gridCenter = new GridBagLayout();
		p_center.setLayout(gridCenter);
//		p_center.add(p_barDetail);
		p_center.add(tb_bar.getScroll());
		p_center.add(center_buttom);

		///// East
		p_east.add(chart);
		p_east.add(tb_dataTset.getScroll());

		///// Buttom
		p_buttom.add(scroll_log);

//		JPanel p_contract = new JPanel();	

//		ButtonGroup group = new ButtonGroup();		
//		contractPanel.getRadioButton().setSelected(true);		
//		group.add(contractPanel.getRadioButton() );
//		group.add(contractPanel2.getRadioButton() );

//		p_contract.add(contractPanel);
//		p_contract.add(contractPanel2);
		///// back test
		p_backtest.add(tb_backtest.getScroll());
		p_backtest.add(tb_pl.getScroll());
		p_backtest.add(txt_pl);
//		p_bakkte
		//// TabPanel structure

		//// Auto trade
		JPanel p_autotrade = new JPanel();
		InputText net = new InputText("Net liqudity", "0");
		InputText txtSym = new InputText("Symbol", "EUR/USD");
		InputText signal = new InputText("signal", "0");
		GridLayout grid_autotrade = new GridLayout(5, 1);
		
		JPanel sub_autotrade = new JPanel();
		sub_autotrade.setLayout(grid_autotrade);
		p_autotrade.add(tb_autoTrade.getScroll());
		sub_autotrade.add(btn_autoStart);
		sub_autotrade.add(btn_autoStop);
		sub_autotrade.add(net);
		sub_autotrade.add(txtSym);
		sub_autotrade.add(signal);
		
		p_autotrade.add(sub_autotrade);

//		JButton btn_placeOrder = new JButton("Place");
//		p_placeOrder.addComp(btn_placeOrder);
		
		tabPanel.add("Account/Postion", p_west); // tabPanel.setSelectedIndex(0);
		tabPanel.add("Historical/Order", p_center); // tabPanel.setSelectedIndex(1);
		tabPanel.add("Chart", p_east); // tabPanel.setSelectedIndex(2);
		tabPanel.add("Bar Detail", contractPanel); // tabPanel.setSelectedIndex(3);
		tabPanel.add("Back test", p_backtest); // tabPanel.setSelectedIndex(4);
		tabPanel.add("Connecting", p_connect);// tabPanel.setSelectedIndex(4);
		tabPanel.add("AutoTrade", p_autotrade);// tabPanel.setSelectedIndex(6);
		tabPanel.add("placeOrder", p_placeOrder);// tabPanel.setSelectedIndex(7);
		///// End TabPane

		//// Frame structure
		frame.add(p_top, BorderLayout.NORTH);
		frame.add(tabPanel, BorderLayout.CENTER);
		frame.add(p_buttom, BorderLayout.SOUTH);

		frame.setSize(900, 600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//// End Frame structure

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
				showAccount();
//				AccountATS a = new AccountATS();
//				showAccout();
				// comment

			}
		});

		btn_hist.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				historical();

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
				showOrder();

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

		btn_realtime.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				realtime();
			}
		});
		btn_stopRealtime.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				stopRealtime();
			}
		});

		btn_backtest.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				backtest();
			}
		});

		btn_autoTrade.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				autoTrade();
			}
		});
		
		btn_autoStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				autoStart();
			}
		});
		btn_autoStop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				autoStop();
			}
		});
		connect();

	}

	public void connect() {

//		m_controller.connect("127.0.0.1", 4002, 0, "");
		m_controller.connect("127.0.0.1", 7496, 0, "");
	}

	RealTime real;

//	
//	public Contract getContractPanel() {
//		String symbol = contractPanel.get_symbol();
//		symbol = symbol.toUpperCase();
//		System.out.println(symbol);
//		Contract contract;
//		if (symbol.contains("/")) {
//			String symbol1 = symbol.split("/")[0];
//			String symbol2 = symbol.split("/")[1];
//			contract = ContractATS.getContractFX(symbol1, symbol2);
//
//		} else {
//			contract = ContractATS.getContractStock(symbol);
//
//		}
//		return contract;
//	}
	public void autoTrade() {
		tabPanel.setSelectedIndex(6);
//		AutoTrade auto = new AutoTrade(contractPanel,tb_autoTrade);

	}
	AutoTreadGO autogo ;
	public void autoStart() {	
		autogo = new AutoTreadGO(contractPanel,tb_autoTrade);
	}

	public void autoStop() {
		autogo.reqStop();
	}
	public void historical() {
		tabPanel.setSelectedIndex(1);
//		HistoryATS a = new HistoryATS(contractPanel.getContact(), contractPanel.get_barSize());

		HistoryATS a = new HistoryATS(contractPanel);
	}

	public void realtime() {
		tabPanel.setSelectedIndex(1);
		real = new RealTime(contractPanel);
	}

	public void stopRealtime() {
		real.reqStop();
	}

	BackTest backtest = new BackTest();

	public void backtest() {
		tabPanel.setSelectedIndex(4);

		backtest = new BackTest(tb_backtest, tb_pl, contractPanel);
		backtest.setTextPL(txt_pl);
		backtest.reqHist();

	}

	static int stepAuto = 0;

	public void initialize() {
		showAccount();
		showPosition();
		showOrder();
	}

	public void closePosition() {
//		Contract c = new Contract();
//		Position p = pos.getPositon();
//		c = p.contract();
//		c.exchange("SMART");
//
//		if (c != null) {
//			double qty = pos.getPositon().pos();
//
//			System.out.println(c.toString());
//
////		placeOrder.placeOrder(contract, placeOrder.sellLimit(10, qty));
//			PlaceOrderATS pp = new PlaceOrderATS();
//			OrderATS oo = new OrderATS();
//			if (qty > 0)
//				pp.placeOrder(c, oo.sellMarket(qty));
//			else
//				pp.placeOrder(c, oo.buyMarket(qty * -1));
//			///
//		}

	}

	PositionATS pos = new PositionATS(tb_position);

	public void showPosition() {

		pos.reqPosition();

	}

	AccountATS acc = null;

	public void showAccount() {
		acc = new AccountATS(accountList().get(0), tb_account);
		acc.reqAccount();
	}

	LiveOrderATS liveOrder = new LiveOrderATS(tb_order);

	public void showOrder() {

		liveOrder.reqLiveOrder();
	}

	public void cancelOrder() {
//		m_controller.cancelOrder(234015635);
		m_controller.cancelAllOrders();

	}

	public void buyOder() {
		
		 tabPanel.setSelectedIndex(7);
//		PlaceOrderATS p = new PlaceOrderATS();
//		OrderATS oo = new OrderATS();
//		p.placeOrder(ContractATS.getContractStock("IBM"), oo.buyLimit(2, 1));
		
//		PlaceOrderATS p = new PlaceOrderATS();
//		p.placeBracketOder();
//		p.placeOrder(ContractATS.getContractFXExample(), OrderATS.buyMarket(10000));
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
//		System.out.println("Account List:" + list.toString());

		show("Received account list");
		m_acctList.clear();
		m_acctList.addAll(list);

		initialize();
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