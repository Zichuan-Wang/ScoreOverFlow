package server.action;

import dao.ReservationDao;
import dao.factory.ReservationDaoFactory;
import entity.Reservation;
import exception.DBConnectionException;

public class CancelReservationAction {
	public static boolean cancelReservation(Reservation reservation) throws DBConnectionException {
		ReservationDao reservationDao = ReservationDaoFactory.getInstance();
		reservationDao.remove(reservation);
		
		return true; // for a single-user system
	}
}
