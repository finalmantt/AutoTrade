package autotrade;

import com.ib.client.Contract;

public class Position {
	private Contract contract;
	private String account;
	private double m_position;
	private double m_marketPrice;
	private double m_marketValue;
	private double m_averageCost;
	private double m_unrealPnl;
	private double m_realPnl;
	
	private double  pos;
	private double avgCost;

	public Contract contract()      { return contract; }
	public int conid()				{ return contract.conid(); }
	public double averageCost() 	{ return m_averageCost;}
	public double marketPrice() 	{ return m_marketPrice;}
	public double marketValue() 	{ return m_marketValue;}
	public double realPnl() 		{ return m_realPnl;}
	public double unrealPnl() 		{ return m_unrealPnl;}

	public String account() 		{ return account;}

	public double pos() 			{ return pos;}
	public double avgCost() 		{ return avgCost;}
	
	
	public Position( Contract contract, String account, double position, double marketPrice, double marketValue, double averageCost, double unrealPnl, double realPnl) {
		this.contract = contract;
		this.account = account;
		
		m_marketPrice = marketPrice;
		m_marketValue =marketValue;
		m_averageCost = averageCost;
		m_unrealPnl = unrealPnl;
		m_realPnl = realPnl;
	}
	
	public Position(String account, Contract contract, double pos, double avgCost) 
	{
		this.contract = contract;
		this.account = account;
		this.pos = pos;
		this.avgCost = avgCost;
		
		
	}
}