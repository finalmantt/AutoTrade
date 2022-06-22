package autotrade;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.ib.client.Contract;
import com.ib.client.Types.BarSize;
import com.ib.client.Types.DurationUnit;
import com.ib.client.Types.WhatToShow;

import apidemo.util.TCombo;
import apidemo.util.VerticalPanel;

public class PlaceOrderPanel extends JPanel{
	
	JTextField symbol = new JTextField("AAPL");
	String[] symbol_list = {"EUR/USD","AAPL"};
	TCombo <String> list = new TCombo<String>(symbol_list) ;
	
	JTextField qty = new JTextField("2000");
	JTextField limitPrice = new JTextField("1");
//	TCombo <BarSize> barSize = new TCombo<>( BarSize.values() );
	String[] d_product = {"FX", "Stock", "Option"};
	TCombo <String> product = new TCombo<String>(d_product) ;
	
	String[] d_order = {"Buy market","Buy limit" ,"Buy stop","Sell market","Sell limit" ,"Sell stop", "Buy and Stop"};
	TCombo <String> order = new TCombo<String>(d_order) ;
	
	String[] d_right = {"C","P"};
	TCombo <String> right = new TCombo<String>(d_right) ;
	
	JTextField strike = new JTextField("130");

	VerticalPanel p_barDetail = new VerticalPanel();	
	JButton btn_placeOrder = new JButton("Place");
	Contract contract =new Contract();

	PlaceOrderPanel(){
		product.setSelectedIndex(0);
		
		
//		p_barDetail.add(btn1);
		p_barDetail.add("Symbol",symbol);
		p_barDetail.add("Symbol", list);
		p_barDetail.add("Product",product);
		p_barDetail.add("Order",order);
		p_barDetail.add("QTY", qty);
		p_barDetail.add("limit/stop", limitPrice);
		p_barDetail.add("right", right);
		p_barDetail.add("strike", strike);

		
		p_barDetail.add("", btn_placeOrder);

		
		setLayout(new GridLayout(0, 1));
		add(p_barDetail);
		
		
		
		btn_placeOrder.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				placeOrder();
			}
		});
	}
	
	public void placeOrder() {
		
		double qty= Double.parseDouble(this.qty.getText());
		double limit_price = Double.parseDouble(this.limitPrice.getText());
//		symbol.setText(list.getSelectedItem());
		PlaceOrderATS p = new PlaceOrderATS();
		// buy market
		if(product.getSelectedItem() == "FX") {
			if(order.getSelectedItem() == "Buy market")
				p.placeOrder(ContractATS.getContractFX(symbol.getText()), OrderATS.buyMarket(qty));
			if(order.getSelectedItem() == "Buy limit")
				p.placeOrder(ContractATS.getContractFX(symbol.getText()), OrderATS.buyLimit(limit_price, qty));
			if(order.getSelectedItem() == "Buy stop")
				p.placeOrder(ContractATS.getContractFX(symbol.getText()), OrderATS.buyStop(limit_price, qty));
			if(order.getSelectedItem() == "Sell market")
				p.placeOrder(ContractATS.getContractFX(symbol.getText()), OrderATS.sellMarket(qty));
			if(order.getSelectedItem() == "Sell limit")
				p.placeOrder(ContractATS.getContractFX(symbol.getText()), OrderATS.sellLimit(limit_price,qty));
			if(order.getSelectedItem() == "Sell stop")
				p.placeOrder(ContractATS.getContractFX(symbol.getText()), OrderATS.sellStop(limit_price,qty));
			
			if(order.getSelectedItem() == "Buy and Stop") {
				 p.placeBracketOder();
			}
		}
		
		
		if(product.getSelectedItem() == "Stock") {
			if(order.getSelectedItem() == "Buy market")
				p.placeOrder(ContractATS.getContractStock(symbol.getText()), OrderATS.buyMarket(qty));
			if(order.getSelectedItem() == "Buy limit")
				p.placeOrder(ContractATS.getContractStock(symbol.getText()), OrderATS.buyLimit(limit_price, qty));
			if(order.getSelectedItem() == "Buy stop")
				p.placeOrder(ContractATS.getContractStock(symbol.getText()), OrderATS.buyStop(limit_price, qty));
			if(order.getSelectedItem() == "Sell market")
				p.placeOrder(ContractATS.getContractStock(symbol.getText()), OrderATS.sellMarket(qty));
			if(order.getSelectedItem() == "Sell limit")
				p.placeOrder(ContractATS.getContractStock(symbol.getText()), OrderATS.sellLimit(limit_price,qty));
			if(order.getSelectedItem() == "Sell stop")
				p.placeOrder(ContractATS.getContractStock(symbol.getText()), OrderATS.sellStop(limit_price,qty));
		}
		
		
		if(product.getSelectedItem() == "Option") {
			if(order.getSelectedItem() == "Buy market")
				p.placeOrder(ContractATS.getContractOptionExample(), OrderATS.buyMarket(1));
//			if(order.getSelectedItem() == "Buy limit")
//				p.placeOrder(ContractATS.getContractStock(symbol.getText()), OrderATS.buyLimit(limit_price, qty));
//			if(order.getSelectedItem() == "Buy stop")
//				p.placeOrder(ContractATS.getContractStock(symbol.getText()), OrderATS.buyStop(limit_price, qty));
//			if(order.getSelectedItem() == "Sell market")
//				p.placeOrder(ContractATS.getContractStock(symbol.getText()), OrderATS.sellMarket(qty));
//			if(order.getSelectedItem() == "Sell limit")
//				p.placeOrder(ContractATS.getContractStock(symbol.getText()), OrderATS.sellLimit(limit_price,qty));
//			if(order.getSelectedItem() == "Sell stop")
//				p.placeOrder(ContractATS.getContractStock(symbol.getText()), OrderATS.sellStop(limit_price,qty));
		}
		}
		
	
}
