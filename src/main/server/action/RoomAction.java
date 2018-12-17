package server.action;

import java.util.List;

import dao.ReservationDao;
import dao.RoomDao;
import dao.UserDao;
import email.EmailSender;
import entity.Reservation;
import entity.Room;
import entity.User;
import server.constraint.SearchRoomConstraint;

// Handle all actions related to Room
public class RoomAction {

	private RoomDao roomDao;
	private ReservationDao reservationDao;
	private UserDao userDao;

	public RoomAction(RoomDao roomDao, ReservationDao reservationDao, UserDao userDao) {
		this.roomDao = roomDao;
		this.reservationDao = reservationDao;
		this.userDao = userDao;
	}

	public List<Room> searchRooms(SearchRoomConstraint constraint) {
		return roomDao.searchRooms(constraint);
	}

	public List<Object[]> searchReservedRooms(SearchRoomConstraint constraint) {
		return roomDao.searchReservedRooms(constraint);
	}

	public Room findRoomByName(String name) {
		return roomDao.findRoomByName(name);
	}
	
	public Room getRoomById(int id) {
		return roomDao.getRoomById(id);
	}
	
	public List<Room> getAllRooms() {
		return roomDao.getAllRooms();
	}
	
	public void deleteRoom(Room room) {
		List<Reservation> reservations = reservationDao.getReservationsByRoomId(room.getId());
		for (Reservation reservation : reservations) {
			User user = userDao.findById(reservation.getUserId());
			try {
				EmailSender.sendDeleteRoomEmail(user, reservation, room);
			} catch (Exception e) {
				e.printStackTrace();
			}
			reservationDao.remove(reservation);
		}
		roomDao.remove(room);
	}
	
	public Room saveRoom(Room room) {
		return roomDao.saveOrUpdate(room);
	}
}
