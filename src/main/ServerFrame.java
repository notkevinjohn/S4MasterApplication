package main;

import java.awt.Toolkit;

import javax.swing.JFrame;

public class ServerFrame extends JFrame 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ServerFrame instance;
	
	public static ServerFrame getInstance ()
	{
		if(instance==null)
		{
			instance = new ServerFrame();
		}
		return instance;
	}
	
	public ServerFrame ()
	{
		JFrame frame = new JFrame();
		Toolkit tk = Toolkit.getDefaultToolkit();  
		int xSize = ((int) tk.getScreenSize().getWidth());  
		int ySize = ((int) tk.getScreenSize().getHeight());  
		frame.setBounds(0, 0,xSize, ySize);
		frame.setVisible(true);	
	}
	
}
