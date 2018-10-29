package UI;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MainPanel extends BasePanel {
	private final static String TITLE = "Main";
	private JButton reserveButton;
	private JButton viewRoomsButton;

	public MainPanel(JPanel cards, ReservePanel reservePane, ViewRoomsPanel viewRoomsPane) {
		super(TITLE);

		// middle Panel
		JPanel middlePane = new JPanel();

		middlePane.setLayout(new BoxLayout(middlePane, BoxLayout.Y_AXIS));

		middlePane.add(Box.createVerticalGlue());

		// Reserve
		reserveButton = GUIUtil.getJumpCardButton(cards, "Reserve a Room", "reserve");
		reserveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		middlePane.add(reserveButton);

		middlePane.add(Box.createVerticalGlue());

		// See Booked Rooms
		viewRoomsButton = GUIUtil.getJumpCardButton(cards, "View booked Rooms", "view rooms");
		viewRoomsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewRoomsPane.printList();
			}
		});
		viewRoomsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		middlePane.add(viewRoomsButton);

		middlePane.add(Box.createVerticalGlue());
		setMiddlePanel(middlePane);
	}
}
