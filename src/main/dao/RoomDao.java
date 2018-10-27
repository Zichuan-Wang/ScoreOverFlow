package dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import entity.Room;
import exception.DBConnectionException;
import server.constraint.SearchRoomConstraint;

public class RoomDao extends BaseDao<Room> {
	
	public RoomDao() throws DBConnectionException {
		super(Room.class);
	}
	
	/*
	 * Find the room by name, return null if not found
	 */
	public Room findRoomByName(String name) {
		Query query = manager.createQuery("SELECT u FROM Room u WHERE u.name = :name").setParameter("name", name);
		try {
			return (Room) query.getSingleResult();
		} catch (NoResultException exception) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Room> searchRooms(SearchRoomConstraint constraint) {
		Query query = manager.createQuery(
			"SELECT u FROM Room u " +
			"INNER JOIN Reservation r " +
			"ON r.roomId = u.id " +
			"WHERE u.name = :name " +
			"AND u.capacity >= :capacity" +
			"AND (r.eventDate != :eventDate" + 
			"OR r.startTime > :endTime" +
			"OR r.endTime < :startTime)")
			.setParameter("name", constraint.getRoomName())
			.setParameter("capacity", constraint.getCapacity())
			.setParameter("eventDate", constraint.getEventDate())
			.setParameter("startTime", constraint.getStartTime())
			.setParameter("endTime", constraint.getEndTime());
		return query.getResultList();
				
	}
}
