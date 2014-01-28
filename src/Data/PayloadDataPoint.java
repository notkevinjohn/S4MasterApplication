package Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

public class PayloadDataPoint implements Serializable 
{	
	private static final long serialVersionUID = 1L;
	
	public String Payload;
	public Timestamp TimeStamp;
	public double GPS_Lon = Double.NaN;
	public double GPS_Lat = Double.NaN; 
	public double GPS_Alt = Double.NaN;
	public String GPS_TimeSpan;
	public String GPS_Fix;
	public String GPS_Raw;
	public ArrayList<KeyValuePair> SensorData;
	
	public PayloadDataPoint()
	{
		SensorData = new ArrayList<KeyValuePair>();
	}

}
