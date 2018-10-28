package ui;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MainPanel extends JPanel{
	private JPanel contentPane;
	private JButton reserveButton;
	private JButton viewRoomsButton;
	
	public MainPanel(JPanel contentPane) {
		this.contentPane = contentPane;
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		reserveButton = new JButton("Reserve a Room");
		reserveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(reserveButton);
		
		add(Box.createRigidArea(new Dimension(0,10)));
		
		// show rooms button
		viewRoomsButton = GUIUtil.getJumpCardButton(contentPane, "View booked Rooms","view rooms");
		viewRoomsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(viewRoomsButton);
	}
	
	public void createJump(Map<String, JPanel> map) {
		reserveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				((ReservePanel) map.get("reserve")).reset();
				CardLayout cl = (CardLayout) contentPane.getLayout();
				cl.show(contentPane, "reserve");
			}
		});
		
		viewRoomsButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				((ViewRoomsPanel) map.get("view rooms")).printList();
				CardLayout cl = (CardLayout) contentPane.getLayout();
				cl.show(contentPane, "view rooms");
			}
		});
	}
}
