package autotrade;

import java.util.Map;

import com.ib.client.Contract;

public class OrderSend {
	AutoTrade autoTrade = null;
	Contract contract = null;

	OrderSend(AutoTrade autoTrade) {
		this.autoTrade = autoTrade;
		this.contract = autoTrade.contract;
	}

	public void createOrder(String data, double sig, double posN, double qty) {
		// == Trade == EUR GBP CASH 0.0 20000.0

		System.out.println("== Trade == symbol:" + contract.symbol() + " ,currency:" + contract.currency()
				+ " ,secType:" + contract.secType() + " ,posN:" + posN + " ,qty:" + qty);

		System.out.println("=====check condition before send order======");


		// Sig Buy
		if (sig == 1) {
			PlaceOrderATS p = new PlaceOrderATS();

			if (posN == 0) { // no position then buy to open
				System.out.println("Buy to open");
				p.placeOrder(contract, OrderATS.buyMarket(qty));

				LogFile w = new LogFile();
				w.appendPlaceOrder(data + ", Buy to Open, " + contract.symbol() + ", " + contract.currency() + ", "
						+ posN + ", " + qty);

			} else if (posN > 0) { // buy already
				System.out.println("Buy alreay ");

			} else if (posN < 0) { // Ex posN = -20, then Buy to close

				System.out.println("Buy to close");
				p.placeOrder(contract, OrderATS.buyMarket(qty));

				LogFile w = new LogFile();
				w.appendPlaceOrder(data + ", Buy to Close, " + contract.symbol() + ", " + contract.currency() + ", "
						+ posN + ", " + qty);

			}
		}
		// Sig Sell
		else if (sig == -1) {
			PlaceOrderATS p = new PlaceOrderATS();

			if (posN == 0) { // no position then sell to open
				System.out.println("sell  to open");
				p.placeOrder(contract, OrderATS.sellMarket(qty));

				LogFile w = new LogFile();
				w.appendPlaceOrder(data + ", Sell to Open, " + contract.symbol() + ", " + contract.currency() + ", "
						+ posN + ", " + qty);

			} else if (posN > 0) { // Ex posN = 20, then Sell to close
				System.out.println("Sell to close ");
				p.placeOrder(contract, OrderATS.sellMarket(qty));

				LogFile w = new LogFile();
				w.appendPlaceOrder(data + ", Sell to Close, " + contract.symbol() + ", " + contract.currency() + ", "
						+ posN + ", " + qty);

			} else if (posN < 0) { // sell already
				System.out.println("sell already");
			}
		}
		// Sig Close
		else if (sig == 10) {
			PlaceOrderATS p = new PlaceOrderATS();
			// Buy to Close, Ex posN = -10, then buy to close
			if (posN < 0) {
				System.out.println("Buy to close");
				p.placeOrder(contract, OrderATS.buyMarket(qty));

				LogFile w = new LogFile();
				w.appendPlaceOrder(data + ", Buy to Close, " + contract.symbol() + ", " + contract.currency() + ", "
						+ posN + ", " + qty);

			} // Sell to close
			else if (posN > 0) {
				System.out.println("Sell to close ");
				p.placeOrder(contract, OrderATS.sellMarket(qty));

				LogFile w = new LogFile();
				w.appendPlaceOrder(data + ", Sell to Close, " + contract.symbol() + ", " + contract.currency() + ", "
						+ posN + ", " + qty);
			}

		}
		// Sig Wait
		else {
			System.out.println("============wait==============");
		}
		
	}
	
	public double getPosition() {
		double posN = 0;
		PositionATS postion = API.INSTANCE.pos;
		for (Map.Entry<String, Position> entry : postion.m_postion.entrySet()) {
			posN = entry.getValue().pos();			

			Contract con = entry.getValue().contract();
			// choose AAPL or EUR and ckeck secType

			if(con.symbol().equals(contract.symbol()) && 
					con.currency().equals(contract.currency()) &&
					con.secType().equals(contract.secType())
					) {
				break;
			}
		}
		return posN;
	}
}
