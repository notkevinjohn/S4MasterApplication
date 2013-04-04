package Sandbox;
	
import java.io.*;
import java.net.*;
import java.sql.Date;
import java.util.Calendar;

public class ServerSandbox {	
	
	public static void main(String[] args) throws ClassNotFoundException
	{
		ServerSocket serverSocket = null;
		Socket socket = null;		
		ObjectInputStream inputStream = null;
		ObjectOutputStream outputStream = null;		
		Object clientDataObject = null;		
		
		try 
		{
			serverSocket = new ServerSocket(2001);				
			socket = serverSocket.accept();							
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			inputStream = new ObjectInputStream(socket.getInputStream());			
			do
			{
				clientDataObject = (Object) inputStream.readObject();
				if(clientDataObject!=null )
				{
					SandboxDataObject data = new SandboxDataObject();
					data.setName("Server Data");
					Calendar currenttime = Calendar.getInstance();
					Date sqldate = new Date((currenttime.getTime()).getTime());
					data.setDate(sqldate);
					outputStream.writeObject(data);
				}
			}
			while(true);
			
		} 
		catch (IOException e) 
		{				
			e.printStackTrace();
		}
		try
		{				
			inputStream.close();
			outputStream.close();
			socket.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}				
		
	}
	

}
