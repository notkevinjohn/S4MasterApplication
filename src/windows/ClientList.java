package windows;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;

import events.ClientEvent;
import events.IClientEventListener;
import Server.LocalData;

import java.awt.BorderLayout;

public class ClientList extends JPanel 
{
	
	private static final long serialVersionUID = 1L;
	private JList<String> clientList;
	private DefaultListModel<String> listModel;
	
	public ClientList() 
	{		
		listModel = new DefaultListModel<String>();
		clientList = new JList<String>(listModel);		
		add(clientList, BorderLayout.CENTER);
		LocalData.getInstance().addEventListener(new IClientEventListener() {
			
			@Override
			public void handleClientEvent(ClientEvent clientEvent) 
			{
				listModel.addElement(clientEvent.clientIPAddress);
				
			}
		});
	}

}
