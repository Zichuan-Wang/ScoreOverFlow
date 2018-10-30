package utils;

import server.action.ReservationAction;

public class ReservationActionTestUtils {

	public static ReservationAction getReservationAction() {
		return new ReservationAction(ReservationDaoTestUtils.getReservationDao());
	}
	
}
