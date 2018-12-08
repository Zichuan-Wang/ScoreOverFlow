
package utils;

import exception.DBConnectionException;
import server.action.ReservationAction;

public class ReservationActionTestUtils {

	public static ReservationAction getReservationAction() throws DBConnectionException {
		//ReservationAction reservationAction = mock(ReservationAction.class);
		
		return new ReservationAction(ReservationDaoTestUtils.getReservationDao());
	}
}