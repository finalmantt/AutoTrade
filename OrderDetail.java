package autotrade;

import java.awt.List;
import java.util.ArrayList;

import com.ib.client.Contract;
import com.ib.client.Order;
import com.ib.client.OrderState;
import com.ib.client.OrderStatus;
import com.ib.controller.ApiController.ILiveOrderHandler;
import java.util.Arrays;

public class OrderDetail implements ILiveOrderHandler {
	ArrayList<Contract> contract = new ArrayList<Contract>();
	ArrayList<Order> order = new ArrayList<Order>();
	ArrayList<OrderState> orderState = new ArrayList<OrderState>();

	ArrayList<String> contractList = new ArrayList<String>();
	ArrayList<String> orderList = new ArrayList<String>();
	ArrayList<String> orderStateList = new ArrayList<String>();

	public void getLiveOrder() {
		this.contract.clear();
		this.order.clear();
		this.orderState.clear();
		API.INSTANCE.m_controller.reqLiveOrders(this);
	}

	private DataFrame df = new DataFrame();

	public void setDetail() {
		df.clear();

//		df.setHeader(Arrays.asList("time","open","high","low","close"));
//		List<String> header = Array.asList("Symbol","Action","Status");
		List header = new List();

		df.setHeader(Arrays.asList("ordeID","Symbol","OrderType", "SecType", "Action", "Status"));
		for (int i = 0; i <contract.size(); i++) {
			ArrayList<String> detail = new ArrayList<String>();
			
			detail.add(""+order.get(i).orderId());
			detail.add(contract.get(i).symbol());
			detail.add(order.get(i).getOrderType());
			detail.add(contract.get(i).getSecType());
			detail.add(order.get(i).getAction());
			detail.add(orderState.get(i).getStatus());
			df.addRow(detail);
			
			

		}
		System.out.println(contract.toString());
		showTable();
	}

	public  void showTable() {
		df.showTable();
	}

	@Override
	public void openOrder(Contract contract, Order order, OrderState orderState) {
		// TODO Auto-generated method stub

		
		this.contract.add(contract);
		this.order.add(order);
		this.orderState.add(orderState);
//		System.out.println("openOrder");
//		System.out.println("contract:\n" +contract.symbol()+"\norder:\n "+order.getAction()+"\nstate:\n"+orderState.getStatus());
////		System.out.println(order);
////		System.out.println(orderState);
//		System.out.println("xx openOrder");
	}

	@Override
	public void openOrderEnd() {
		// TODO Auto-generated method stub
		System.out.println("openOrdeEnd");
		setDetail();
	}

	@Override
	public void orderStatus(int orderId, OrderStatus status, double filled, double remaining, double avgFillPrice,
			int permId, int parentId, double lastFillPrice, int clientId, String whyHeld, double mktCapPrice) {
		// TODO Auto-generated method stub
//		System.out.println("orderStatus");
		System.out.println("orderId: " +orderId+" "+status+" "+filled+" "+remaining+" "+avgFillPrice+" "+permId+" "+parentId+" "+lastFillPrice+" "+clientId+" "+whyHeld+" "+mktCapPrice);
//	System.out.println("xx orderStatus");
	}

	@Override
	public void handle(int orderId, int errorCode, String errorMsg) {
		// TODO Auto-generated method stub
//		System.out.println(errorCode);
//		System.out.println(errorMsg);
		API.INSTANCE.show("error code " + errorCode);
		API.INSTANCE.show(errorMsg);
	}

}
