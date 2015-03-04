package main;

import java.net.SocketException;
import java.sql.SQLException;

import Data.KeyValuePair;
import Data.PayloadDataPoint;
import Data.SQLLiteDatabaseAdapter;
import Server.PayloadUDP_Socket;
import Server.ServerConsole;
import Server.ServerFrame;

public class Server 
{
	
	public static void main(String[] args) 
	{		
		SQLLiteDatabaseAdapter.getInstance();			
		initializeSockets();
		ServerFrame frame = ServerFrame.getInstance();		
		frame.setVisible(true);
		
		try 
		{
			PayloadDataPoint point = new PayloadDataPoint();
			point.Payload = "PayloadName";
			point.GPS_Lon = 0;
			point.GPS_Lat = 0;
			point.GPS_Alt = 0;
			point.GPS_TimeSpan = "0:00:00";
			point.GPS_Fix = "1";
			point.GPS_Raw = "0";
			
			point.SensorData.add(new KeyValuePair("Sensor1", "Value1"));
			point.SensorData.add(new KeyValuePair("Sensor2", "Value2"));
			point.SensorData.add(new KeyValuePair("Sensor3", "Value3"));
			
			SQLLiteDatabaseAdapter.getInstance().writePayloadDataPointToDataBase(point);
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void initializeSockets ()
	{
		try
		{		
			PayloadUDP_Socket socket = new PayloadUDP_Socket(2002);
			socket.start();
		} 
		catch (SocketException e)
		{		
			ServerConsole.getInstance().printSystemMessage(e.toString());
		}			
		
	}	

}
