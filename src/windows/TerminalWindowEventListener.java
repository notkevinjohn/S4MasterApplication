package windows;

import events.INewDataEventListener;
import events.NewDataEvent;

public class TerminalWindowEventListener implements INewDataEventListener 
{
	private TerminalWindow terminalWindow; 
	
	public TerminalWindowEventListener(TerminalWindow terminalWindow)
	{
		this.terminalWindow = terminalWindow;
	}
	@Override
	public void handleNewDataEvent(NewDataEvent e) 
	{
		if(terminalWindow != null)
		{
			terminalWindow.addNewDataPoints(e.newPayloadDataPoints);
		}
		
	}

}
