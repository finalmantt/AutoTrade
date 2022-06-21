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

//	public double getLiveOrder() {
//		return 0;
//	}
	public Map<String, LiveOrder> getLiveOrder() {

		return m_liveOrder;
	}
	public Map<String, LiveOpenOrder> getLiveOpenOrder() {

		return m_liveOpenOrder;
	}

	public void setLiveOpenOrder() {
		System.out.println("Live Open Order <<<<<<<<<<<<<<<<<<<<<<<<<<<,");
		table.clearRows();
		for (Map.Entry<String, LiveOpenOrder> entry : m_liveOpenOrder.entrySet()) {
//			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			table.addRow(new String[] { "" + entry.getValue().order.orderId(),

					"" + entry.getValue().contract.localSymbol(), "" + entry.getValue().order.getOrderType(),
					"" + entry.getValue().contract.getSecType(), "" + entry.getValue().order.getAction(),
					"" + entry.getValue().orderState.getStatus()

			});

		}

	}

	public void setLiveOrder() {
		System.out.println("Live Order ++++++++++++++++++ :: " + m_liveOrder.size());
		table.clearRows();
		for (Map.Entry<String, LiveOrder> entry : m_liveOrder.entrySet()) {
//			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			table.addRow(new String[] { "" + entry.getValue().orderId, "" + entry.getValue().status,
					"" + entry.getValue().filled, "" + entry.getValue().remaining, "" + entry.getValue().avgFillPrice,

					"" + entry.getValue().permId, "" + entry.getValue().parentId, "" + entry.getValue().lastFillPrice,
					"" + entry.getValue().whyHeld, "" + entry.getValue().mktCapPrice

			});

		}
	}

	@Override
	public void openOrder(Contract contract, Order order, OrderState orderState) {
		// TODO Auto-generated method stub
		System.out.println("----------------------- Live order ----------------------");
		System.out.println("contract:" + contract.localSymbol() + " oder: " + order.permId() + " order state: "
				+ orderState.getStatus());
		m_liveOpenOrder.put("" + order.permId(), new LiveOpenOrder(contract, order, orderState));

//		setLiveOpenOrder();
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
		System.out.println("$$$$$$$$$$$$$$ orderStatus $$$$$$$$$$$$$$$$$$$$");
		System.out.println("orderId: " + orderId + " " + status + " " + filled + " " + remaining + " " + avgFillPrice
				+ " " + permId + " " + parentId + " " + lastFillPrice + " " + clientId + " " + whyHeld + " "
				+ mktCapPrice);

		m_liveOrder.put("" + permId, new LiveOrder(orderId, status, filled, remaining, avgFillPrice, permId, parentId,
				lastFillPrice, clientId, whyHeld, mktCapPrice));

		if (status.name().equals("Cancelled") || status.name().equals("Filled")
				|| status.name().equals("PendingCancel")) {

			m_liveOrder.remove("" + permId);
		}
//
		setLiveOrder();
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
