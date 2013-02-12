package communication;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import events.IServerConnectionEventListener;
import events.ServerConnectionEvent;


public class ConnectionManager extends Thread{

		
	private String serverIPAddress;
	private int port;
	private SocketAddress socketAddress;
	private Socket socket;
	private int socketTimeout = 100;
	private static ConnectionManager Instance;
	private List<Object> listeners = new ArrayList<>();
	
	/*Singleton pattern*/
	public static ConnectionManager getInstance()
	{
		if(Instance == null)
		{
			Instance = new ConnectionManager();
		}
		return Instance;
	}	
	public void setServerConnection(String serverIPAddress, String port) 
	{
		this.serverIPAddress = serverIPAddress;
		this.port = Integer.parseInt(port);
		connect();
	}		
	private void connect()
	{
		try 
		{
		 socketAddress = new InetSocketAddress(serverIPAddress, port);
		 socket = new Socket();
		 socket.connect(socketAddress, socketTimeout);		 
		} 
		catch (UnknownHostException e1) 
		{
			fireEvent(new ServerConnectionEvent(this, false, e1.getMessage(), null, 0));			
		} 
		catch (IOException e1) 
		{	
			fireEvent(new ServerConnectionEvent(this, false, e1.getMessage(), null, 0));			
		}
		if(socket.isConnected())
		{
			fireEvent(new ServerConnectionEvent(this, true, "successfully connected", serverIPAddress, port));
			this.start();				
		}
	}	
	public synchronized void addEventListener(IServerConnectionEventListener listener)
	{
		listeners.add(listener);
	}
	public synchronized void removeEventListener(IServerConnectionEventListener listener)
	{
		listeners.remove(listener);
	}
	public void fireEvent(ServerConnectionEvent serverConnectionEvent)
	{
		Iterator<Object> i = listeners.iterator();
		while(i.hasNext())
		{
			((IServerConnectionEventListener) i.next()).handleServerConnectionEvent(serverConnectionEvent);
		}
	}	
	
	public void run()
	{
		System.out.println("ConnectionManager Started"); 		
		while(true)
		{
			
			try 
			{
				int available = socket.getInputStream().available();
				System.out.println(available);
						
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}			
		}
	}
	

}
