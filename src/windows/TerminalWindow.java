package windows;

import Componets.TerminalComponent;
import bibliothek.gui.dock.DefaultDockable;

public class TerminalWindow extends DefaultDockable {

	/**
	 * @param args
	 */
	public TerminalWindow (String title)
	{
		super(title);
		this.add(new TerminalComponent());		
	}

}
