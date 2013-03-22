package main;

import java.io.IOException;
import Server.Server_ServerSocket;

public class Main_Server 
{
	/**
	 * @param args
	 */	
	public static void main(String[] args) 
	{
		System.out.println("Server Starting");
		ServerFrame.getInstance();
		Server_ServerSocket terminalServerSocket;
		try 
		{
			terminalServerSocket = new Server_ServerSocket(2001);
			terminalServerSocket.start();		
		} 
		catch (IOException e)  
		{			
			e.printStackTrace();
		}
		
	}

}
