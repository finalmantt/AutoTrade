package autotrade;

import com.ib.client.Order;
import com.ib.client.Types.Action;
import com.ib.client.Types.TimeInForce;

public class OrderATS {
	public static  Order buyMarket(double Qty) {
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

}
