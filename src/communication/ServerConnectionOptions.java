package communication;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;

public class ServerConnectionOptions extends JDialog implements ActionListener
{	
	private static final long serialVersionUID = 1L;
	private JTextField ipText;
	private JTextField portText;	

	public ServerConnectionOptions() 
	{		
		setTitle("Server Connection Options");		
		
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));		
		
		JPanel newServerPanel = new JPanel();
		getContentPane().add(newServerPanel);
		newServerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel lblIpAddress = new JLabel("IP Address:");
		newServerPanel.add(lblIpAddress);
		
		ipText = new JTextField();
		newServerPanel.add(ipText);
		ipText.setColumns(10);
		
		JLabel lblPort = new JLabel("Port:");
		newServerPanel.add(lblPort);
		
		portText = new JTextField();
		newServerPanel.add(portText);
		portText.setColumns(5);		
		
		JPanel buttonsPanel = new JPanel();
		getContentPane().add(buttonsPanel);
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setActionCommand("Cancel");
		btnCancel.addActionListener(this);
		buttonsPanel.add(btnCancel);
		
		JButton btnConfirm = new JButton("Connect");
		btnConfirm.setActionCommand("Connect");
		btnConfirm.addActionListener(this);
		buttonsPanel.add(btnConfirm);
		
		this.pack();
		
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getActionCommand() == "Connect")
		{				
			ServerConnectionEventListener serverConnectionEventListener = new ServerConnectionEventListener(this);
			ConnectionManager.getInstance().addEventListener(serverConnectionEventListener);
			ConnectionManager.getInstance().setServerConnection(ipText.getText(), portText.getText());	
		}
		if(e.getActionCommand() == "Cancel")
		{
			this.setVisible(false);
		}
	}
	
	
	

}
