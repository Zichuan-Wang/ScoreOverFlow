package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import entity.Room;

public class RoomDao {
	
	private EntityManager manager;
	
	public RoomDao() {
		manager = DaoUtils.factory.createEntityManager();
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
	
	/*
	 * Save the room or update its fields, returns the managed entity
	 */
	public Room saveOrUpdateRoom(Room room) {
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
		Room saved = manager.merge(room);
		transaction.commit();
		return saved;
	}
	
	/*
	 * Remove the room
	 * 
	 * @param room managed room entity 
	 */
	public void removeUser(Room room) {
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
		manager.remove(room);
		transaction.commit();
	}
}
