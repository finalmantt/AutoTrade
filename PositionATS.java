package autotrade;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.ib.client.Contract;
import com.ib.controller.ApiController.IPositionHandler;

public class PositionATS implements IPositionHandler {
	Map<String, Position> m_postion = new HashMap<>();

	TableData table; 
	double postion = 0;
	PositionATS(TableData table) {
		// from API to AccountATS2
		this.table = table;
		System.out.println("Create Position");
	}

	public void reqPosition() {
		API.INSTANCE.m_controller.reqPositions(this);
	}

	public void setPosition() {
		System.out.println("setPosition postion > " + m_postion.size());

		table.clearRows();
		
		for (Map.Entry<String, Position> entry : m_postion.entrySet()) {
//			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			table.addRow(new String[] { 
					entry.getValue().account(), 
					"" + entry.getValue().conid(),
					entry.getValue().contract().localSymbol(), 
					"" + entry.getValue().pos(),
					"" + entry.getValue().avgCost()});
			

			
			if(entry.getValue().pos() > 0) {
				API.txtPosition.setText(""+entry.getValue().pos());
				postion = entry.getValue().pos();
			}
		}
		
//		Vector<Vector > V = API.tb_position.getData(); //.getModel().getDataVector();
//		int i = 0;
//		for(Vector v : V) {
////			System.out.println(v.get(1));
//			API.txtPosition.setText(""+v.get(3));
////			API.txtMoney.setText(""+API.tb_position.getModel().getValueAt(i, 1));
//			i++;
//		}
		
		
	}
	public double getPostion() {
		
		return postion;
	}

	public Position getPositonID() {

//		for (Position p : position) {
//			if (p.pos() != 0) {
//				return p;
//			}
//		}

		return null;
	}

	

	@Override
	public void position(String account, Contract contract, double pos, double avgCost) {
		// TODO Auto-generated method stub

//		System.out.println("position" + account + " " + contract.conid() + " " + pos + " " + avgCost);
//		System.out.println();

		System.out.println("add position");
		m_postion.put("" + contract.conid(), new Position(account, contract, pos, avgCost));
		setPosition();

	}

	@Override
	public void positionEnd() {
		System.out.println("positionEnd");
		// TODO Auto-generated method stub
//		setPosition();


	}
}
