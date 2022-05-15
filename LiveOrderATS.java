package autotrade;

import java.awt.List;
import java.util.ArrayList;

import com.ib.client.Contract;
import com.ib.client.Order;
import com.ib.client.OrderState;
import com.ib.client.OrderStatus;
import com.ib.controller.ApiController.ILiveOrderHandler;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LiveOrderATS implements ILiveOrderHandler {

	Map<String, LiveOrder> m_liveOrder = new HashMap<>();
	Map<String, LiveOpenOrder> m_liveOpenOrder = new HashMap<>();
	TableData table; 
	public LiveOrderATS(TableData table) {
		// TODO Auto-generated constructor stub
		this.table = table;
	}
	public void reqLiveOrder() {
		this.table = table;
		
		API.INSTANCE.m_controller.reqLiveOrders(this);
	}




	public void setLiveOpenOrder() {
		System.out.println("Live Open Order <<<<<<<<<<<<<<<<<<<<<<<<<<<,");
		table.clear();
		for (Map.Entry<String, LiveOpenOrder> entry : m_liveOpenOrder.entrySet()) {
//			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			table.addRow(new String[] { 
					"" +entry.getValue().order.orderId(), 
					
					"" + entry.getValue().contract.localSymbol(),
					"" +entry.getValue().order.getOrderType(), 
					"" + entry.getValue().contract.getSecType(),
					"" + entry.getValue().order.getAction()
				
					});

		}
	}
	public void setLiveOrder() {
		System.out.println("Live Order <<<<<<<<<<<<<<<<<<<<<<<<<<<,");
		table.clear();
		for (Map.Entry<String, LiveOrder> entry : m_liveOrder.entrySet()) {
//			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			table.addRow(new String[] { 
					"" +entry.getValue().orderId, 
					
					"" + entry.getValue().status,
					"" +entry.getValue().filled, 
					"" + entry.getValue().remaining,
					"" + entry.getValue().avgFillPrice });

		}
	}
	@Override
	public void openOrder(Contract contract, Order order, OrderState orderState) {
		// TODO Auto-generated method stub
		m_liveOpenOrder.put(""+order.orderId() , new LiveOpenOrder(contract,order,orderState));

		setLiveOpenOrder();
	}
	@Override
	public void openOrderEnd() {
		// TODO Auto-generated method stub
		System.out.println("openOrdeEnd");
//		setDetail();
	}

	@Override
	public void orderStatus(int orderId, OrderStatus status, double filled, double remaining, double avgFillPrice,
			int permId, int parentId, double lastFillPrice, int clientId, String whyHeld, double mktCapPrice) {
		// TODO Auto-generated method stub
//		System.out.println("orderStatus");
		System.out.println("orderId: " + orderId + " " + status + " " + filled + " " + remaining + " " + avgFillPrice
				+ " " + permId + " " + parentId + " " + lastFillPrice + " " + clientId + " " + whyHeld + " "
				+ mktCapPrice);
//	System.out.println("xx orderStatus");
		m_liveOrder.put(""+orderId+":"+status , new LiveOrder(orderId, status, filled, remaining, avgFillPrice));

//		setLiveOrder();
	}

	@Override
	public void handle(int orderId, int errorCode, String errorMsg) {
		// TODO Auto-generated method stub
//		System.out.println(errorCode);
//		System.out.println(errorMsg);
		API.INSTANCE.show("error code " + errorCode);
		API.INSTANCE.show(errorMsg);
//		setLiveOrder();
	}

}
