package ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import dao.ReservationDao;
import dao.factory.ReservationDaoFactory;
import entity.EntityUtils;
import entity.Reservation;
import exception.DBConnectionException;
import server.action.CancelReservationAction;
import server.action.ReserveRoomAction;
import server.constraint.SearchReservationConstraint;

public class ViewRoomsPanel extends BasePanel {
	private final static String TITLE = "Booked Rooms";
	private ReservationDao myDao;
	private TablePanel reservationPane;

	public ViewRoomsPanel(JPanel cards) {
		super(TITLE);

		try {
			myDao = ReservationDaoFactory.getInstance();
		} catch (DBConnectionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

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
		JButton backButton = GuiUtils.getJumpCardButton(cards, "back", "main");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		middlePane.add(backButton, c);

		setMiddlePanel(middlePane);
	}

	public void reset() {
		reservationPane.reset();
	}

	public void printList() {
		SearchReservationConstraint src = new SearchReservationConstraint(); // @TODO need ID
		List<Reservation> reservationList = myDao.findReservationsByUserId(src);
		List<Object[]> rows = new ArrayList<>();
		for (Reservation reservation : reservationList) {
			Object[] row = new Object[2];
			row[0] = Integer.toString(reservation.getRoomId()); // @TODO need name
			JButton cancelButton = new JButton("Cancel");
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						boolean success = CancelReservationAction.cancelReservation(reservation);
					} catch (DBConnectionException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					// @TODO failure and success handling
				}
			});
			row[1] = cancelButton;
			rows.add(row);
		}
		reservationPane.populateList(rows);
	}

}
