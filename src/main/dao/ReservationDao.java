package dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import entity.Reservation;
import server.constraint.SearchReservationConstraint;
import server.constraint.SearchRoomConstraint;

// DAO for Reservation
public class ReservationDao extends BaseDao<Reservation> {

	public ReservationDao(EntityManager manager) {
		super(Reservation.class, manager);
	}

	/**
	 * Search the reservations by the constraint criteria provided. The result
	 * 
	 * @param constraint
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Reservation> searchReservations(SearchReservationConstraint constraint) {
		Date date = new Date();
		Query query = manager.createQuery("SELECT r FROM Reservation r "//
				+ "WHERE r.userId = :id "//
				+ "AND (r.eventDate > :curDate "//
				+ "OR (r.eventDate = :curDate "
				+ "AND r.endTime >= :curTime))")//
				.setParameter("id", constraint.getUserId())//
				.setParameter("curDate", date, TemporalType.DATE)//
				.setParameter("curTime", date, TemporalType.TIME);
		return query.getResultList();
	}

	public Reservation getReservation(SearchRoomConstraint src, int id) {
		Query query = manager.createQuery("SELECT r FROM Reservation r "//
				+ "WHERE r.roomId = :id "//
				+ "AND r.eventDate = :eventDate "//
				+ "AND r.startTime = :endTime "//
				+ "AND r.endTime = :startTime").setParameter("id", id)
				.setParameter("eventDate", src.getEventDate(), TemporalType.DATE)
				.setParameter("startTime", src.getStartTime(), TemporalType.TIME)
				.setParameter("endTime", src.getEndTime(), TemporalType.TIME);
		try {
			return (Reservation) query.getSingleResult();
		} catch (NoResultException exception) {
			return null;
		}
	}
}