package autotrade;

import com.ib.client.Contract;
import com.ib.client.Order;
import com.ib.client.OrderState;

public class LiveOpenOrder {
	Contract contract;
	Order order;
	OrderState orderState;
	LiveOpenOrder(Contract contract, Order order, OrderState orderState){
		this.contract = contract;
		this.order = order;
		this.orderState = orderState;
	}
}
