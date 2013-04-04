package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import Data.KeyValuePair;
import Data.PayloadDataPoint;

public class ServerToPayload_Socket extends Thread
{
	public Socket payloadSocket;
	public PayloadDataPointsManager payloadDataPointsManager = new PayloadDataPointsManager();
	
	Object payloadDataObject = null;
	private int available = 0;
	private InputStream inputStream;
	private OutputStream outputStream;
	private boolean initialized = false;
	
	public ServerToPayload_Socket (Socket _payloadSocket)
	{
		this.payloadSocket = _payloadSocket;
		try
		{			
			inputStream = payloadSocket.getInputStream();	
			outputStream = payloadSocket.getOutputStream();		
		}
		catch (IOException e) 
		{			
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		while(true)
		{
			try 
			{
				available =inputStream.available();
			} 
			catch (IOException e) 
			{				
				e.printStackTrace();
			}
			if(available>0)
			{	
				byte packet[] = new byte[available];			
				try 
				{					
					inputStream.read(packet, 0, available);
				} 
				catch (IOException e) {e.printStackTrace();}
				String data = new String(packet);				
				
				if(initialized == false)
				{
					PrintWriter out = new PrintWriter(outputStream, true);
					out.print("@");
					out.flush();
					initialized = true;
				}
				
				data = data.replace("\n", "");
				
				if(data.startsWith("<gps>") && data.indexOf("<sensor>") != 0)
				{
					String gpsSubstring = data.substring(0, data.indexOf("<sensor>"));
					String sensorSubstring = data.substring(data.indexOf("<sensor>"), data.length());					
					processGPS(gpsSubstring);					
					processSensors(sensorSubstring);	
				}
				
				else if(data.startsWith("<gps>"))
				{					
					processGPS(data);
				}
				else if(data.startsWith("<sensor>"))
				{
					processSensors(data);
				}
			}
			
		}
		
	}
	private void processGPS(String data)
	{
		PayloadDataPoint payloadDataPoint = new PayloadDataPoint();		
		ArrayList<String> strings = new ArrayList<String>();
		data = data.replace("<gps>", "");
		data = data.replace("</gps>", "");		
		payloadDataPoint.GPS_Raw = data;		
		while(data.length() > 0)
		{
			String substring = "";
			if(data.indexOf(",") != -1)
			{
				substring = data.substring(0, data.indexOf(","));
				strings.add(substring);			
				data = data.substring(data.indexOf(",")+1, data.length());
			}
			else
			{				
				substring = data.substring(0, data.length());
				strings.add(substring);			
				data = "";
			}		
		}
		payloadDataPoint.Payload = strings.get(0);		
		try
		{
			payloadDataPoint.GPS_Lat = Double.parseDouble(strings.get(3));
			if(strings.get(4) == "S") payloadDataPoint.GPS_Lat *= -1;
		}
		catch(NumberFormatException e)
		{	
			payloadDataPoint.GPS_Lat = 0;
		}	
		try
		{
			payloadDataPoint.GPS_Lon = Double.parseDouble(strings.get(5));
			if(strings.get(6) == "W") payloadDataPoint.GPS_Lat *= -1;
		}
		catch(NumberFormatException e)
		{
			payloadDataPoint.GPS_Lon = 0;	
		}	
		try
		{
			payloadDataPoint.GPS_Alt = Double.parseDouble(strings.get(10));
		}
		catch(NumberFormatException e)
		{	
			payloadDataPoint.GPS_Alt = 0;
		}		
		payloadDataPoint.GPS_Fix = strings.get(7);
		payloadDataPoint.GPS_TimeSpan= strings.get(2);	
		
		payloadDataPointsManager.AddPartialPayloadDataPoint(payloadDataPoint);
		
	
	}
	private void processSensors(String data)
	{
		try
		{
			PayloadDataPoint payloadDataPoint = new PayloadDataPoint();	
			ArrayList<String> strings = new ArrayList<String>();
			data = data.replace("<sensor>", "");
			data = data.replace("</sensor>", "");			
			while(data.length() > 0)
			{
				String substring = "";
				if(data.indexOf(",") != -1)
				{
					substring = data.substring(0, data.indexOf(","));
					strings.add(substring);			
					data = data.substring(data.indexOf(",")+1, data.length());
				}
				else
				{				
					substring = data.substring(0, data.length());
					strings.add(substring);			
					data = "";
				}		
			}
			payloadDataPoint.Payload = strings.get(0);
			strings.remove(0);
			payloadDataPoint.SensorData = new ArrayList<KeyValuePair>();
			int i=0;			
			while(i+1<strings.size())
			{
				KeyValuePair keyValuePair = new KeyValuePair(strings.get(i), strings.get(i+1));
				strings.remove(0);
				strings.remove(0);
				payloadDataPoint.SensorData.add(keyValuePair);
			}	
			payloadDataPointsManager.AddPartialPayloadDataPoint(payloadDataPoint);
		}
		catch(Error e)
		{
			e.printStackTrace();
		}
	}
}
