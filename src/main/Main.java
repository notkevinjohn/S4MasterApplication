package main;

import javax.swing.JFrame;

import events.IWindowEventListener;
import events.WindowEvent;

public class Main 
{
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		MenuBar menuBar = new MenuBar();	
				
		JFrame frame = new JFrame();
		frame.setJMenuBar(menuBar);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(20, 20, 1240, 720);
		frame.setVisible(true);	
		
		S4DockController controller = new S4DockController(frame);
		windowEventListener menuBarListener = new windowEventListener(controller);
		menuBar.addEventListener(menuBarListener);
				
	}	
	public static class windowEventListener implements IWindowEventListener
	{
		private S4DockController controller;
		public windowEventListener(S4DockController controller)
		{
			this.controller = controller;
		}
		@Override
		public void handleWindowEvent(WindowEvent e) 
		{
			if(e.actionCommand == MenuBar.terminal)
			{
				controller.addTerminalWindow();
			}
		}
		
	}

}
