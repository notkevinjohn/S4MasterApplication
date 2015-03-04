/*package sandbox{

import javax.swing.JFrame;

import java.awt.FlowLayout;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;;
import javax.swing.JPanel;

public class CardView extends JFrame {

	private static final long serialVersionUID = 1L;
	public JPanel panel = new JPanel(new FlowLayout());
	public CardView ()
	{
		setTitle("card view");
		setBounds(100,100,800,600);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JButton btnNewPayload = new JButton("New Payload");
		btnNewPayload.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				panel.add(new Card());			
			}
		});
		getContentPane().add(btnNewPayload, BorderLayout.SOUTH);
		
		JLabel lblPayloads = new JLabel("Payloads");
		getContentPane().add(lblPayloads, BorderLayout.NORTH);		
		
		getContentPane().add(panel, BorderLayout.CENTER);			
		
	}

}*/
