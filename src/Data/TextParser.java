package Data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TextParser
{  
	
	private static ArrayList<PayloadDataPoint> dataPoints;	
      
	public static ArrayList<PayloadDataPoint> ProcessFile(String path) 
	{    	  
		  dataPoints = new ArrayList<PayloadDataPoint>();
		  PayloadDataPoint dataPoint  = new PayloadDataPoint();;
		  FileReader input;
		  try 
		  {
			  	input = new FileReader(path);
				BufferedReader bufRead = new BufferedReader(input);
				String line = null;
			  	try 
			  	{
					while ((line = bufRead.readLine()) != null)
					{				  
						
						String dataType = resolveDataType(line);
						if(dataType == GPS)
						{
							
							dataPoint = new PayloadDataPoint();
							dataPoint.SensorData = new ArrayList<KeyValuePair>();
							try {
								processGPS(line, dataPoint);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}				    				    	
						}
						if(dataType == Sensor )
					    {					    	
							try {
								processSensors(line, dataPoint);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}					    	
					    	dataPoints.add(dataPoint);
					    }
					}
				}
	  			catch (IOException e) {e.printStackTrace();}
			  	try 
			  	{
					input.close();
				} 
			  	catch (IOException e) {e.printStackTrace();}
		  } 
		  catch (FileNotFoundException e) {e.printStackTrace();}
		  return dataPoints;		  	
	  }   
	
	public static void processGPS (String GPS, PayloadDataPoint dataPoint) throws Exception
	{
		dataPoint.Payload = GPS.substring(GPS.indexOf(">")+1,GPS.indexOf(","));	
		GPS = GPS.substring(GPS.indexOf(",")+1,GPS.length());
		GPS = GPS.substring(0,GPS.indexOf("<"));
		dataPoint.GPS_Raw= GPS;		
		ArrayList<String> deserializedGPS = new ArrayList<String>();
		while(GPS.contains(","))
		{	
			String item = GPS.substring(0, GPS.indexOf(","));			 
			deserializedGPS.add(item);			  
			GPS = GPS.substring(item.length()+1, GPS.length());	
		}
		deserializedGPS.add(GPS);	
		dataPoint.GPS_Alt = (deserializedGPS.get(9).isEmpty()) ? 0:Double.parseDouble(deserializedGPS.get(9));
		dataPoint.GPS_Fix = deserializedGPS.get(6);
		dataPoint.GPS_Lat = (deserializedGPS.get(2).isEmpty()) ? 0:processLatLon(deserializedGPS.get(2));
		if(deserializedGPS.get(3).equals("S")) dataPoint.GPS_Lat *= -1;
		dataPoint.GPS_Lon = (deserializedGPS.get(4).isEmpty()) ? 0:processLatLon(deserializedGPS.get(4));
		if(deserializedGPS.get(5).equals("W")) dataPoint.GPS_Lon *= -1;
		dataPoint.GPS_TimeSpan = processTimeStamp(deserializedGPS.get(1));	  	  
		
	}
	public static void processSensors(String Sensors, PayloadDataPoint dataPoint) throws Exception
	 {		  
		  
		dataPoint.Payload = Sensors.substring(Sensors.indexOf(">")+1,Sensors.indexOf(","));	
		Sensors = Sensors.substring(Sensors.indexOf(",")+1,Sensors.length());
		Sensors = Sensors.substring(0,Sensors.indexOf("<"));		  
		  
		  String Key = "";
		  String Value = "";		 
		  while(Sensors.contains(","))
		  {			  
			  String item = Sensors.substring(0, Sensors.indexOf(","));	
			  if(item.length() > 0)
			  {
				  if(Key == "")
				  {
					  Key = item;				  
				  }
				  else
				  {
					  Value = item;				  
					  KeyValuePair keyValuePair = new KeyValuePair(Key, Value);
					  dataPoint.SensorData.add(keyValuePair);
					  Key = "";
					  Value = "";			  
				  }			 
			  }
			  Sensors = Sensors.substring(item.length()+1, Sensors.length());	
		  }
		  Value = Sensors;				  
		  KeyValuePair keyValuePair = new KeyValuePair(Key, Value);
		  dataPoint.SensorData.add(keyValuePair);		  
		
	 }
    public static String GPS = "GPS";
	public static String Sensor = "Sensor";
	public static String resolveDataType(String line)
	{
		if(line.startsWith("<gps>"))
		{
			return GPS;
		}
		if(line.startsWith("<sensor>") )
	    {					    	
			return Sensor;
	    }
		else return null;
	}
	  private static Double processLatLon (String l)
	  {
		  if(l.length()>0)
		  {
			String minutes = l.substring(l.indexOf('.')-2, l.length());		  		  
		  	String hours = l.substring(0, l.indexOf(minutes));		  
		  	Double decimal = Double.parseDouble(hours)+Double.parseDouble(minutes)/60;		  
		  	return decimal;
		  }
		  else return (double) 0;
	  }
	  
	  private static String processTimeStamp(String time)
	  {
		  if(time.length()>0)
		  {
			  time = time.substring(0, time.indexOf("."));				 
			  String seconds = time.substring(time.length()-2, time.length());		 	 
			  String minutes = time.substring(time.length()-seconds.length()-2, time.length()-seconds.length());		 
			  String hours = time.substring(time.length()-seconds.length()-minutes.length()-2, time.length()-seconds.length()-minutes.length());		 
			  Double Time = Double.parseDouble(hours)*3600+Double.parseDouble(minutes)*60+Double.parseDouble(seconds);		 
			  return Time.toString();	
		  }
		  else return "0";
	  }
      
}
