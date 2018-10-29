package server.action;

import java.sql.Timestamp;

import dao.ReservationDao;
import dao.factory.ReservationDaoFactory;
import entity.Reservation;
import exception.DBConnectionException;

public class ReserveRoomAction {
	public static boolean reserveRoom(Reservation reservation) throws DBConnectionException {
		ReservationDao reservationDao = ReservationDaoFactory.getInstance();
		reservation.setCreated(new Timestamp(System.currentTimeMillis()));
		reservation.setModified(new Timestamp(System.currentTimeMillis()));
		reservationDao.saveOrUpdate(reservation);
		
		return true; // for a single-user system
	}
}
