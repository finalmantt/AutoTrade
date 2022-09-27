package autotrade;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelConnection extends JPanel{

	PanelConnection(){
		
		JButton btn_connect = new JButton("connect");
		JButton btn_disconnect = new JButton("disconnect");

		add(btn_connect);
		add(btn_disconnect);
		
		btn_connect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
//				APITrade.INSTANCE.m_controller.connect("127.0.0.1", 7496, 0, "");
				API.INSTANCE.connect();
			}
		});
		
		btn_disconnect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				API.INSTANCE.m_controller.disconnect();

			}
		});
	}
	
}
