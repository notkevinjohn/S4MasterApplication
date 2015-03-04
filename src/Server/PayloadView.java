package Server;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JPanel;

import Data.PayloadDataPoint;

public class PayloadView extends JPanel 
{
	private static final long serialVersionUID = 1L;
	private static PayloadView instance;	
	
	public static PayloadView getInstance ()
	{
		if(instance==null)
		{
			instance = new PayloadView();
		}
		return instance;
	}
	public PayloadView ()
	{
		setLayout(new FlowLayout(FlowLayout.LEFT));
	}
	public void updatePayloadDataPoint(PayloadDataPoint dataPoint)
	{
		PayloadTile payloadTile  = getPanelByPayloadName(dataPoint.Payload);
		if(payloadTile==null)
		{
			payloadTile = new PayloadTile(dataPoint.Payload);
			add(payloadTile);			
		}	
		if(!payloadTile.isVisible())
		{
			payloadTile.setVisible(true);
		}
		payloadTile.seconds = -1;
		if(!payloadTile.mode.equals(PayloadTile.connected)) payloadTile.setMode(PayloadTile.connected);
		if (!Double.isNaN(dataPoint.GPS_Alt)) payloadTile.AltLabel.setText(Double.toString(dataPoint.GPS_Alt));
		if (!Double.isNaN(dataPoint.GPS_Lat)) payloadTile.LatLabel.setText(Double.toString(dataPoint.GPS_Lat));
		if (!Double.isNaN(dataPoint.GPS_Lon)) payloadTile.LonLabel.setText(Double.toString(dataPoint.GPS_Lon));
		
	}		
	private PayloadTile getPanelByPayloadName(String payloadName)
	{
		PayloadTile payloadTile = null;
		for (Component c : this.getComponents()) {
		    if (c instanceof PayloadTile) 
		    { 			  
		    	if(((PayloadTile) c).payloadName .equals(payloadName))
			      {
		    		payloadTile = (PayloadTile) c;;
			      }
		    }
		}
		return payloadTile;		
	}
}

