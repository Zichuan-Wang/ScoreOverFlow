package server.action;

import dao.ReservationDao;
import entity.Reservation;
import exception.DBConnectionException;

public class CancelReservationAction {
	public static boolean cancelReservation(Reservation reservation) throws DBConnectionException {
		ReservationDao reservationDao = new ReservationDao();
		reservationDao.remove(reservation);
		
		return true; // for a single-user system
	}
}
