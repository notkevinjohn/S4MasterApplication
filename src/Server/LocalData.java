package Server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import events.ClientEvent;
import events.IClientEventListener;


public class LocalData 
{
	private static LocalData instance;
	private ArrayList<ServerToClient_Socket> activeClientConnections;	
	private ArrayList<ServerToPayload_Socket> activePayloadConnections;
	public List<Object> listeners = new ArrayList<>();
	
	public static LocalData getInstance ()
	{
		if(instance==null)
		{
			instance = new LocalData();
		}
		return instance;
	}		
	
	public LocalData ()
	{
		activeClientConnections = new ArrayList<ServerToClient_Socket>();
		activePayloadConnections = new ArrayList<ServerToPayload_Socket>();
	}
	public void addClientConnection (ServerToClient_Socket client)
	{
		activeClientConnections.add(client);
		fireEvent(new ClientEvent(this,client.clientSocket.getLocalAddress().toString(), "client"));
	}
	public void addPayloadConnection (ServerToPayload_Socket payload)
	{
		activePayloadConnections.add(payload);
		fireEvent(new ClientEvent(this,payload.payloadSocket.getLocalAddress().toString(), "payload"));
	}
	
	
	public synchronized void addEventListener(IClientEventListener listener)
	{
		listeners.add(listener);
	}
	public synchronized void removeEventListener(IClientEventListener listener)
	{
		listeners.remove(listener);
	}
	private synchronized void fireEvent(ClientEvent clientEvent)
	{
		Iterator<Object> i = listeners.iterator();
		while(i.hasNext())
		{
			((IClientEventListener) i.next()).handleClientEvent(clientEvent);
		}
	}
	
}
