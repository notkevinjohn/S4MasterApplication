package sandbox;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Color;

public class Card extends JPanel 
{
		private static final long serialVersionUID = 1L;
		public Card ()
		{
			setSize(150,150);
			setBackground(Color.darkGray);
			setLayout(new BorderLayout(0, 0));		
			
			JLabel lblPayload = new JLabel("Payload Name");
			add(lblPayload, BorderLayout.NORTH);
			
		
		}
}
