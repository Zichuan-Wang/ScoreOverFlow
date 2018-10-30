package server.action;

import java.sql.Timestamp;
import java.util.List;

import dao.ReservationDao;
import entity.Reservation;
import server.constraint.SearchReservationConstraint;

// Handle all actions related to Reservation
public class ReservationAction {

	private ReservationDao dao;
	
	public ReservationAction(ReservationDao dao) {
		this.dao = dao;
	}
	
	public boolean reserveRoom(Reservation reservation) {
		reservation.setCreated(new Timestamp(System.currentTimeMillis()));
		reservation.setModified(new Timestamp(System.currentTimeMillis()));
		dao.saveOrUpdate(reservation);
		
		return true; // for a single-user system
	}
	
	public boolean cancelReservation(Reservation reservation) {
		dao.remove(reservation);
		
		return true; // for a single-user system
	}
	
	public List<Reservation> searchReservations(SearchReservationConstraint constraint) {
		return dao.searchReservations(constraint);
	}
}
