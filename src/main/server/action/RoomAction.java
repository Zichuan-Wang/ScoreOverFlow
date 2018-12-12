package server.action;

import java.util.List;

import dao.RoomDao;
import entity.Room;
import server.constraint.SearchRoomConstraint;

// Handle all actions related to Room
public class RoomAction {

	private RoomDao dao;

	public RoomAction(RoomDao dao) {
		this.dao = dao;
	}

	public List<Room> searchRooms(SearchRoomConstraint constraint) {
		return dao.searchRooms(constraint);
	}

	public List<Object[]> searchReservedRooms(SearchRoomConstraint constraint) {
		return dao.searchReservedRooms(constraint);
	}

	public Room getRoomById(int id) {
		return dao.getRoomById(id);
	}
	
	public List<Room> getAllRooms() {
		return dao.getAllRooms();
	}
}
