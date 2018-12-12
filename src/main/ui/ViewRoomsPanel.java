package ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import entity.Reservation;
import entity.Room;
import entity.User;
import security.SecurityService;
import server.action.ReservationAction;
import server.action.RoomAction;
import server.constraint.SearchReservationConstraint;
import server.constraint.SearchRoomConstraint;

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
		super(cards, TITLE);
		this.user = user;
		this.reservationAction = reservationAction;
		this.roomAction = roomAction;
		setMiddlePanel();
	}

	private void setMiddlePanel() {
		middlePane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		reservationPane = new TablePanel();
		reservationPane.setPreferredSize(new Dimension(600, 200));

		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		c.gridheight = 2;
		c.weightx = 1.0;
		c.weighty = 1.0;
		middlePane.add(reservationPane, c);

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
		showReservationList();
	}

	public void showReservationList() {
		
		SearchReservationConstraint src = new SearchReservationConstraint();
		src.setUserId(user.getId());
		List<Reservation> overridenReservationList = reservationAction.searchOverridenReservations(src);
		List<Object[]> rows = new ArrayList<>();
		// Room name, date, start time, end time, cancel button
		Object[] rowName = new Object[] { "Room Name", "Date", "Start Time", "End Time", "Button" };
		for (Reservation reservation : overridenReservationList) {
			Room room = roomAction.getRoomById(reservation.getRoomId());
			Object[] row = new Object[5];
			row[0] = room.getName()+"(overriden)";
			row[1] = reservation.getEventDate().toString();
			row[2] = reservation.getStartTime().toString();
			row[3] = reservation.getEndTime().toString();
			row[4] = getReplacementButton(reservation);
			rows.add(row);
		}
		//reservationPane.populateList(rowName1, rows1, "Cancel");
		

		List<Reservation> reservationList = reservationAction.searchReservations(src);
		List<Object[]> rows1 = new ArrayList<>();
		// Room name, date, start time, end time, cancel button
		for (Reservation reservation : reservationList) {
			Room room = roomAction.getRoomById(reservation.getRoomId());
			Object[] row = new Object[5];
			row[0] = room.getName();
			row[1] = reservation.getEventDate().toString();
			row[2] = reservation.getStartTime().toString();
			row[3] = reservation.getEndTime().toString();
			row[4] = getCancelButton(reservation);
			rows1.add(row);
		}
		rows.addAll(rows1);
		reservationPane.populateList(rowName, rows, "Button");
	}

	private JButton getCancelButton(Reservation reservation) {
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(e -> cancelReservation(reservation));
		return cancelButton;
	}

	private void cancelReservation(Reservation reservation) {
		if (user.getUserGroup() > 3) {
		//if (!SecurityService.currentUser.hasRole("Normal")) {
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
		reservationPane.revalidate();
		reservationPane.repaint();
	}
	
	private JButton getReplacementButton(Reservation reservation) {
		JButton replacementButton = new JButton("Find replacement");
		replacementButton.addActionListener(e -> replaceReservation(reservation));
		return replacementButton;
	}

	private void replaceReservation(Reservation reservation) {
		// if (user.getUserGroup() > 3) {
		if (!SecurityService.currentUser.hasRole("Normal")) {
			if (alert)
				JOptionPane.showMessageDialog(null, "You do not have the right role to perform this action.");
			return;
		}
		
		SearchRoomConstraint src = new SearchRoomConstraint();
		src.setStartTime(reservation.getStartTime());
		src.setEndTime(reservation.getEndTime());
		Room room = roomAction.getRoomById(reservation.getRoomId());
		src.setFacilities(room.getFacilities());
		src.setCapacity(room.getCapacity());
		src.setEventDate(reservation.getEventDate());		
		
		GuiUtils.jumpToPanel(rootPane, "reserve");
		Component[] components = rootPane.getComponents();
		for (Component c : components) {
			if(c instanceof ReservePanel) {
				ReservePanel panel = (ReservePanel) c;
				panel.searchRoom(src);
				break;
			}
		}
		
		
		
		reservationAction.cancelReservation(reservation);
		//boolean success = reservationAction.cancelReservation(reservation);
		/*
		if (success) {
			if (alert)
				JOptionPane.showMessageDialog(null, "Success!");
			showReservationList();
		} else {
			if (alert)
				JOptionPane.showMessageDialog(null, "There is something wrong with the reservation. Please Try Again.");
		}
		*/
		reservationPane.revalidate();
		reservationPane.repaint();
	}

}
