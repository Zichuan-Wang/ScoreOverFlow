package utils;

import server.action.ReservationAction;
import server.action.RoomAction;
import ui.MainWindow;

public class MainWindowTestUtils {

	public static MainWindow getMainWindow() {
		ReservationAction reservationAction = ReservationActionTestUtils.getReservationAction();
		RoomAction roomAction = RoomActionTestUtils.getRoomAction();
		return new MainWindow(reservationAction, roomAction);
	}

}
