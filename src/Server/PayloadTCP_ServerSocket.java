package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class PayloadTCP_ServerSocket extends Thread 
{
	private ServerSocket serverSocket;	
	
	public PayloadTCP_ServerSocket (int port) throws IOException
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
	            if(socket != null)
	            {
	            	ServerToPayload_Socket payloadSocketHandler = new ServerToPayload_Socket(socket);
	            	LocalData.getInstance().addPayloadConnection(payloadSocketHandler);
	            	payloadSocketHandler.start();
	            }
	         }
			 catch(SocketTimeoutException s)
	         {	            
	            break;
	         }
			 catch(IOException e)
	         {
	            e.printStackTrace();
	            break;
	         }			
		}
	}
}
