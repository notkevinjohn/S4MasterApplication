package main;



import windows.TerminalWindow;
import Client.ServerConnectionOptions;
import bibliothek.gui.DockController;
import bibliothek.gui.dock.SplitDockStation;
import bibliothek.gui.dock.station.split.SplitDockGrid;

public class S4DockController extends DockController 
{
	public SplitDockStation station;
	private SplitDockGrid grid;	
	private static S4DockController instance;
	
	public static S4DockController getInstance()
	{
		if(instance == null)
		{
			instance = new S4DockController();			
		}
		return instance;
	}
	
	
	public S4DockController() 
	{		
		station = new SplitDockStation();
		this.add(station);		
		grid = new SplitDockGrid ();		
		station.dropTree(grid.toTree());		
	}	
	public void addTerminalWindow()
	{
		grid.addDockable(0, 0, 1, 1, new TerminalWindow());
		station.dropTree(grid.toTree());		
	}
	public void addConnectionWindow()
	{		
		ServerConnectionOptions serverOptions = new ServerConnectionOptions();		
		serverOptions.setLocationRelativeTo(ClientFrame.getInstance());
		serverOptions.setVisible(true);		
		
	}
}


