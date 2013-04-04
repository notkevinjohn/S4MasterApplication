package Sandbox;

import java.io.*;
import java.net.*;

public class ClientSandbox {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException 
	{
		@SuppressWarnings("resource")
		Socket socket = new Socket();
		String host = "127.0.0.1";		
		ObjectOutputStream outputStream = null;
		ObjectInputStream inputStream = null;
		
		try
		{
			socket.connect(new InetSocketAddress(host, 2001));			
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			outputStream.flush();
			inputStream = new ObjectInputStream(socket.getInputStream());
			
		}
		catch(UnknownHostException e)
		{
			e.printStackTrace();
			System.exit(1);
		} 
		catch (IOException e) 
		{			
			e.printStackTrace();
			System.exit(1);
		}
		
		do
		{
			System.out.println("looping");
			SandboxDataRequest data = new SandboxDataRequest();
			data.setName("PayloadName");		
			outputStream.writeObject(data);			
			
			Object object;
			while((object = inputStream.readObject())!=null)
			{
				System.out.println(((SandboxDataObject)object).getDate()+" : "+((SandboxDataObject)object).getDate().getTime());
			}	
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		while(true);
		//inputStream.close();
		//outputStream.close();
		//socket.close();
		
	}

}
