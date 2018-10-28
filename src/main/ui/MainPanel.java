package ui;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MainPanel extends JPanel{
	private JPanel cards;
	private JButton reserveButton;
	private JButton viewRoomsButton;
	
	public MainPanel(JPanel cards) {
		this.cards = cards;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	    
		add(Box.createVerticalGlue());
		
		// Reserve
		reserveButton = GUIUtil.getJumpCardButton(cards, "Reserve a Room", "reserve");
		reserveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(reserveButton);
		
		add(Box.createVerticalGlue());
		
		// See Booked Rooms
		viewRoomsButton = GUIUtil.getJumpCardButton(cards, "View booked Rooms","view rooms");
		viewRoomsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(viewRoomsButton);
		
		add(Box.createVerticalGlue());
	}
}
