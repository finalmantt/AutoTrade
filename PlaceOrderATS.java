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
		// from Historical to Position
		System.out.println("Create placeOrder");
////		Contract contract = ContractATS.getContractStock("IBM");
//		Contract contract = ContractATS.getContractOptionExample();
//
////        Order order  = buyLimit(1, 1);
//        Order order  = buyMarket( 1);
//
////		Order order = buyStop(1, 1);
//        
//		API.INSTANCE.m_controller.placeOrModifyOrder(contract, order, this);

	}

	public void cancelOrder() {

		int orderId = 0;
		API.INSTANCE.m_controller.cancelOrder(orderId);
//		API.INSTANCE.m_controller.cancelAllOrders();
	}

	public void reqPlaceOrderExample() {
//		PlaceOrderATS p = new PlaceOrderATS();
		if (API.stepAuto == 1) {
			OrderATS orderAts = new OrderATS();
			Contract contract = ContractATS.getContractStock("AAPL");
			Order order = orderAts.buyMarket(1);
			API.INSTANCE.m_controller.placeOrModifyOrder(contract, order, this);

		}

	}

	public void setPlaceOrder() {
//		System.out.println("placeOrderEnd");
		if (API.stepAuto == 1) {

			System.out.println("End Auto trade");
//			API.delay(5, ">>>>>>>>>>wait 5 secoud to start new trade");
			System.out.println("Start new auto trade");
			HistoryATS hist = new HistoryATS();
			hist.reqHistorical();

//			PositionATS p = new PositionATS();
//			p.reqPosition();

		}

	}

	public void placeOrder(Contract contract, Order order) {

		API.INSTANCE.m_controller.placeOrModifyOrder(contract, order, this);

	}

	@Override
	public void orderState(OrderState orderState) {
		// TODO Auto-generated method stub
		System.out.println("orderState >>> " + orderState.toString());
	}

	@Override
	public void orderStatus(OrderStatus status, double filled, double remaining, double avgFillPrice, int permId,
			int parentId, double lastFillPrice, int clientId, String whyHeld, double mktCapPrice) {
		// TODO Auto-generated method stub
		System.out.println("ordeStatus start");
//		System.out.println("status: " + status + "\n" + "filled: " + filled + "\n" + "remaining: " + remaining + "\n"
//				+ "avgFillPrice:" + avgFillPrice + "\n" + "permId: " + permId + "\n" + "parentId: " + parentId + "\n"
//				+ "lastFillPrice: " + lastFillPrice + "\n" + "clientId: " + clientId + "\n" + "whyHeld: " + whyHeld
//				+ "\n" + "mktCapPrice: " + mktCapPrice + "\n");
//		
//		System.out.println("ordeStatus end");

	}

	@Override
	public void handle(int errorCode, String errorMsg) {
		// TODO Auto-generated method stub
		System.out.println("errorCode: " + errorCode);
		System.out.println("errorMsg: " + errorMsg);
		setPlaceOrder();
	}

}
