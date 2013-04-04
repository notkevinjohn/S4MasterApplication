package Client;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import Data.PayloadDataPoint;

import main.ClientFrame;

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
			ArrayList<PayloadDataPoint> payloads = ClientToServer_Socket.getInstance().getPayloads();			
			serverConnectionOptions.addPayloadNames(payloads);
			ClientFrame.getInstance().menuBar.ConnectionIndicatorConnected(e.serverIP, e.serverPort);
		}
		if(e.success == false)
		{			
			JOptionPane.showMessageDialog(serverConnectionOptions, e.message);
			if(e.message == "Server Disconnected")
			{
				ClientToServer_Socket.getInstance().stopExecution();
				ClientFrame.getInstance().menuBar.ConnectionIndicatorDisconnected();				
			}
		}
		
	}

}
