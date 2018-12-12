
package utils;

import exception.DBConnectionException;
import server.action.ReservationAction;

public class ReservationActionTestUtils {

	public static ReservationAction getReservationAction() throws DBConnectionException {
		return new ReservationAction(ReservationDaoTestUtils.getReservationDao(), RoomDaoTestUtils.getRoomDao());
	}
}