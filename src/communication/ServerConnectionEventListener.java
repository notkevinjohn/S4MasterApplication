package communication;

import javax.swing.JOptionPane;

import main.MainFrame;

import events.IServerConnectionEventListener;
import events.ServerConnectionEvent;

public class ServerConnectionEventListener implements IServerConnectionEventListener 
{

	private ServerConnectionOptions serverConnectionOptions;
	public ServerConnectionEventListener (ServerConnectionOptions serverConnectionOptions)
	{
		this.serverConnectionOptions = serverConnectionOptions;
	}
	@Override
	public void handleServerConnectionEvent(ServerConnectionEvent e) 
	{
		if(e.success == true)
		{
			serverConnectionOptions.setVisible(false);
			MainFrame.getInstance().menuBar.ConnectionIndicatorConnected(e.serverIP, e.serverPort);
		}
		if(e.success == false)
		{			
			JOptionPane.showMessageDialog(serverConnectionOptions, e.message);
		}
		
	}

}
