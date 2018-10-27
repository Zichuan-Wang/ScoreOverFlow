package dao;

import entity.Reservation;
import exception.DBConnectionException;

public class ReservationDao extends BaseDao<Reservation> {
	
	public ReservationDao() throws DBConnectionException {
		super(Reservation.class);
	}
}
