package Data;

import java.sql.Timestamp;
import java.util.ArrayList;

public class LocalData 
{	
	
	private static LocalData instance;	
	public String payloadName = "Test Payload";
	
	public static LocalData getInstance ()
	{
		if(instance==null)
		{
			instance = new LocalData();
		}
		return instance;
	}	
	
	private ArrayList<PayloadDataPoint> payloadDataPoints;
	public ArrayList<PayloadDataPoint> getPayloadDataPoints ()
	{
		if(payloadDataPoints == null)
		{
			payloadDataPoints = new ArrayList<PayloadDataPoint>();
		}		
		return payloadDataPoints;
		
	}
	
	public void addPayloadDataPoints (ArrayList<?> additionalPayloadDataPoints)
	{
		if(payloadDataPoints == null)
		{
			payloadDataPoints = new ArrayList<PayloadDataPoint>();
		}	
		for(int i=0; i<additionalPayloadDataPoints.size(); i++)
		{
			if(additionalPayloadDataPoints.get(i) instanceof PayloadDataPoint)
			{
				payloadDataPoints.add((PayloadDataPoint)additionalPayloadDataPoints.get(i));
			}
		}				
	} 
	
	public Timestamp mostRecentTimestamp ()
	{
		if(payloadDataPoints == null)
		{
			payloadDataPoints = new ArrayList<PayloadDataPoint>();
		}
		Timestamp timestamp = null;
		for(int i=0; i<payloadDataPoints.size(); i++)
		{
			if(timestamp == null)
			{
				timestamp = payloadDataPoints.get(i).TimeStamp;
			}
			else if(payloadDataPoints.get(i).TimeStamp.after(timestamp))
			{
				timestamp = payloadDataPoints.get(i).TimeStamp;
			}
		}
		if(timestamp == null)
		{
			timestamp = new Timestamp(0);
		}
		return timestamp;
	}
	
	public void clearPayloadDataPoints()
	{
		payloadDataPoints = new ArrayList<PayloadDataPoint>();
	}
	

}
