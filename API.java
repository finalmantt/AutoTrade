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

//	public TableData tb_account = new TableData(new String[] { "account", "key", "value", "currency" });
	public TableData tb_position = new TableData(new String[] { "account", "contract id", "symbol", "pos", "avg" });
	public static TableData tb_bar = new TableData(new String[] { "time", "open", "high", "low", "close" });
	public TableData tb_order = new TableData(new String[] { "orderId", "status", "filled", "remaining", "avgFillPrice",
			"permId", "parentId", "lastFillPrice", "clientId", "whyHeld", "mktCapPrice" });

	public TableData tb_dataTset = new TableData(new String[] { "time", "close" });
	public TableData tb_backtest = new TableData(new String[] { "time", "open", "high", "low", "close" });
	public TableData tb_pl = new TableData(new String[] { "time", "signal", "buy price", "sell price", "PL" });
//	public TableData tb_autoTrade = new TableData(new String[] { "time", "open", "high", "low", "close" });
	public static JPanel chart = new JPanel();
	JTabbedPane tabPanel = new JTabbedPane();
	JPanel p_postion = new JPanel();
	JPanel p_backtest = new JPanel();

	static JTextField txtMoney = new JTextField("1");
	static JTextField txtPosition = new JTextField("txtPosition");

	static JTextField txt_time = new JTextField("txt_time");
	static JTextField txtLiveOrder = new JTextField("txtLiveOrder");

	ContractPanel contractPanel = new ContractPanel();
//	ContractPanel contractPanel2 = new ContractPanel();
	PlaceOrderPanel p_placeOrder = new PlaceOrderPanel();
	InputText txt_pl = new InputText("PL", "0.0000");
	
	//////////////// Panel 
	PanelAccount p_account = new PanelAccount();
	PanelOrder p_order = new PanelOrder();
	public static void main(String[] args) {
		INSTANCE.run();
	}

	public void run() {
		JPanel p_east = new JPanel();
		JPanel p_center = new JPanel();
		JPanel p_top = new JPanel();
		JPanel p_buttom = new JPanel();
		// make initial connection to local host, port 7496, client id 0
		JScrollPane scroll_log = new JScrollPane(txt_log);
		scroll_log.setPreferredSize(new Dimension(500, 120));

		GridLayout gridTop = new GridLayout(0, 5);
		p_top.setLayout(gridTop);

		p_top.add(txt_time);
		p_top.add(txtMoney);
		p_top.add(txtPosition);
		

		///////////// Center

		///// East
		p_east.add(chart);
		p_east.add(tb_dataTset.getScroll());

		///// Buttom
		p_buttom.add(scroll_log);

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

		
		sub_autotrade.add(net);
		sub_autotrade.add(txtSym);
		sub_autotrade.add(signal);
		p_autotrade.add(sub_autotrade);

	
		tabPanel.add("Account", p_account.getPanelAccount()); // tabPanel.setSelectedIndex(0);
		tabPanel.add("Ordert/Postion", p_order.getPanelOrder()); // tabPanel.setSelectedIndex(1);
		tabPanel.add("Chart", p_east); // tabPanel.setSelectedIndex(2);
		tabPanel.add("Bar Detail", contractPanel); // tabPanel.setSelectedIndex(3);
		tabPanel.add("Back test", p_backtest); // tabPanel.setSelectedIndex(4);
		tabPanel.add("Connecting", new PanelConnection());// tabPanel.setSelectedIndex(5);
		
		tabPanel.add("placeOrder", p_placeOrder);// tabPanel.setSelectedIndex(7);
		
		AutotradePanel atp1 = new AutotradePanel("EUR/USD","20000");
		tabPanel.add("Auto1", atp1); // tabPanel.setSelectedIndex(8);
		AutotradePanel atp2 = new AutotradePanel("EUR/GBP","20000");
		tabPanel.add("Auto2", atp2); // tabPanel.setSelectedIndex(9);
		AutotradePanel atp3 = new AutotradePanel("AAPL","1");
		tabPanel.add("Auto3", atp3); // tabPanel.setSelectedIndex(10);
		///// End TabPane

		//// Frame structure
		frame.add(p_top, BorderLayout.NORTH);
		frame.add(tabPanel, BorderLayout.CENTER);
		frame.add(p_buttom, BorderLayout.SOUTH);
		frame.setSize(900, 600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		connect();

	}

	public void connect() {

//		m_controller.connect("127.0.0.1", 4002, 0, "");
		m_controller.connect("127.0.0.1", 7496, 0, "");
	}

	public void historical() {
		tabPanel.setSelectedIndex(1);
//		HistoryATS a = new HistoryATS(contractPanel.getContact(), contractPanel.get_barSize());

		HistoryATS a = new HistoryATS(contractPanel);
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
		p_account.showAccount();
		showPosition();
		showOrder();
	}

	PositionATS pos = new PositionATS(tb_position);
	PositionData posData = new PositionData();
	public void showPosition() {

		pos.reqPosition();

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

		m_controller.reqCurrentTime(time -> { 
			show("Server date/time is " + Formats.fmtDate(time * 1000));
			txt_time.setText(Formats.fmtDate(time * 1000));
			});

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