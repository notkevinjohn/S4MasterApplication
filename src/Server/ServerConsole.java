package Server;

import javax.swing.DefaultListModel;
import javax.swing.JList;

public class ServerConsole extends JList<String> {

	private static final long serialVersionUID = 1L;
	private static ServerConsole instance;
	
	public static ServerConsole getInstance ()
	{
		if(instance==null)
		{
			instance = new ServerConsole();
		}
		return instance;
	}	
	private DefaultListModel<String> listModel = new DefaultListModel<String>();
	public ServerConsole ()
	{
		this.setModel(listModel);
	}
	public void printSystemMessage(String message)
	{
		listModel.addElement(message);
		ensureIndexIsVisible(listModel.size()-1);
		if(listModel.size()>=500)
		{
			listModel.remove(0);
		}
	}
}
