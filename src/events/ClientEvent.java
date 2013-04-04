package events;

import java.util.EventObject;

public class ClientEvent extends EventObject 
{

	private static final long serialVersionUID = 1L;
	public String clientIPAddress;
	public String type;
	
	public ClientEvent(Object source, String clientIPAddress, String type) 
	{
		super(source);		
		this.clientIPAddress = clientIPAddress;
		this.type = type;
	}

}
