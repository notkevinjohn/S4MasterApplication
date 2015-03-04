package Server;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.Font;
import java.awt.Color;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.JSplitPane;


public class ServerFrame extends JFrame 
{

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
	
	@SuppressWarnings("static-access")
	public ServerFrame ()
	{
		setSize(1280,720);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("S4 Server");
		java.net.URL url = ClassLoader.getSystemResource("Graphics/ServerIcon.png");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.createImage(url);
		setIconImage(img);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		
		JLabel lblServerIpAddress = new JLabel("Server IP Address: ");
		panel.add(lblServerIpAddress);
		
		String ipAddress;
		try {
			InetAddress localHost = InetAddress.getLocalHost();
			ipAddress = localHost.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			ipAddress = "IP ERROR";
		}
		JLabel label;
		label = new JLabel(ipAddress);		
	
		label.setForeground(Color.BLUE);
		label.setFont(new Font("Tahoma", Font.PLAIN, 26));
		panel.add(label);		
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.5d);	
		getContentPane().add(splitPane, BorderLayout.CENTER);
		
		ServerConsole serverConsole = ServerConsole.getInstance();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(serverConsole);
		splitPane.setRightComponent(scrollPane);		
		
		PayloadView payloadView = PayloadView.getInstance();		
		payloadView.setSize(splitPane.getLeftComponent().getSize());
		JScrollPane scrollPane2 = new JScrollPane(payloadView,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,  JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane2.setViewportView(payloadView);
		splitPane.setLeftComponent(payloadView);	//<!------Fix resizing issue of PayloadView if you have time. 	
		

		
		
	}
	
}
