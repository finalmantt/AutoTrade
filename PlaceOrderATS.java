package autotrade;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.ib.client.Contract;
import com.ib.client.Order;
import com.ib.client.OrderState;
import com.ib.client.OrderStatus;
import com.ib.client.Types.Action;
import com.ib.client.Types.TimeInForce;
import com.ib.controller.ApiController.ILiveOrderHandler;
import com.ib.controller.ApiController.IOrderHandler;

import samples.testbed.orders.OrderSamples;

public class PlaceOrderATS implements IOrderHandler {

	PlaceOrderATS() {

		Contract contract = ContractATS.getContractStock("IBM");

        Order order  = buyLimit(1, 1);
//        Order order  = buyMarket( 1);

//		Order order = buyStop(1, 1);
        
		API.INSTANCE.m_controller.placeOrModifyOrder(contract, order, this);

//		( m_contract, m_order, new IOrderHandler() {
//			@Override public void orderState(OrderState orderState) {
//				ApiDemo.INSTANCE.controller().removeOrderHandler( this);
//				SwingUtilities.invokeLater(() -> dispose());
//			}
//			@Override public void orderStatus(OrderStatus status, double filled, double remaining, double avgFillPrice, int permId, int parentId, double lastFillPrice, int clientId, String whyHeld, double mktCapPrice) {
//			}
//			@Override public void handle(int errorCode, final String errorMsg) {
//				m_order.orderId( 0);
//				SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog( TicketDlg.this, errorMsg));
//			}
//		});
	}

	public Order buyMarket(double Qty) {
		Order order = new Order();
		order.action(Action.BUY);
		order.orderType("MKT");
		order.totalQuantity(Qty);
//      order.lmtPrice(1);
		order.tif(TimeInForce.DAY);
		return order;
	}

	public Order buyLimit(double limit, double Qty) {
		Order order = new Order();
		order.action(Action.BUY);
		order.orderType("LMT");
		order.totalQuantity(Qty);
		order.lmtPrice(limit);
		order.tif(TimeInForce.DAY);
		return order;
	}

	public Order buyStop(double stop, double Qty) {
		Order order = new Order();
		order.action(Action.BUY);
		order.orderType("STP");
		order.totalQuantity(Qty);
		order.auxPrice(stop);
		order.tif(TimeInForce.DAY);
		return order;
	}
	
	public Order sellMarket(double Qty) {
		Order order = new Order();
		order.action(Action.SELL);
		order.orderType("MKT");
		order.totalQuantity(Qty);
//      order.lmtPrice(1);
		order.tif(TimeInForce.DAY);
		return order;
	}

	public Order sellLimit(double limit, double Qty) {
		Order order = new Order();
		order.action(Action.SELL);
		order.orderType("LMT");
		order.totalQuantity(Qty);
		order.lmtPrice(limit);
		order.tif(TimeInForce.DAY);
		return order;
	}

	public Order sellStop(double stop, double Qty) {
		Order order = new Order();
		order.action(Action.SELL);
		order.orderType("STP");
		order.totalQuantity(Qty);
		order.auxPrice(stop);
		order.tif(TimeInForce.DAY);
		return order;
	}

	@Override
	public void orderState(OrderState orderState) {
		// TODO Auto-generated method stub
		System.out.println(orderState);
	}

	@Override
	public void orderStatus(OrderStatus status, double filled, double remaining, double avgFillPrice, int permId,
			int parentId, double lastFillPrice, int clientId, String whyHeld, double mktCapPrice) {
		// TODO Auto-generated method stub
		System.out.println("status: " + status + "\n" + "filled: " + filled + "\n" + "remaining: " + remaining + "\n"
				+ "avgFillPrice:" + avgFillPrice + "\n" + "permId: " + permId + "\n" + "parentId: " + parentId + "\n"
				+ "lastFillPrice: " + lastFillPrice + "\n" + "clientId: " + clientId + "\n" + "whyHeld: " + whyHeld
				+ "\n" + "mktCapPrice: " + mktCapPrice + "\n");
	}

	@Override
	public void handle(int errorCode, String errorMsg) {
		// TODO Auto-generated method stub
		System.out.println(errorCode);
		System.out.println(errorMsg);

	}

}
