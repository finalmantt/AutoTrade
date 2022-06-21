package autotrade;

import java.util.ArrayList;
import java.util.List;

import com.ib.client.Order;

public class TestOrder {
	static int nextOrderId = 111;
	public static void main(String[] args) {
        List<Order> bracket = BracketOrder(nextOrderId++, "BUY", 100, 30, 40, 20);
        for(Order o : bracket) {
//            API.INSTANCE.m_controller.placeOrModifyOrder(o.orderId(), ContractATS.getContractStock("AAPL"), o);
        }

	}
	
    public static List<Order> BracketOrder(int parentOrderId, String action, double quantity, double limitPrice, double takeProfitLimitPrice, double stopLossPrice) {
        //This will be our main or "parent" order
        Order parent = new Order();
        parent.orderId(parentOrderId);
        parent.action(action);
        parent.orderType("LMT");
        parent.totalQuantity(quantity);
        parent.lmtPrice(limitPrice);
        //The parent and children orders will need this attribute set to false to prevent accidental executions.
        //The LAST CHILD will have it set to true.
        parent.transmit(false);
        
        Order takeProfit = new Order();
        takeProfit.orderId(parent.orderId() + 1);
        takeProfit.action(action.equals("BUY") ? "SELL" : "BUY");
        takeProfit.orderType("LMT");
        takeProfit.totalQuantity(quantity);
        takeProfit.lmtPrice(takeProfitLimitPrice);
        takeProfit.parentId(parentOrderId);
        takeProfit.transmit(false);
        
        Order stopLoss = new Order();
        stopLoss.orderId(parent.orderId() + 2);
        stopLoss.action(action.equals("BUY") ? "SELL" : "BUY");
        stopLoss.orderType("STP");
        //Stop trigger price
        stopLoss.auxPrice(stopLossPrice);
        stopLoss.totalQuantity(quantity);
        stopLoss.parentId(parentOrderId);
        //In this case, the low side order will be the last child being sent. Therefore, it needs to set this attribute to true 
        //to activate all its predecessors
        stopLoss.transmit(true);
        
        List<Order> bracketOrder = new ArrayList<>();
        bracketOrder.add(parent);
        bracketOrder.add(takeProfit);
        bracketOrder.add(stopLoss);
        
        return bracketOrder;
    }

}
