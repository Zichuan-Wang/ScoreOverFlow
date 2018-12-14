package utils;

import java.awt.CardLayout;

import javax.swing.JPanel;

import entity.User;
import exception.DBConnectionException;
import server.action.FacilityAction;
import server.action.ReservationAction;
import server.action.RoomAction;
import ui.ReservePanel;
import ui.ViewRoomsPanel;

public class PanelTestUtils {

	private static JPanel cards = new JPanel(new CardLayout());
	private static ReservationAction reservationAction;
	private static RoomAction roomAction = RoomActionTestUtils.getRoomAction();
	private static FacilityAction facilityAction = FacilityActionTestUtils.getFacilityAction();

	public static ReservePanel getReservePanel() throws DBConnectionException {
		User user = EntityTestUtils.getDefaultUser();
		reservationAction = ReservationActionTestUtils.getReservationAction();
		return new ReservePanel(cards, user, null, reservationAction, roomAction, facilityAction);
	}

	public static ViewRoomsPanel getViewRoomsPanel() throws DBConnectionException {
		User user = EntityTestUtils.getDefaultUser();
		reservationAction = ReservationActionTestUtils.getReservationAction();
		return new ViewRoomsPanel(cards, user, reservationAction, roomAction);
	}
	
	public static ViewRoomsPanel getHighUserViewRoomsPanel() throws DBConnectionException {
		User user = EntityTestUtils.getDefaultUser();
		user.setUserGroup(EntityTestUtils.HIGH_USER_GROUP);
		reservationAction = ReservationActionTestUtils.getReservationAction();
		return new ViewRoomsPanel(cards, user, reservationAction, roomAction);
	}
	
}
