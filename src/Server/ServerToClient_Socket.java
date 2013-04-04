package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import Data.*;


public class ServerToClient_Socket extends Thread
{	
	
	public Socket clientSocket;
	ObjectInputStream inputStream = null;
	ObjectOutputStream outputStream = null;		
	Object clientDataObject = null;	
	
	public ServerToClient_Socket (Socket terminalSocket)
	{		
		this.clientSocket = terminalSocket;		
		try 
		{
			outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
			inputStream = new ObjectInputStream(clientSocket.getInputStream());
			//ArrayList<PayloadDataPoint> dataPoints = MySQLConnection.getInstance().payloadsFromToday();
			//outputStream.writeObject(dataPoints);
			
		} 
		catch (IOException e) 
		{			
			e.printStackTrace();
		}
	}
	
	public void run()
	{			
		do
		{
			try 
			{
				clientDataObject = (Object) inputStream.readObject();
			} 
			catch (ClassNotFoundException | IOException e) 
			{				
				e.printStackTrace();
			}
			if(clientDataObject instanceof PayloadDataRequest)
			{
				ArrayList<PayloadDataPoint> dataPoints;
				if(((PayloadDataRequest) clientDataObject).payloadName == null)
				{
					dataPoints = MySQLConnection.getInstance().payloadsFromToday();
				}
				else
				{
					dataPoints = MySQLConnection.getInstance().readPayloadDataPointsFromDataBase((PayloadDataRequest) clientDataObject);
				}
				try 
				{
					outputStream.writeObject(dataPoints);
				} 
				catch (IOException e) 
				{					
					e.printStackTrace();
				}
				
			}
		}
		while(true);
			
	}

}
