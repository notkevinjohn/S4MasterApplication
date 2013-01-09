package main;

import javax.swing.JFrame;

import windows.TerminalWindow;
import bibliothek.gui.DockController;
import bibliothek.gui.dock.SplitDockStation;
import bibliothek.gui.dock.station.split.SplitDockGrid;

public class S4DockController extends DockController 
{
	public SplitDockStation station;
	public SplitDockGrid grid;
	
	public S4DockController(JFrame masterFrame) 
	{
		station = new SplitDockStation();
		this.add(station);		
		grid = new SplitDockGrid ();		
		station.dropTree(grid.toTree());	
		masterFrame.add(station.getComponent());		
	}	
	public void addTerminalWindow()
	{
		grid.addDockable(0, 0, 1, 1, new TerminalWindow("Terminal"));
		station.dropTree(grid.toTree());		
	}

}
