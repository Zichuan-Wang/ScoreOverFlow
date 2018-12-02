
package utils;

import exception.DBConnectionException;
import server.action.ReservationAction;
//import dao.factory.RoomDaoFactory;

public class ReservationActionTestUtils {

	public static ReservationAction getReservationAction() throws DBConnectionException {
		return new ReservationAction(ReservationDaoTestUtils.getReservationDao());
		//return new ReservationAction(ReservationDaoTestUtils.getReservationDao(), RoomDaoFactory.getInstance());
	}
}