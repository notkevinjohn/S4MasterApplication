package main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import events.WindowEvent;
import events.IWindowEventListener;

public class MenuBar extends JMenuBar implements ActionListener
{
	public static String terminal = "Terminal";
	public static String connect = "Connect";
	public static String disconnect = "Disconnect";
	
	private static final long serialVersionUID = 1L;
	private List<Object> listeners = new ArrayList<>();
	private JMenu connectionMenu;
	
	public synchronized void addEventListener(IWindowEventListener listener)
	{
		listeners.add(listener);
	}
	public synchronized void removeEventListener(IWindowEventListener listener)
	{
		listeners.remove(listener);
	}
	
	public MenuBar()
	{
		createFileMenu();		
		createPreferencesMenu();
		createEditMenu();
		createWindowsMenu();
		createModeMenu();	
		createConnectionIndicator();		
	}
	
	private void createFileMenu()
	{
		JMenu fileMenu = new JMenu("File");		
		fileMenu.setMnemonic(KeyEvent.VK_F);		
		this.add(fileMenu);
		
		JMenuItem exitItem = new JMenuItem("Exit");
		fileMenu.add(exitItem);		
		
		/*CODE STUB: add file-related items*/
	}
	
	private void createPreferencesMenu()
	{
		JMenu preferencesMenu = new JMenu("Preferences");
		preferencesMenu.setMnemonic(KeyEvent.VK_P);		
		this.add(preferencesMenu);
		
		/*CODE STUB: add preferences-related items*/
	}
	
	private void createEditMenu()
	{
		JMenu editMenu = new JMenu("Edit");
		editMenu.setMnemonic(KeyEvent.VK_E);		
		this.add(editMenu);
		
		/*CODE STUB: add edit-related items*/
	}
	
	private void createWindowsMenu()
	{
		JMenu windowsMenu = new JMenu("Windows");
		windowsMenu.setMnemonic(KeyEvent.VK_W);		
		this.add(windowsMenu);
		
		JMenuItem terminalItem = new JMenuItem(terminal);
		terminalItem.setMnemonic(KeyEvent.VK_T);
		terminalItem.addActionListener(this);
		windowsMenu.add(terminalItem);	
		
		/*CODE STUB: add menu-related items*/
	}
	
	private void createModeMenu()
	{
		JMenu modeMenu = new JMenu("Mode");
		modeMenu.setMnemonic(KeyEvent.VK_M);		
		this.add(modeMenu);
		
		/*CODE STUB: add mode-related items*/
	}
	
	private void createConnectionIndicator ()
	{
		connectionMenu = new JMenu("Connected to:");
		connectionMenu.setMnemonic(KeyEvent.VK_C);
		this.add(connectionMenu);
		
		JMenuItem connectItem = new JMenuItem(connect);
		connectItem.setMnemonic(KeyEvent.VK_C);
		connectItem.addActionListener(this);
		connectionMenu.add(connectItem);
		
		JMenuItem disconnectItem = new JMenuItem(disconnect);
		disconnectItem.setMnemonic(KeyEvent.VK_D);
		disconnectItem.addActionListener(this);
		connectionMenu.add(disconnectItem);
	}
	public void ConnectionIndicatorConnected(String serverIP, int serverPort)
	{
		connectionMenu.setText("Connected to: "+serverIP+" : "+serverPort);
		connectionMenu.setForeground(new Color(0x00AA00));
	}
	public void ConnectionIndicatorDisconnected()
	{
		connectionMenu.setForeground(new Color(0xFF0000));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		WindowEvent windowEvent = new WindowEvent(this, e.getActionCommand());
		fireEvent(windowEvent);		
	}
	private synchronized void fireEvent(WindowEvent windowEvent)
	{
		Iterator<Object> i = listeners.iterator();
		while(i.hasNext())
		{
			((IWindowEventListener) i.next()).handleWindowEvent(windowEvent);
		}
	}
	
}
