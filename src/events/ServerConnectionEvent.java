package events;

import java.util.EventObject;

public class ServerConnectionEvent extends EventObject {

	private static final long serialVersionUID = 1L;
	public Boolean success;
	public String message;
	public String serverIP;
	public int serverPort;

	public ServerConnectionEvent(Object sender, Boolean success, String message, String serverIP, int serverPort)
	{		
		super(sender);
		this.success = success;
		this.message = message;
		this.serverIP = serverIP;
		this.serverPort = serverPort;
	}

}
