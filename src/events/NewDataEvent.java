package events;

import java.util.ArrayList;
import java.util.EventObject;

import Data.PayloadDataPoint;

public class NewDataEvent extends EventObject
{

	private static final long serialVersionUID = 1L;
	public ArrayList<PayloadDataPoint> newPayloadDataPoints;

	public NewDataEvent(Object sender) 
	{
		super(sender);
		
	}

}
