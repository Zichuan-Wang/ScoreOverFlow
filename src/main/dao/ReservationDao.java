package dao;

import java.util.List;

import javax.persistence.Query;

import entity.Reservation;

import exception.DBConnectionException;
import server.SearchReservationConstraint;

public class ReservationDao extends BaseDao<Reservation> {
	
	public ReservationDao() throws DBConnectionException {
		super(Reservation.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Reservation> findReservationsByUserId(SearchReservationConstraint constraint) {
		Query query = manager.createQuery("SELECT u FROM Reservation u WHERE u.userId = :id").setParameter("id", constraint.getUserId());
		return query.getResultList();
		
	}
}
