package Client;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JList;

import Data.LocalData;
import Data.PayloadDataPoint;

public class ServerConnectionOptions extends JDialog implements ActionListener
{	
	private static final long serialVersionUID = 1L;
	private JTextField ipText;
	private JTextField portText;
	private DefaultListModel<String> payloads;
	private JList<String> payloadList;

	public ServerConnectionOptions() 
	{		
		setTitle("Server Connection Options");		
		payloads = new DefaultListModel<String>();
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));		
		
		JPanel newServerPanel = new JPanel();
		getContentPane().add(newServerPanel);
		newServerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel lblIpAddress = new JLabel("IP Address:");
		newServerPanel.add(lblIpAddress);
		
		ipText = new JTextField();
		ipText.setText("127.0.0.1");
		newServerPanel.add(ipText);
		ipText.setColumns(10);
		
		JLabel lblPort = new JLabel("Port:");
		newServerPanel.add(lblPort);
		
		portText = new JTextField();
		portText.setText("2001");
		newServerPanel.add(portText);
		portText.setColumns(5);		
		
		
		JPanel buttonsPanel = new JPanel();
		getContentPane().add(buttonsPanel);
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
					
		JButton btnConfirm = new JButton("Connect");
		btnConfirm.setActionCommand("Connect");
		btnConfirm.addActionListener(this);
		buttonsPanel.add(btnConfirm);
		
		JPanel payloadPanel = new JPanel();		
		getContentPane().add(payloadPanel);
		payloadPanel.setLayout(new BorderLayout());
		JLabel lblPayload = new JLabel("Select Payload:");
		payloadPanel.add(lblPayload, BorderLayout.NORTH);
		payloadList = new JList<String>(payloads);
		payloadPanel.add(payloadList, BorderLayout.CENTER);
		
		JPanel buttonsPanel2 = new JPanel();
		getContentPane().add(buttonsPanel2);
		buttonsPanel2.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setActionCommand("Cancel");
		btnCancel.addActionListener(this);
		buttonsPanel2.add(btnCancel);
		
		JButton btnComplete = new JButton("Complete");
		btnComplete.setActionCommand("Complete");
		btnComplete.addActionListener(this);
		buttonsPanel2.add(btnComplete);
		
		this.pack();		
	}

	public void addPayloadNames (ArrayList<PayloadDataPoint> payloadNames)
	{
		for(int i=0; i<payloadNames.size(); i++)
		{
			PayloadDataPoint currentPoint = payloadNames.get(i);
			String payloadName = currentPoint.Payload;				
			payloads.addElement(payloadName);
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getActionCommand() == "Connect")
		{				
			ServerConnectionEventListener serverConnectionEventListener = new ServerConnectionEventListener(this);
			ClientToServer_Socket.getInstance().addEventListener(serverConnectionEventListener);
			ClientToServer_Socket.getInstance().setServerConnection(ipText.getText(), portText.getText());	
		}
		if(e.getActionCommand() == "Complete")
		{
			LocalData.getInstance().payloadName = payloadList.getSelectedValue();
			ClientToServer_Socket.getInstance().start();
			this.setVisible(false);
		}
		if(e.getActionCommand() == "Cancel")
		{
			this.setVisible(false);
		}
	}
	
	
	

}
