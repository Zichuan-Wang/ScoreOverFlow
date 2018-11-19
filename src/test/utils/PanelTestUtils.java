package utils;

import java.awt.CardLayout;

import javax.swing.JPanel;

import entity.User;
import server.action.FacilityAction;
import server.action.ReservationAction;
import server.action.RoomAction;
import ui.ReservePanel;
import ui.ViewRoomsPanel;

public class PanelTestUtils {

	private static User user = EntityTestUtils.getDefaultUser();
	private static JPanel cards = new JPanel(new CardLayout());
	private static ReservationAction reservationAction = ReservationActionTestUtils.getReservationAction();
	private static RoomAction roomAction = RoomActionTestUtils.getRoomAction();
	private static FacilityAction facilityAction = FacilityActionTestUtils.getFacilityAction();

	public static ReservePanel getReservePanel() {
		return new ReservePanel(cards, user, reservationAction, roomAction, facilityAction);
	}

	public static ViewRoomsPanel getViewRoomsPanel() {
		return new ViewRoomsPanel(cards, user, reservationAction, roomAction);
	}
}
