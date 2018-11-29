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

	private static User user = EntityTestUtils.getDefaultUser();
	private static JPanel cards = new JPanel(new CardLayout());
	private static ReservationAction reservationAction;
	private static RoomAction roomAction = RoomActionTestUtils.getRoomAction();
	private static FacilityAction facilityAction = FacilityActionTestUtils.getFacilityAction();

	public static ReservePanel getReservePanel() throws DBConnectionException {
		reservationAction = ReservationActionTestUtils.getReservationAction();
		return new ReservePanel(cards, user, reservationAction, roomAction, facilityAction);
	}
	
	public static ReservePanel getHighUserReservePanel() throws DBConnectionException {
		User highUser  = EntityTestUtils.getHighUser();
		reservationAction = ReservationActionTestUtils.getReservationAction();
		return new ReservePanel(cards, highUser, reservationAction, roomAction, facilityAction);
	}

	public static ViewRoomsPanel getViewRoomsPanel() throws DBConnectionException {
		reservationAction = ReservationActionTestUtils.getReservationAction();
		return new ViewRoomsPanel(cards, user, reservationAction, roomAction);
	}
}
