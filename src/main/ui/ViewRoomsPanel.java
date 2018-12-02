package ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import entity.Reservation;
import entity.Room;
import entity.User;
//import security.SecurityService;
import server.action.ReservationAction;
import server.action.RoomAction;
import server.constraint.SearchReservationConstraint;

public class ViewRoomsPanel extends BasePanel {
	/**
	 * Default serial version id
	 */
	private static final long serialVersionUID = 1L;
	private final static String TITLE = "My Reservations";
	private User user;

	private TablePanel reservationPane;

	private ReservationAction reservationAction;
	private RoomAction roomAction;

	public ViewRoomsPanel(JPanel cards, User user, ReservationAction reservationAction, RoomAction roomAction) {
		super(TITLE, cards);
		this.user = user;
		this.reservationAction = reservationAction;
		this.roomAction = roomAction;
		initPanels();
	}

	@Override
	public JPanel getMiddlePanel() {
		// middle Panel
		JPanel middlePane = new JPanel();

		middlePane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		reservationPane = new TablePanel();
		JScrollPane scrollReservationPane = new JScrollPane(reservationPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollReservationPane.setPreferredSize(new Dimension(600, 200));

		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		c.gridheight = 2;
		c.weightx = 1.0;
		c.weighty = 1.0;
		middlePane.add(scrollReservationPane, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.weightx = 0.0;
		c.weighty = 1.0;
		JButton backButton = GuiUtils.getBackButton(this, cards);
		middlePane.add(backButton, c);
		return middlePane;
	}

	@Override
	public void showPanel() {
		showReservationList();
	}

	@Override
	public void reset() {
		reservationPane.revalidate();
		reservationPane.repaint();
	}

	public void showReservationList() {
		SearchReservationConstraint src = new SearchReservationConstraint();
		src.setUserId(user.getId());
		List<Reservation> reservationList = reservationAction.searchReservations(src);
		List<Object[]> rows = new ArrayList<>();
		// Room name, date, start time, end time, cancel button
		Object[] rowName = new Object[] { "Room Name", "Date", "Start Time", "End Time", "Cancel" };
		for (Reservation reservation : reservationList) {
			Room room = roomAction.getRoomById(reservation.getRoomId());
			Object[] row = new Object[5];
			row[0] = room.getName();
			row[1] = reservation.getEventDate().toString();
			row[2] = reservation.getStartTime().toString();
			row[3] = reservation.getEndTime().toString();
			row[4] = getCancelButton(reservation);
			rows.add(row);
		}
		reservationPane.populateList(rowName, rows, "Cancel");
	}

	private JButton getCancelButton(Reservation reservation) {
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(e -> cancelReservation(reservation));
		return cancelButton;
	}

	private void cancelReservation(Reservation reservation) {
		if (user.getUserGroup() > 3) {
			// if (!SecurityService.currentUser.hasRole("Normal")) {
			if (alert)
				JOptionPane.showMessageDialog(null, "You do not have the right role to perform this action.");
			return;
		}
		boolean success = reservationAction.cancelReservation(reservation);
		if (success) {
			if (alert)
				JOptionPane.showMessageDialog(null, "Success!");
			showReservationList();
		} else {
			if (alert)
				JOptionPane.showMessageDialog(null, "There is something wrong with the reservation. Please Try Again.");
		}
		reset();
	}

}
