package main;

import java.awt.Toolkit;
import javax.swing.JFrame;

import events.WindowEventListener;

public class MainFrame extends JFrame 
{
	
	private static final long serialVersionUID = 1L;
	private static MainFrame instance;
	public MenuBar menuBar;

	public static MainFrame getInstance ()
	{
		if(instance==null)
		{
			instance = new MainFrame();
		}
		return instance;
	}
	
	private MainFrame ()
	{
		menuBar = new MenuBar();		
		JFrame frame = new JFrame();
		frame.setJMenuBar(menuBar);		
		frame.add(S4DockController.getInstance().station.getComponent());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Toolkit tk = Toolkit.getDefaultToolkit();  
		int xSize = ((int) tk.getScreenSize().getWidth());  
		int ySize = ((int) tk.getScreenSize().getHeight());  
		frame.setBounds(0, 0,xSize, ySize);
		frame.setVisible(true);	
		WindowEventListener menuBarListener = new WindowEventListener();
		menuBar.addEventListener(menuBarListener);	
		
	}
}
