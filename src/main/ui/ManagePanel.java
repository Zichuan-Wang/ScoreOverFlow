package ui;

import javax.swing.JPanel;

import entity.User;
import server.action.FacilityAction;
import server.action.ReservationAction;
import server.action.RoomAction;
import server.action.UserAction;

public class ManagePanel extends BasePanel{
	private static final long serialVersionUID = 1L;
	private final static String TITLE = "Manage Rooms and Facilities";
	private User user;
	
	private UserAction userAction;
	private ReservationAction reservationAction;
	private RoomAction roomAction;
	private FacilityAction facilityAction;
	
	public ManagePanel(JPanel cards, User user, UserAction userAction, ReservationAction reservationAction,
			RoomAction roomAction, FacilityAction facilityAction) {
		super(cards,TITLE);
		this.user = user;
		this.userAction = userAction;
		this.reservationAction = reservationAction;
		this.roomAction = roomAction;
		this.facilityAction = facilityAction;
		setMiddlePanel();
	}

	private void setMiddlePanel() {
		// @TODO implement this
		if (user == null) {
			return;
		}
		if (userAction == null || reservationAction == null || roomAction == null || facilityAction == null)
			return;
	}

}
