package server.action;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dao.ReservationDao;
import dao.RoomDao;
import entity.Reservation;
import entity.Room;
import server.constraint.SearchReservationConstraint;
import server.constraint.SearchRoomConstraint;

// Handle all actions related to Reservation
public class ReservationAction {

	private ReservationDao reservationDao;
	private RoomDao roomDao;

	public ReservationAction(ReservationDao reservationDao, RoomDao roomDao) {
	//public ReservationAction(ReservationDao reservationDao) {
		this.reservationDao = reservationDao;
		this.roomDao = roomDao;
	}

	public boolean reserveRoom(Reservation reservation) {
		// TODO: check if the reservation is valid, e.g. the room is available
		reservation.setCreated(new Timestamp(System.currentTimeMillis()));
		reservation.setModified(new Timestamp(System.currentTimeMillis()));
		//Time systemTime = new Time(System.currentTimeMillis());
		//if(systemTime.before(reservation.getStartTime())) {
		    //return false;
		//}
		if (!isAvailable(reservation)) {
			return false;
		}
		
		reservationDao.saveOrUpdate(reservation);

		return true; // for a single-user system
	}

	public boolean overrideRoom(Reservation reservation, Date eventDate, Date startTime, Date endTime, int userID) {
		reservation.setOverriden(1);
		reservationDao.saveOrUpdate(reservation);
		
		Reservation newReservation = new Reservation();
		newReservation.setEventDate(eventDate);
		newReservation.setStartTime(startTime);
		newReservation.setEndTime(endTime);
		newReservation.setUserId(userID);
		newReservation.setModified(new Timestamp(System.currentTimeMillis()));
		newReservation.setCreated(new Timestamp(System.currentTimeMillis()));
		newReservation.setOverriden(0);
		newReservation.setRoomId(reservation.getRoomId());
		reservationDao.saveOrUpdate(newReservation);

		return true; // for a single-user system
	}

	public boolean cancelReservation(Reservation reservation) {
		reservationDao.remove(reservation);

		return true; // for a single-user system
	}

	public List<Reservation> searchReservations(SearchReservationConstraint constraint) {
		return reservationDao.searchReservations(constraint);
	}
	
	public List<Reservation> searchOverridenReservations(SearchReservationConstraint constraint) {
		return reservationDao.searchOverridenReservations(constraint);
	}

	public Reservation getReservation(SearchRoomConstraint constraint, int id) {
		return reservationDao.getReservation(constraint, id);
	}

	public Reservation getReservationById(int id) {
		return reservationDao.findById(id);
	}

	// returns all reservations that fail to process
	public List<Reservation> reserveMultipleRooms(List<Reservation> reservations) {
		List<Reservation> failedItems = new ArrayList<>();

		for (Reservation reservation : reservations) {
			boolean status = reserveRoom(reservation);
			if (!status) {
				failedItems.add(reservation);
			}
		}

		return failedItems;
	}

	private boolean isAvailable(Reservation reservation) { SearchRoomConstraint
		constraint = new SearchRoomConstraint()
		  .setStartTime(reservation.getStartTime())
		  .setEndTime(reservation.getEndTime())
		  .setEventDate(reservation.getEventDate());
		
		Room target = roomDao.getRoomById(reservation.getRoomId()); 
		for (Room room: roomDao.searchRooms(constraint)) { 
			 if (target.getId() == room.getId()) { 
				 return true;
		     } 
		}
		 
		return false; 
	}
}