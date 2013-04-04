package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Client_ServerSocket extends Thread
{
	private ServerSocket serverSocket;	
	
	public Client_ServerSocket (int port) throws IOException
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
	            	ServerToClient_Socket clientSocketHandler = new ServerToClient_Socket(socket);
	            	LocalData.getInstance().addClientConnection(clientSocketHandler);
	            	clientSocketHandler.start();
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
