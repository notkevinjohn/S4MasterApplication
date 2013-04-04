package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Data.LocalData;
import Data.PayloadDataPoint;
import Data.PayloadDataRequest;
import events.IServerConnectionEventListener;
import events.ServerConnectionEvent;


public class ClientToServer_Socket extends Thread{

		
	private String serverIPAddress;
	private int port;
	private Socket socket;	
	private static ClientToServer_Socket Instance;
	private List<Object> listeners = new ArrayList<>();
	
	ObjectOutputStream outputStream = null;
	ObjectInputStream inputStream = null;
    
    
    private volatile Boolean continueExecution = true;
	
	/*Singleton pattern*/
	public static ClientToServer_Socket getInstance()
	{
		if(Instance == null)
		{
			Instance = new ClientToServer_Socket();
		}
		return Instance;
	}
	
	public void setServerConnection(String serverIPAddress, String port) 
	{
		this.serverIPAddress = serverIPAddress;
		this.port = Integer.parseInt(port);
		this.socket = new Socket();
		connect();
	}
	
	public void stopExecution()
	{
		continueExecution = false;
	}
	
	private void connect()
	{
		
		try 
		{
			socket.connect(new InetSocketAddress(serverIPAddress, port));			
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			outputStream.flush();
			inputStream = new ObjectInputStream(socket.getInputStream());
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
			//this.start();				
		}
		
	}	
	@SuppressWarnings("unchecked")
	public ArrayList<PayloadDataPoint> getPayloads ()
	{		
		ArrayList<PayloadDataPoint> payloads = null;
		try 
		{
			PayloadDataRequest request = new PayloadDataRequest(null, LocalData.getInstance().mostRecentTimestamp());						
			outputStream.writeObject(request);
			Object object;
			if((object = inputStream.readObject())!=null)
			{
				if(object instanceof ArrayList<?>)
				payloads = (ArrayList<PayloadDataPoint>)object;						
			}
		} 
		catch (ClassNotFoundException | IOException e) {e.printStackTrace();}	
		return payloads;
	}
	public void run()
	{   
		
		do
		{		
			try 
			{				
				PayloadDataRequest request = new PayloadDataRequest(LocalData.getInstance().payloadName, LocalData.getInstance().mostRecentTimestamp());						
				outputStream.writeObject(request);
				Object object;
				if((object = inputStream.readObject())!=null)
				{
					if(object instanceof ArrayList<?>)
					LocalData.getInstance().addPayloadDataPoints((ArrayList<?>)object);						
				}				
				
			} 
			catch (IOException e1) 
			{			
				e1.printStackTrace();				
			} 
			catch (ClassNotFoundException e) 
			{				
				e.printStackTrace();
			}	
			try 
			{
				Thread.sleep(500);
			} 
			catch (InterruptedException e) 
			{				
				e.printStackTrace();
			}
		}
		while(continueExecution);
		
		try 
		{
			inputStream.close();
			outputStream.close();
			socket.close();
		} 
		catch (IOException e)
		{			
			e.printStackTrace();
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
		Iterator<Object> i = listeners.iterator() ;
		while(i.hasNext())
		{
			((IServerConnectionEventListener) i.next()).handleServerConnectionEvent(serverConnectionEvent);
		}
	}	
	

}
