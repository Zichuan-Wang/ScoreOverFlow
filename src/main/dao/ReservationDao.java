package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entity.Reservation;

import server.constraint.SearchReservationConstraint;

public class ReservationDao extends BaseDao<Reservation> {
	
	public ReservationDao(EntityManager manager) {
		super(Reservation.class, manager);
	}
	
	@SuppressWarnings("unchecked")
	public List<Reservation> searchReservations(SearchReservationConstraint constraint) {
		Query query = manager.createQuery("SELECT u FROM Reservation u WHERE u.userId = :id").setParameter("id", constraint.getUserId());
		return query.getResultList();
		
	}
}
