package utils;

import entity.User;
import server.action.ReservationAction;
import server.action.RoomAction;
import ui.MainWindow;

public class MainWindowTestUtils {

	public static MainWindow getMainWindow() {
		User user = EntityTestUtils.getDefaultUser();
		ReservationAction reservationAction = ReservationActionTestUtils.getReservationAction();
		RoomAction roomAction = RoomActionTestUtils.getRoomAction();
		return new MainWindow(user, reservationAction, roomAction);
	}

}
