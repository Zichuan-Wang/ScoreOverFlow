package ui;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MainPanel extends BasePanel {
	/**
	 * Default serial version id
	 */
	private static final long serialVersionUID = 1L;
	private final static String TITLE = "Main";
	private JButton reserveButton;
	private JButton viewRoomsButton;

	public MainPanel(JPanel cards, ReservePanel reservePane, ViewRoomsPanel viewRoomsPane) {
		super(TITLE, cards);
		reserveButton.addActionListener(e -> reservePane.showPanel());
		viewRoomsButton.addActionListener(e -> viewRoomsPane.showPanel());
	}

	@Override
	public JPanel getMiddlePanel() {
		// middle Panel
		JPanel middlePane = new JPanel();

		middlePane.setLayout(new BoxLayout(middlePane, BoxLayout.Y_AXIS));

		middlePane.add(Box.createVerticalGlue());

		// Reserve
		reserveButton = GuiUtils.createButton("Reserve a Room", GuiUtils.getJumpCardActionListener(cards, "reserve"));
		reserveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		middlePane.add(reserveButton);

		middlePane.add(Box.createVerticalGlue());

		// See Booked Rooms
		viewRoomsButton = GuiUtils.createButton("View Reservations",
				GuiUtils.getJumpCardActionListener(cards, "view rooms"));
		viewRoomsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		middlePane.add(viewRoomsButton);

		middlePane.add(Box.createVerticalGlue());
		return middlePane;
	}

}
