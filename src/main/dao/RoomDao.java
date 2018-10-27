package main.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import main.entity.Room;
import main.exception.DBConnectionException;

public class RoomDao extends BaseDao<Room> {
	
	public RoomDao() throws DBConnectionException {
		super(Room.class);
	}
	
	/*
	 * Find the room by name, return null if not found
	 */
	public Room findRoomByName (String name) {
		Query query = manager.createQuery("SELECT u FROM Room u WHERE u.name = :name").setParameter("name", name);
		try {
			return (Room) query.getSingleResult();
		} catch (NoResultException exception) {
			return null;
		}
	}
}
