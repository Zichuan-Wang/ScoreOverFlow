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
import server.action.ReservationAction;
import server.constraint.SearchReservationConstraint;

public class ViewRoomsPanel extends BasePanel {
	/**
	 * Default serial version id
	 */
	private static final long serialVersionUID = 1L;
	private final static String TITLE = "My Reservations";
	private TablePanel reservationPane;
	
	private ReservationAction reservationAction;

	public ViewRoomsPanel(JPanel cards, ReservationAction reservationAction) {
		super(TITLE,cards);
		this.reservationAction = reservationAction;
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
		JButton backButton = GuiUtils.getBackButton(this,cards);
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
		SearchReservationConstraint src = new SearchReservationConstraint(); // @TODO need ID
		List<Reservation> reservationList = reservationAction.searchReservations(src);
		List<Object[]> rows = new ArrayList<>();
		for (Reservation reservation : reservationList) {
			Object[] row = new Object[2];
			row[0] = Integer.toString(reservation.getRoomId()); // @TODO need name
			row[1] = getCancelButton(reservation);
			rows.add(row);
		}
		reservationPane.populateList(rows);	
	}
	private JButton getCancelButton(Reservation reservation) {
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(e -> {
			boolean success = reservationAction.cancelReservation(reservation);
			if (success) {
				JOptionPane.showMessageDialog(null, "Success!");
				showReservationList();
			} else {
				JOptionPane.showMessageDialog(null, "There is something wrong with the reservation. Please Try Again.");
			}
			reset();
		});
		return cancelButton;
	}
	

}
