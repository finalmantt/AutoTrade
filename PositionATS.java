package autotrade;

import java.util.ArrayList;
import java.util.Arrays;

import com.ib.client.Contract;
import com.ib.controller.ApiController.IPositionHandler;

public class PositionATS implements IPositionHandler {
	ArrayList<Position> position = new ArrayList<Position>();

//	ArrayList<String> data = new ArrayList<String>();
	DataFrame df = new DataFrame();

	PositionATS() {
		// from API to AccountATS2
		System.out.println("Create Position");
	}

	public void reqPosition() {
		df.clear();
		position.clear();
		System.out.println("position >" +position);
		API.INSTANCE.m_controller.reqPositions(this);
	}

//	public void showPosition() {
//		
//	}
	public void setPosition() {

		System.out.println("stepAuto = " + API.stepAuto + " account id = " + API.INSTANCE.accountList());
		df.setHeader(Arrays.asList("account", "contract id", "symbol", "pos", "avg"));
		for (Position p : position) {
			ArrayList<String> detail = new ArrayList<String>();

			detail.add(p.account());
			detail.add("" + p.conid());
			detail.add(p.contract().localSymbol());
			detail.add("" + p.pos());
			detail.add("" + p.avgCost());

			df.addRow(detail);

		}

		API.INSTANCE.p_west.add(df.getTable());
		API.INSTANCE.frame.repaint();
		API.INSTANCE.frame.setVisible(true);

	
//		showTable();
	}

	public Position getPositon() {

		for (Position p : position) {
			if (p.pos() != 0) {
				return p;
			}
		}
//		
//		
//		if(position.size() !=0) {
//			Position p = position.get(0); 
//			System.out.println(">>>"+p);
//			return p;
//		}
//		else
		return null;
	}

	public void showTable() {
		df.showTable();
	}

	@Override
	public void position(String account, Contract contract, double pos, double avgCost) {
		// TODO Auto-generated method stub
//		Position p = new Position(account, contract, pos, avgCost);
		position.add(new Position(account, contract, pos, avgCost));
//		Position p = new Position(contract, account, position, marketPrice, marketValue, averageCost, unrealPnl, realPnl)
//	System.out.println("position come");
	}

	@Override
	public void positionEnd() {
		// TODO Auto-generated method stub
		setPosition();
		
		///// auto trade
		if (API.INSTANCE.stepAuto == 1) {
			System.out.println("position.size() = " + position.size());

			System.out.println("stepAuto = " + API.stepAuto + " account id = " + API.INSTANCE.accountList());
			String accoundID = API.INSTANCE.accountList().get(0);

			HistoryATS hist = new HistoryATS();
			hist.reqHistorical();

//			AccountATS2 acc = new AccountATS2(accoundID);
//			acc.reqAccount();
//			System.out.println(acc.df.getData().size());
		}
	}

}
