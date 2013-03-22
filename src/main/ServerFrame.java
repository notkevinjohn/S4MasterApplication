package main;

import java.awt.Image;
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
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//Toolkit tk = Toolkit.getDefaultToolkit();  
		//int xSize = ((int) tk.getScreenSize().getWidth());  
		//int ySize = ((int) tk.getScreenSize().getHeight());  
		
		int xSize = 800;  
		int ySize = 600;  
		frame.setBounds(0, 0,xSize, ySize);
		frame.setVisible(true);	
		
		java.net.URL url = ClassLoader.getSystemResource("Graphics/ServerIcon.png");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.createImage(url);
		frame.setIconImage(img);
		frame.setTitle("S4 - Server");
	}
	
}
