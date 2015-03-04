package Server;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;

//import com.jgoodies.forms.layout.FormLayout;
//import com.jgoodies.forms.layout.ColumnSpec;
//import com.jgoodies.forms.factories.FormFactory;
//import com.jgoodies.forms.layout.RowSpec;

import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class PayloadTile extends JPanel 
{
	private static final long serialVersionUID = 1L;
	public String payloadName;
	public Timer timer;
	public int seconds = 0;
	public JLabel AltLabel;	
	public JLabel strengthLabel;
	public JLabel LonLabel;
	public JLabel LatLabel;
	
	private JLabel timerLabel;
	public PayloadTile(String _payloadName)
	{
		setBorder(new LineBorder(new Color(0, 255, 51), 5, true));
		payloadName = _payloadName;
		setBackground(Color.LIGHT_GRAY);
		/*setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("16px"),
				ColumnSpec.decode("88px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("112px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("47px"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(39dlu;default)"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("22px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));*/
		
		JLabel lblPayloadName = new JLabel("Payload Name");
		lblPayloadName.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblPayloadName, "2, 2, left, center");
		
		JLabel lblPayloadname = new JLabel(_payloadName);
		lblPayloadname.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblPayloadname.setForeground(new Color(51, 102, 255));
		add(lblPayloadname, "4, 2, 2, 1, left, top");
		
		JLabel lblLat = new JLabel("Lat");
		lblLat.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblLat, "6, 2");
		
		LatLabel = new JLabel("?");
		LatLabel.setForeground(new Color(51, 102, 255));
		LatLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		add(LatLabel, "8, 2");
		
		JLabel lblLastDataPoint = new JLabel("Last Data");
		lblLastDataPoint.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblLastDataPoint, "2, 4, left, center");
		
		timerLabel = new JLabel(seconds+" s");
		timerLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		timerLabel.setForeground(new Color(51, 102, 255));
		add(timerLabel, "4, 4");		
		
		JLabel lblLon = new JLabel("Lon");
		lblLon.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblLon, "6, 4");
		
		LonLabel = new JLabel("?");
		LonLabel.setForeground(new Color(51, 102, 255));
		LonLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		add(LonLabel, "8, 4");
		
		JLabel lblSignalStrength = new JLabel("Signal Strength");
		add(lblSignalStrength, "2, 6");
		
		strengthLabel = new JLabel("? dB");
		strengthLabel.setForeground(new Color(51, 102, 255));
		strengthLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		add(strengthLabel, "4, 6");
		
		JLabel lblAlt = new JLabel("Alt");
		lblAlt.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblAlt, "6, 6");
		
		AltLabel = new JLabel("? M");
		AltLabel.setForeground(new Color(51, 102, 255));
		AltLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		add(AltLabel, "8, 6");
	
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() 
			{
				seconds ++;				
				timerLabel.setText(seconds+" s");
				if(seconds > 10)
				{
					setWarning();
				}
				if(seconds>60)
				{
					setDisconnected();
				}		
				if(seconds>120)
				{
					setVisible(false);
				}
			}
		}, 0,1000);	
	}
	
	public static String connected = "Connected";
	public static String warning = "Warning";
	public static String disconnected = "Disconnected";
	public String mode = connected;
	
	public void setMode(String _mode)
	{
		if(_mode.equals(connected)) setConnected();
		if(_mode.equals(warning)) setWarning();
		if(_mode.equals(disconnected)) setDisconnected();
	}
	private void setConnected ()
	{
		setBorder(new LineBorder(new Color(0, 255,0), 5, true));
		mode = connected;
	}
	private void setWarning ()
	{
		setBorder(new LineBorder(new Color(255, 255, 0), 5, true));
		mode = warning;
	}
	private void setDisconnected ()
	{
		setBorder(new LineBorder(new Color(255, 0, 0), 5, true));
		mode = disconnected;
	}
	

}
