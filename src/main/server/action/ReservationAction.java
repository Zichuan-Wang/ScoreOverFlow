package server.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
		// TODO: check if the reservation is valid, e.g. the room is available
		
		reservation.setCreated(new Timestamp(System.currentTimeMillis()));
		reservation.setModified(new Timestamp(System.currentTimeMillis()));
		dao.saveOrUpdate(reservation);

		return true; // for a single-user system
	}
	
	// NOT WORKING!
	public boolean overrideRoom(Reservation reservation, Date eventDate, Date startTime, Date endTime, int userID) {
		reservation.setEventDate(eventDate);
		reservation.setStartTime(startTime);
		reservation.setEndTime(endTime);
		reservation.setUserId(userID);
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
	
	// returns all reservations that fail to process
	public List<Reservation> reserveMultipleRooms (List<Reservation> reservations) {
		List<Reservation> failedItems = new ArrayList<>();
		
		for (Reservation reservation: reservations) {
			boolean status = reserveRoom(reservation);
			if (!status) {
				failedItems.add(reservation);
			}
		}
		
		return failedItems;
	}

}
