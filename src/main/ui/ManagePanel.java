package ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import entity.Room;
import entity.User;
import server.action.FacilityAction;
import server.action.ReservationAction;
import server.action.RoomAction;
import server.action.UserAction;

public class ManagePanel extends BasePanel {
	private static final long serialVersionUID = 1L;
	private final static String TITLE = "Manage Rooms and Facilities";
	private User user;

	private UserAction userAction;
	private ReservationAction reservationAction;
	private RoomAction roomAction;
	private FacilityAction facilityAction;

	private TablePanel roomPane;

	public ManagePanel(JPanel cards, User user, UserAction userAction, ReservationAction reservationAction,
			RoomAction roomAction, FacilityAction facilityAction) {
		super(cards, TITLE);
		this.user = user;
		this.userAction = userAction;
		this.reservationAction = reservationAction;
		this.roomAction = roomAction;
		this.facilityAction = facilityAction;
		setMiddlePanel();
	}

	private void setMiddlePanel() {
		middlePane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		roomPane = new TablePanel();
		roomPane.setPreferredSize(new Dimension(600, 200));

		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		c.gridheight = 2;
		c.weightx = 1.0;
		c.weighty = 1.0;
		middlePane.add(roomPane, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.weightx = 0.0;
		c.weighty = 1.0;
		JButton backButton = GuiUtils.createButton("Back", e -> GuiUtils.jumpToPanel(rootPane, "main"));
		middlePane.add(backButton, c);
	}

	@Override
	public void pareparePanel() {
		showRoomList();
	}

	public void showRoomList() {
		List<Room> rooms = roomAction.getAllRooms();
		Object[] rowName = new Object[] { "Name", "Capacity", "Action" };

		List<Object[]> rows = new ArrayList<>();
		for (Room room : rooms) {
			Object[] row = new Object[3];
			row[0] = room.getName();
			row[1] = room.getCapacity();
			row[2] = getManageButton(room);
			rows.add(row);
		}
		roomPane.populateList(rowName, rows, "Action");
	}

	private JButton getManageButton(Room room) {
		JButton manageButton = new JButton("Manage");
		manageButton.addActionListener(e -> {
		});
		return manageButton;
	}

}
