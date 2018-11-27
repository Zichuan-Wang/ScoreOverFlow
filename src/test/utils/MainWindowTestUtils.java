package utils;

import server.action.FacilityAction;
import server.action.ReservationAction;
import server.action.RoomAction;
import server.action.UserAction;
import ui.MainWindow;

public class MainWindowTestUtils {

	public static MainWindow getMainWindow() {
		UserAction userAction = UserActionTestUtils.getUserAction();
		ReservationAction reservationAction = ReservationActionTestUtils.getReservationAction();
		RoomAction roomAction = RoomActionTestUtils.getRoomAction();
		FacilityAction facilityAction = FacilityActionTestUtils.getFacilityAction();
		return new MainWindow(userAction, reservationAction, roomAction, facilityAction);
	}

}
