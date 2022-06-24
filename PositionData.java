package autotrade;

import java.util.HashMap;
import java.util.Map;

import com.ib.client.Contract;
import com.ib.controller.ApiController.IPositionHandler;

public class PositionData implements IPositionHandler {
	Map<String, Position> m_position = new HashMap<>();
	public void reqPosition() {
		API.INSTANCE.m_controller.reqPositions(this);
	}
	
	public void getPosition() {
		for (Map.Entry<String, Position> entry : m_position.entrySet()) {
			System.out.println("position =  " + entry.getValue().contract().symbol()+" " + entry.getValue().pos());
			
		}
	}
	
	public Position getPosition(String key) {
		Position pos = m_position.get(key);
		return pos;
	}
	
	public Position getPosition(String symbol, String secType) {
		for (Map.Entry<String, Position> entry : m_position.entrySet()) {
			if (	entry.getValue().contract().symbol().equals(symbol) &&
					entry.getValue().contract().getSecType().equals(secType) ) {
				return entry.getValue();
			}
		}
		return null;
	}
	
	
	@Override
	public void position(String account, Contract contract, double pos, double avgCost) {
		// TODO Auto-generated method stub
		System.out.println("position = " + account + " " + contract.conid()+ " " + contract.symbol() + " " + contract.secType() + " " + pos + " " + avgCost);
		m_position.put("" + contract.conid(), new Position(account, contract, pos, avgCost));
	}

	@Override
	public void positionEnd() {
		// TODO Auto-generated method stub
		
	}

}
