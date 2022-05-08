package autotrade;

import com.ib.client.Contract;
import com.ib.client.Order;
import com.ib.client.OrderState;
import com.ib.client.OrderStatus;
import com.ib.controller.ApiController.IOrderHandler;

public class placeOrderATS2 implements IOrderHandler{
	placeOrderATS2() {
		
	}
	public void reqPlaceOrder() {
		Contract contract = ContractATS.getContractStockExample();
		Order order = OrderATS.buyMarket(1);
		API.INSTANCE.m_controller.placeOrModifyOrder(contract, order, this);
	}

	@Override
	public void orderState(OrderState orderState) {
		// TODO Auto-generated method stub
		System.out.println("orderState:"+orderState);
	}

	@Override
	public void orderStatus(OrderStatus status, double filled, double remaining, double avgFillPrice, int permId,
			int parentId, double lastFillPrice, int clientId, String whyHeld, double mktCapPrice) {
		// TODO Auto-generated method stub
		System.out.println("status:"+status);
	}

	@Override
	public void handle(int errorCode, String errorMsg) {
		// TODO Auto-generated method stub
		System.out.println("status:"+errorMsg);
	}
}
