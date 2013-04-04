package Server;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Data.KeyValuePair;
import Data.PayloadDataPoint;
import Data.PayloadDataRequest;


public class MySQLConnection 
{
	private Connection connect = null;	
	private static MySQLConnection instance;
	
	public static MySQLConnection getInstance ()
	{
		if(instance==null)
		{
			instance = new MySQLConnection();
		}
		return instance;
	}	
	public MySQLConnection() 
	{
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");		
			connect = DriverManager.getConnection("jdbc:mysql://localhost/S4?user=S4Application");			  
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}		
	}
	public boolean writePayloadDataPointToDataBase (PayloadDataPoint payloadDataPoint)
	{
		boolean returnValue = false;
		try 
		{
			PreparedStatement preparedStatement;
			preparedStatement = connect.prepareStatement("insert into payloaddata (Payload, GPS_Lon, GPS_Lat, GPS_Alt, GPS_TimeStamp, GPS_Fix, GPS_Raw) values (?, ?, ?, ? , ?, ?, ?)");
			preparedStatement.setString(1, payloadDataPoint.Payload);
			preparedStatement.setDouble(2, payloadDataPoint.GPS_Lon);
			preparedStatement.setDouble(3, payloadDataPoint.GPS_Lat);
			preparedStatement.setDouble(4, payloadDataPoint.GPS_Alt);
			preparedStatement.setString(5, payloadDataPoint.GPS_TimeSpan);
			preparedStatement.setString(6, payloadDataPoint.GPS_Fix);
			preparedStatement.setString(7, payloadDataPoint.GPS_Raw);
			preparedStatement.executeUpdate();
			ResultSet results = preparedStatement.getGeneratedKeys();			
			results.next();
			int id= results.getInt(1);
			
			for (int i=0; i<payloadDataPoint.SensorData.size(); i++) 
			{
				PreparedStatement sensorPointStatement;
				KeyValuePair sensorPoint = payloadDataPoint.SensorData.get(i);
				sensorPointStatement = connect.prepareStatement("insert into sensordata (PayloadDataID, SensorKey, SensorValue) values (?, ?, ?)");
				sensorPointStatement.setInt(1, id);
				sensorPointStatement.setString(2, sensorPoint.Key);
				sensorPointStatement.setString(3, sensorPoint.Value);
				sensorPointStatement.executeUpdate();
			}
		} 
		catch (Exception e) 
		{			
			//e.printStackTrace();
			returnValue = false;
		}
		return returnValue;
	}
	public ArrayList<PayloadDataPoint> readPayloadDataPointsFromDataBase(PayloadDataRequest payloadRequest)
	{
		ArrayList<PayloadDataPoint> payloadDataPoints = new ArrayList<PayloadDataPoint>();
		try
		{
			PreparedStatement preparedStatement;
			preparedStatement = connect.prepareStatement("select * from payloaddata WHERE Payload=? AND TimeStamp > ?");
			preparedStatement.setString(1, payloadRequest.payloadName);
			preparedStatement.setTimestamp(2, payloadRequest.timestamp);
			ResultSet results = preparedStatement.executeQuery();
			while (results.next())
			{
				PayloadDataPoint payloadDataPoint = new PayloadDataPoint();
				payloadDataPoint.Payload = results.getString("Payload");
				payloadDataPoint.TimeStamp = results.getTimestamp("TimeStamp");
				payloadDataPoint.GPS_Lon = results.getDouble("GPS_Lon");
				payloadDataPoint.GPS_Lat = results.getDouble("GPS_Lat");
				payloadDataPoint.GPS_Alt = results.getDouble("GPS_Alt");
				payloadDataPoint.GPS_TimeSpan = results.getString("GPS_TimeStamp");
				payloadDataPoint.GPS_Fix = results.getString("GPS_Fix");
				payloadDataPoint.GPS_Raw = results.getString("GPS_Raw");
				
				payloadDataPoint.SensorData = new ArrayList<KeyValuePair>();
				PreparedStatement sensorPointStatement;
				sensorPointStatement = connect.prepareStatement("select * from sensordata WHERE PayloadDataID = ?");
				sensorPointStatement.setInt(1, results.getInt("ID"));
				ResultSet sensorResults = sensorPointStatement.executeQuery();
				while (sensorResults.next())
				{
					KeyValuePair sensorPoint = new KeyValuePair
							(
							sensorResults.getString("SensorKey"), 
							sensorResults.getString("SensorValue")
							);					
					payloadDataPoint.SensorData.add(sensorPoint);
				}
				payloadDataPoints.add(payloadDataPoint);
			}
		}
		catch (Exception e) 
		{			
			e.printStackTrace();
		}
		return payloadDataPoints;
	}
	public ArrayList<PayloadDataPoint> payloadsFromToday()
	{
		ArrayList<PayloadDataPoint> payloads = new ArrayList<PayloadDataPoint>();
		PreparedStatement preparedStatement;
		try 
		{
			preparedStatement = connect.prepareStatement("select distinct Payload from payloaddata where TimeStamp > ? ");	
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			long time = cal.getTimeInMillis();
			preparedStatement.setTimestamp(1, new Timestamp(time));
			ResultSet results = preparedStatement.executeQuery();
			while (results.next())
			{
				PayloadDataPoint payloadDataPoint = new PayloadDataPoint();
				payloadDataPoint.Payload = results.getString("Payload");
				payloads.add(payloadDataPoint);			
			}	
		} 
		catch (SQLException e) 
		{			
			e.printStackTrace();
		}		
		return payloads;
	}
}
