package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import entity.Room;
import server.constraint.SearchRoomConstraint;

// DAO for Room
public class RoomDao extends BaseDao<Room> {

	public RoomDao(EntityManager manager) {
		super(Room.class, manager);
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

	public Room getRoomById(int id) {
		Query query = manager.createQuery("SELECT u FROM Room u WHERE u.id = :id").setParameter("id", id);
		try {
			return (Room) query.getSingleResult();
		} catch (NoResultException exception) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Room> getAllRooms() {
		return (List<Room>) manager.createQuery("SELECT u FROM Room u").getResultList();
	}

	/**
	 * Search the rooms by the constraint criteria provided. The result contains
	 * rooms that 1. Contains the room name string in its name 2. Have larger
	 * capacity than the constraint 3. Does not have an existing reservation that
	 * overlaps with the requested interval 4. Have all the facilities in the
	 * requested set
	 * 
	 * @param constraint
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Room> searchRooms(SearchRoomConstraint constraint) {
		// add null to set to prevent empty set error
		constraint.getFacilities().add(null);
		Query query = manager.createQuery("SELECT u FROM Room u "//
				+ "WHERE (u.name LIKE :name) "//
				+ "AND (u.capacity >= :capacity) "//
				+ "AND NOT EXISTS "//
				+ "(SELECT r FROM Reservation r "//
				+ "WHERE r.roomId = u.id "//
				+ "AND r.eventDate = :eventDate "//
				+ "AND r.startTime < :endTime "//
				+ "AND r.endTime > :startTime) "//
				+ "AND :facilityCount = "//
				+ "(SELECT COUNT(f.id) FROM Room r "//
				+ "INNER JOIN r.facilities f "//
				+ "WHERE r.id = u.id "//
				+ "AND f in :facilities)")//
				.setParameter("name", "%" + constraint.getRoomName() + "%")
				.setParameter("capacity", constraint.getCapacity())
				.setParameter("eventDate", constraint.getEventDate(), TemporalType.DATE)
				.setParameter("startTime", constraint.getStartTime(), TemporalType.TIME)
				.setParameter("endTime", constraint.getEndTime(), TemporalType.TIME)
				.setParameter("facilities", constraint.getFacilities())
				.setParameter("facilityCount", (long) constraint.getFacilities().size() - 1);
		return query.getResultList();

	}

	@SuppressWarnings("unchecked")
	public List<Object[]> searchReservedRooms(SearchRoomConstraint constraint) {
		// add null to set to prevent empty set error
		constraint.getFacilities().add(null);
		Query query = manager
				.createQuery("SELECT room, res.id FROM Room room, Reservation res " + "WHERE room.name LIKE :name "
						+ "AND room.capacity >= :capacity " + "AND :facilityCount = "//
						+ "(SELECT COUNT(f.id) FROM Room r "//
						+ "INNER JOIN r.facilities f "//
						+ "WHERE r.id = room.id "//
						+ "AND f in :facilities) "//
						+ "AND res.roomId = room.id "//
						+ "AND res.overriden = 0" //
						+ "AND res.eventDate = :eventDate "//
						+ "AND res.startTime < :endTime "//
						+ "AND res.endTime > :startTime "//
						+ "AND EXISTS "//
						+ "(SELECT p FROM User p "//
						+ "WHERE p.id = res.userId "//
						+ "AND p.userGroup = 3)")//
				.setParameter("name", "%" + constraint.getRoomName() + "%")
				.setParameter("capacity", constraint.getCapacity())
				.setParameter("eventDate", constraint.getEventDate(), TemporalType.DATE)
				.setParameter("startTime", constraint.getStartTime(), TemporalType.TIME)
				.setParameter("endTime", constraint.getEndTime(), TemporalType.TIME)
				.setParameter("facilities", constraint.getFacilities())
				.setParameter("facilityCount", (long) constraint.getFacilities().size() - 1);
		return query.getResultList();
	}
}