package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class SQLLiteDatabaseAdapter 
{
	private static SQLLiteDatabaseAdapter instance;
	
	public static SQLLiteDatabaseAdapter getInstance()
	{
		if(instance==null)
		{
			instance = new SQLLiteDatabaseAdapter();
		}
		return instance;
	}
	
	Connection connection = null;
	Statement statement = null;
	
	public SQLLiteDatabaseAdapter ()
	{
		try
		{
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:s4Data.db");
		} 
		catch (Exception e) 
		{			
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );		
			System.exit(0);
		}  
		System.out.println("Opened database successfully");
		createTables();
	}
	
	private void createTables()
	{
		try
		{
			statement = connection.createStatement();
			
			String payloadDataTable = "CREATE TABLE if not exists payloaddata ( "+
				    "id INTEGER  PRIMARY KEY NOT NULL,"+
				    "Payload VARCHAR( 20 ) NOT NULL,"+
				    "TimeStamp DATETIME DEFAULT CURRENT_TIMESTAMP,"+
				    "GPS_Lon DOUBLE NOT NULL,"+
				    "GPS_Lat DOUBLE NOT NULL,"+
				    "GPS_Alt DOUBLE NOT NULL,"+
				    "GPS_TimeStamp VARCHAR( 20 ) NOT NULL,"+
				    "GPS_Fix VARCHAR( 20 ) NOT NULL,"+
				    "GPS_Raw TEXT NOT NULL "+
				");";
			String sensorTable = "CREATE TABLE if not exists sensordata (" +
				   " PayloadDataID INT( 11 )      NOT NULL,"+
				    "SensorKey     VARCHAR( 50 )  NOT NULL,"+
				    "SensorValue   VARCHAR( 50 )  NOT NULL "+
				");";
			
			statement.executeUpdate(payloadDataTable+sensorTable);
			statement.close();
			connection.close();
		} 
		catch (SQLException e) 
		{		
			e.printStackTrace();
		}
		
	}
	public void writePayloadDataPointToDataBase(PayloadDataPoint payloadDataPoint) throws SQLException
	{
		
			connection = DriverManager.getConnection("jdbc:sqlite:s4Data.db");
			connection.setAutoCommit(false);
			String sql = "INSERT INTO payloaddata (payload, GPS_Lon, GPS_Lat, GPS_Alt, GPS_TimeStamp, GPS_Fix, GPS_Raw)"+
			"VALUES(?,?,?,?,?,?,?)";
			PreparedStatement pStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pStatement.setString(1, payloadDataPoint.Payload);
			pStatement.setDouble(2, payloadDataPoint.GPS_Lon);
			pStatement.setDouble(3, payloadDataPoint.GPS_Lat);
			pStatement.setDouble(4, payloadDataPoint.GPS_Alt);
			pStatement.setString(5, payloadDataPoint.GPS_TimeSpan);
			pStatement.setString(6, payloadDataPoint.GPS_Fix);
			pStatement.setString(7, payloadDataPoint.GPS_Raw);
			pStatement.executeUpdate();
			
			int id = 0;
			ResultSet generatedKeys = pStatement.getGeneratedKeys();
			if (generatedKeys.next()) {
				id = (int) generatedKeys.getLong(1);
			}		
			
			for(int i=0; i<payloadDataPoint.SensorData.size(); i++)
			{
				KeyValuePair keyValuePair = payloadDataPoint.SensorData.get(i);
				String sensorSql = "INSERT INTO sensordata (PayloadDataID, SensorKey, SensorValue) Values (?,?,?)";
				PreparedStatement sensorStatement = connection.prepareStatement(sensorSql);
				sensorStatement.setInt(1, id);
				sensorStatement.setString(2, keyValuePair.Key);
				sensorStatement.setString(3, keyValuePair.Value);
				sensorStatement.executeUpdate();
			}
			connection.commit();
			connection.close();	
	}
	
}
