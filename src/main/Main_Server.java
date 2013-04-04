package main;

import java.io.IOException;

import Server.Client_ServerSocket;
import Server.PayloadTCP_ServerSocket;
import Server.PayloadUPD_Socket;

public class Main_Server 
{
	/**
	 * @param args
	 */	
	public static void main(String[] args) 
	{		
		ServerFrame.getInstance();
		
		
		Client_ServerSocket clientServerSocket;
		PayloadUPD_Socket payloadUPDServerSocket;
		PayloadTCP_ServerSocket payloadTCPServerSocket;
		
		try 
		{
			clientServerSocket = new Client_ServerSocket(2001);
			clientServerSocket.start();	
			
			payloadTCPServerSocket = new PayloadTCP_ServerSocket(2000);
			payloadTCPServerSocket.start();
			
			payloadUPDServerSocket = new PayloadUPD_Socket(2002);
			payloadUPDServerSocket.start();
			
		} 
		catch (IOException e)  
		{			
			e.printStackTrace();
		}		
		
	}

}
