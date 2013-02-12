package main;

import java.io.IOException;

import Server.PayloadServerSocket;

public class Main_Server 
{
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		System.out.println("Server Starting");
		ServerFrame.getInstance();
		PayloadServerSocket payloadServerSocket;
		try 
		{
			payloadServerSocket = new PayloadServerSocket(2001);
			payloadServerSocket.start();
		} 
		catch (IOException e) 
		{			
			e.printStackTrace();
		}
		
	}

}
