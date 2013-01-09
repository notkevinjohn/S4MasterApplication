package events;

import java.util.EventObject;

public class WindowEvent extends EventObject {

	private static final long serialVersionUID = 1L;
	public String actionCommand;
	public WindowEvent(Object sender, String actionCommand) 
	{
		super(sender);
		this.actionCommand = actionCommand;
	}
	
	

}
