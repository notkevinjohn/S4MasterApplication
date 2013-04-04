package windows;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import Data.LocalData;
import Data.PayloadDataPoint;
import bibliothek.gui.dock.DefaultDockable;


public class TerminalWindow extends DefaultDockable 
{

	private JList<String> terminalList;
	private DefaultListModel<String> listModel;
	
	
	public TerminalWindow ()
	{
		super("Terminal: "+LocalData.getInstance().payloadName);		
		listModel = new DefaultListModel<String>();
		terminalList = new JList<String>(listModel);        
        JScrollPane listScrollPane = new JScrollPane(terminalList);
        add(listScrollPane);
        addNewDataPoints(LocalData.getInstance().getPayloadDataPoints());
        LocalData.getInstance().addEventListener(new TerminalWindowEventListener(this));		
	}	
	public void addNewDataPoints (ArrayList<PayloadDataPoint> newDataPoints)
	{		
		for(int i=0; i<newDataPoints.size(); i++)
		{
			PayloadDataPoint currentPoint = newDataPoints.get(i);
			String payloadString = currentPoint.TimeStamp.toString()+", "+currentPoint.Payload
					+", Lon: "+currentPoint.GPS_Lon+", Lat: "+currentPoint.GPS_Lat+", Alt: "+currentPoint.GPS_Alt;
			listModel.addElement(payloadString);
		}
	}
	

}

