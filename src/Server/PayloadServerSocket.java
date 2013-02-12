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
			try
	         {
	            System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
	            Socket server = serverSocket.accept();
	            System.out.println("Just connected to " + server.getRemoteSocketAddress());
	            DataInputStream in = new DataInputStream(server.getInputStream());
	            System.out.println(in.readUTF());
	            DataOutputStream out = new DataOutputStream(server.getOutputStream());
	            out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress() + "\nGoodbye!");
	            server.close();
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
		}
	}
}
