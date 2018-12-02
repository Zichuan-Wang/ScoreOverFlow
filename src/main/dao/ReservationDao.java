package dao;

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
		Query query = manager.createQuery("SELECT u FROM Reservation u WHERE u.userId = :id").setParameter("id",
				constraint.getUserId());
		return query.getResultList();
	}

	public Reservation getReservation(SearchRoomConstraint src, int id) {
		Query query = manager
				.createQuery("SELECT r FROM Reservation r " + "WHERE r.roomId = :id " + "AND r.eventDate = :eventDate "//
						+ "AND r.startTime = :endTime "//
						+ "AND r.endTime = :startTime")
				.setParameter("id", id).setParameter("eventDate", src.getEventDate(), TemporalType.DATE)
				.setParameter("startTime", src.getStartTime(), TemporalType.TIME)
				.setParameter("endTime", src.getEndTime(), TemporalType.TIME);
		try {
			return (Reservation) query.getSingleResult();
		} catch (NoResultException exception) {
			return null;
		}
	}

	public Reservation getReservationById(int id) {
		Query query = manager.createQuery("SELECT r FROM Reservation r WHERE r.id = :id").setParameter("id", id);
		try {
			return (Reservation) query.getSingleResult();
		} catch (NoResultException exception) {
			return null;
		}
	}
}