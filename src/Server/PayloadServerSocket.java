package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class PayloadServerSocket extends Thread
{
	private ServerSocket serverSocket;
	
	public PayloadServerSocket (int port) throws IOException
	{
		serverSocket = new ServerSocket(port);		
	}
	public void run()
	{
		while(true)
		{
			Socket socket = null;
			try
	         {	            
	            socket = serverSocket.accept();	           
	         }
			 catch(SocketTimeoutException s)
	         {
	            System.out.println("Socket timed out!");
	            break;
	         }
			 catch(IOException e)
	         {
	            e.printStackTrace();
	            break;
	         }	
			
			try 
			{
				socket.getOutputStream().write(10);
			} 
			catch (IOException e)
			{				
				e.printStackTrace();
			}
		}
	}
}
