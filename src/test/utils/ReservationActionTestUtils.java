package utils;

import dao.factory.RoomDaoFactory;
import exception.DBConnectionException;
import server.action.ReservationAction;

public class ReservationActionTestUtils {

	public static ReservationAction getReservationAction() throws DBConnectionException {
		return new ReservationAction(ReservationDaoTestUtils.getReservationDao(), RoomDaoFactory.getInstance());
	}
}
