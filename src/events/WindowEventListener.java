package events;

import main.MenuBar;
import main.S4DockController;

public class WindowEventListener implements IWindowEventListener
{	
	
	@Override
	public void handleWindowEvent(WindowEvent e) 
	{
		if(e.actionCommand == MenuBar.terminal)
		{
			S4DockController.getInstance().addTerminalWindow();
		}
		if(e.actionCommand == MenuBar.connect)
		{
			S4DockController.getInstance().addConnectionWindow();
		}			
	}
	
}