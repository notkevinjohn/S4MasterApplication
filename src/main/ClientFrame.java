package main;

import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JFrame;

import events.WindowEventListener;

public class ClientFrame extends JFrame 
{
	
	private static final long serialVersionUID = 1L;
	private static ClientFrame instance;
	public MenuBar menuBar;

	public static ClientFrame getInstance ()
	{
		if(instance==null)
		{
			instance = new ClientFrame();
		}
		return instance;
	}
	
	private ClientFrame ()
	{
		menuBar = new MenuBar();		
		JFrame frame = new JFrame();
		frame.setJMenuBar(menuBar);		
		frame.add(S4DockController.getInstance().station.getComponent());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Toolkit tk = Toolkit.getDefaultToolkit();  
		//int xSize = ((int) tk.getScreenSize().getWidth());  
		//int ySize = ((int) tk.getScreenSize().getHeight());  
		int xSize = 800;  
		int ySize = 600;  
		frame.setBounds(0, 0,xSize, ySize);
		frame.setVisible(true);	
		WindowEventListener menuBarListener = new WindowEventListener();
		menuBar.addEventListener(menuBarListener);	
		
		java.net.URL url = ClassLoader.getSystemResource("Graphics/TerminalIcon.png");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.createImage(url);
		frame.setIconImage(img);
		frame.setTitle("S4 - Client");
		
	}
}
