package autotrade;

import com.ib.client.OrderStatus;

public class LiveOrder {
	int orderId;
	OrderStatus status;
	double filled;
	double remaining;
	double avgFillPrice;
	int permId;
	int parentId;
	double lastFillPrice;
	int clientId;
	String whyHeld;
	double mktCapPrice;
	
	LiveOrder(int orderId, OrderStatus status, double filled, double remaining, double avgFillPrice,
			int permId, int parentId, double lastFillPrice, int clientId, String whyHeld, double mktCapPrice) {
		this.orderId = orderId;
		this.status = status;
		this.filled = filled;
		this.remaining = remaining;
		this.avgFillPrice = avgFillPrice;
		this.permId = permId;
		this.parentId = parentId;
		this.lastFillPrice = lastFillPrice;
		this.clientId = clientId;
		this.whyHeld = whyHeld;
		this.mktCapPrice = mktCapPrice;
	}

}
