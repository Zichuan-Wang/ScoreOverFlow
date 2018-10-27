package main.dao;

import main.entity.Reservation;
import main.exception.DBConnectionException;

public class ReservationDao extends BaseDao<Reservation> {
	
	public ReservationDao() throws DBConnectionException {
		super(Reservation.class);
	}
}
