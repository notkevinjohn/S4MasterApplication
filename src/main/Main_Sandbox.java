package main;

import java.util.ArrayList;

import Data.KeyValuePair;
import Data.PayloadDataPoint;
import Server.PayloadDataPointsManager;

public class Main_Sandbox {

	/**
	 * @param args
	 */
	public static PayloadDataPointsManager payloadDataPointsManager = new PayloadDataPointsManager();
	
	public static void main(String[] args) 
	{
		String data = "<gps>SSU-01,$GPGGA,,,,,,0,00,99.99,,,,,,*48</gps>"+"\n"+"<sensor>SSU-01,Baro,101038,Temp,24.20,Hum,45.70,Temp2,23.52,AcelX,0.2157,AcelY,0.4902,AcelZ,0.9333,MagX,-246,MagY,-338,MagZ,-384</sensor>";
		data = data.replace("\n", "");
		String gpsSubstring = data.substring(0, data.indexOf("<sensor>"));
		String sensorSubstring = data.substring(data.indexOf("<sensor>"), data.length());		
		System.out.println(gpsSubstring);
		System.out.println(sensorSubstring);	
		
	}
	private static void processTestGPSData(String data)
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
	private static void processTestSensorData(String data)
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
		while(strings.size() > 0)
		{
			KeyValuePair keyValuePair = new KeyValuePair(strings.get(i), strings.get(i+1));
			strings.remove(0);
			strings.remove(0);
			payloadDataPoint.SensorData.add(keyValuePair);
		}	
		payloadDataPointsManager.AddPartialPayloadDataPoint(payloadDataPoint);
		
	}
}
