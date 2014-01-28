package Server;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;

import Data.PayloadDataPoint;
import Data.SQLLiteDatabaseAdapter;

public class PayloadDataPointsManager extends ArrayList<PayloadDataPoint>
{	
	private static final long serialVersionUID = 1L;
	
	private PayloadDataPoint getPayloadDataPointByPayloadName (String payload)
	{
		PayloadDataPoint partialPayloadDataPoint = null;
		for(int i=0; i<this.size();i++)
		{			
			if(this.get(i).Payload.equals(payload))
			{
				partialPayloadDataPoint = (PayloadDataPoint) this.get(i);
			}
		}
		return partialPayloadDataPoint;
	}
	public Boolean containsPayloadDataPoints (PayloadDataPoint payloadDataPoint)
	{
		Boolean value = false;
		for(int i=0; i<this.size();i++)
		{			
			if(this.get(i) == payloadDataPoint)
			{
				value = true;
			}
		}
		return value;
	}
	public void AddPartialPayloadDataPoint(PayloadDataPoint payloadDataPoint)
	{
		PayloadDataPoint existingDataPoint = getPayloadDataPointByPayloadName(payloadDataPoint.Payload);		
		if(existingDataPoint == null)
		{
			this.addPartialPayloadDataPoint(payloadDataPoint);
		}
		else
		{
			try 
			{
				sendPayloadDataPointToDatabase(mergeDataPoints(existingDataPoint, payloadDataPoint));
			} 
			catch (IllegalArgumentException | IllegalAccessException e) 
			{				
				e.printStackTrace();
			}
		}
	}
	private void addPartialPayloadDataPoint (PayloadDataPoint dataPoint)
	{
		this.add(dataPoint);		
		PayloadDataPointTimer timer = new PayloadDataPointTimer(dataPoint, this, 500, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{		
				PayloadDataPointTimer timer = (PayloadDataPointTimer)e.getSource();
				PayloadDataPoint dataPoint = timer.dataPoint;
				if(timer.manager.contains(dataPoint))
				{
					sendPayloadDataPointToDatabase(((PayloadDataPointTimer)e.getSource()).dataPoint);							
				}				
			}			
		});
		timer.start();
	}	
	private PayloadDataPoint mergeDataPoints(PayloadDataPoint dataPoint1, PayloadDataPoint dataPoint2) throws IllegalArgumentException, IllegalAccessException
	{		
		Field[] fields = PayloadDataPoint.class.getDeclaredFields();
		for(Field field: fields)
		{			
			if(field.getModifiers()==1)
			{				
				if(!field.getType().toString().equals("double") && field.get(dataPoint1) == null)
				{
					field.set(dataPoint1,field.get(dataPoint2));		
				}
				else if(field.getType().toString().equals("double") && ((Double)field.get(dataPoint1)).isNaN())
				{
					field.set(dataPoint1,field.get(dataPoint2));
				}
			}
				
		} 		
		if(dataPoint1.SensorData.size() ==0 && dataPoint2.SensorData.size() !=0)
		{
			dataPoint1.SensorData = dataPoint2.SensorData;
		}
		return dataPoint1;
	}
	private void sendPayloadDataPointToDatabase (PayloadDataPoint payloadDataPoint)
	{		
		try 
		{			
			SQLLiteDatabaseAdapter.getInstance().writePayloadDataPointToDataBase(payloadDataPoint);			
		} 
		catch (SQLException e) 
		{			
			e.printStackTrace();
		}
		this.remove(payloadDataPoint);
	}
}

