package events;

import java.util.EventObject;

public class SpecifyServerConnectionEvent extends EventObject 
{
	
	private static final long serialVersionUID = 1L;
	public String ipAddress;
	public String port;

	public SpecifyServerConnectionEvent(Object source, String ipAddress, String port) 
	{
		super(source);		
	}

}
