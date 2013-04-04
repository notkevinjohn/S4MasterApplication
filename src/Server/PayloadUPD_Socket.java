package Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Formatter;

import Data.PayloadDataPoint;


public class PayloadUPD_Socket extends Thread 
{
	
	private DatagramSocket payloadSocket;
	private PayloadDataPointsManager partialDataPoints;
	
	public PayloadUPD_Socket(int port) throws SocketException
	{
		payloadSocket = new DatagramSocket(port);
		partialDataPoints = new PayloadDataPointsManager();
	}
	public void run()
	{	
		byte[] buf = new byte[110];
		DatagramPacket packet = new DatagramPacket(buf, buf.length);
		try 
		{
			payloadSocket.receive(packet);
			//Handle UDP data;
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
	    }		   
		parseHex(hexEncode(buf));
		
		try { Thread.sleep(10); } catch(InterruptedException e) { }
	}
	
	/*------Fuzzy on what this does--------*/
	private static Appendable hexEncode(byte buf[], Appendable sb)  
	{  
		final Formatter formatter = new Formatter(sb);  
	    for (int i = 0; i < buf.length; i++)  
	    {  
	        formatter.format("%02x", buf[i]);  
	    }  
	    formatter.close();
	    return sb;    
	 }  
	 
	private static String hexEncode(byte buf[])  
	{  
	    return hexEncode(buf, new StringBuilder()).toString();  
	} 
	/*-------------------------------------*/
	
	public void parseHex(String hexString)
	{
		if(hexString.substring(0, 1) == "$")
			parseGPS(hexString);
		else if(hexString.substring(0, 1) == "@")
			parseSensorData(hexString);
		
	}
	private void parseGPS (String GPSString)
	{
		PayloadDataPoint payloadDataPoint = new PayloadDataPoint();
		//load with GPS data
		partialDataPoints.AddPartialPayloadDataPoint(payloadDataPoint);
	}
	private void parseSensorData(String sensorString)
	{
		PayloadDataPoint payloadDataPoint = new PayloadDataPoint();
		//load with sensor data
		partialDataPoints.AddPartialPayloadDataPoint(payloadDataPoint);
	}
	
}
