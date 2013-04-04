package Server;

import java.awt.event.ActionListener;
import javax.swing.Timer;

import Data.PayloadDataPoint;

public class PayloadDataPointTimer extends Timer 
{	
	private static final long serialVersionUID = 1L;
	public PayloadDataPoint dataPoint;
	public PayloadDataPointsManager manager;
	
	public PayloadDataPointTimer(PayloadDataPoint dataPoint, PayloadDataPointsManager manager, int delay, ActionListener actionListener)
	{
		super(delay, actionListener);
		this.manager = manager;
		this.dataPoint = dataPoint;
	}

}
