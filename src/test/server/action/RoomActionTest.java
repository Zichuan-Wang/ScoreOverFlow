package server.action;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import dao.ReservationDao;
import dao.RoomDao;
import dao.factory.ReservationDaoFactory;
import dao.factory.RoomDaoFactory;
import entity.Reservation;
import entity.Room;
import exception.DBConnectionException;
import server.constraint.SearchRoomConstraint;
import utils.EntityTestUtils;

public class RoomActionTest {
	@Test
	public void findRoomById_roomExists() throws DBConnectionException {
		RoomDao dao = RoomDaoFactory.getInstance();
		RoomAction action = new RoomAction(dao);

		Room room = dao.saveOrUpdate(EntityTestUtils.getDefaultRoom());
		Room found = action.getRoomById(EntityTestUtils.DEFAULT_ROOM_ID);
		dao.remove(room);

		assertNotNull(found);
		assertEquals(EntityTestUtils.DEFAULT_ROOM_ID, found.getId());
	}

	@Test
	public void findRoomById_roomNotExist() throws DBConnectionException {
		RoomDao dao = RoomDaoFactory.getInstance();
		RoomAction action = new RoomAction(dao);

		assertNull(action.getRoomById(EntityTestUtils.DEFAULT_ROOM_ID));
	}

	@Test
	public void searchRooms_capacity() throws DBConnectionException {
		RoomDao dao = RoomDaoFactory.getInstance();
		RoomAction action = new RoomAction(dao);

		Room room = dao.saveOrUpdate(EntityTestUtils.getDefaultRoom());
		SearchRoomConstraint constraint = new SearchRoomConstraint();
		List<Room> results1 = action.searchRooms(constraint.setCapacity(EntityTestUtils.DEFAULT_CAPACITY)); // capacity = 15
		List<Room> results2 = action.searchRooms(constraint.setCapacity(EntityTestUtils.DEFAULT_CAPACITY + 1)); // capacity = 16
		List<Room> results3 = action.searchRooms(constraint.setCapacity(EntityTestUtils.DEFAULT_CAPACITY - 1)); // capacity = 14 
		List<Room> results4 = action.searchRooms(constraint.setCapacity(0)); // capacity = 0
		dao.remove(room);

		assertEquals(results1.size(), 1);
		assertEquals(results2.size(), 0);
		assertEquals(results3.size(), 1);
		assertEquals(results4.size(), 1);
	}
	
	@Test
	public void searchRooms_name() throws DBConnectionException {
		RoomDao dao = RoomDaoFactory.getInstance();
		RoomAction action = new RoomAction(dao);

		Room room = dao.saveOrUpdate(EntityTestUtils.getDefaultRoom());
		SearchRoomConstraint constraint = new SearchRoomConstraint();
		List<Room> results1 = action.searchRooms(constraint.setRoomName(EntityTestUtils.DEFAULT_ROOM_NAME));
		List<Room> results2 = action.searchRooms(constraint.setRoomName(EntityTestUtils.DEFAULT_ROOM_NAME.substring(0, EntityTestUtils.DEFAULT_ROOM_NAME.length() - 1)));
		List<Room> results3 = action.searchRooms(constraint.setRoomName(EntityTestUtils.DEFAULT_ROOM_NAME + "=")); // non-exist
		List<Room> results4 = action.searchRooms(constraint.setRoomName(""));
		dao.remove(room);

		assertEquals(results1.size(), 1);
		assertEquals(results1.get(0).getName(), EntityTestUtils.DEFAULT_ROOM_NAME);
		assertEquals(results2.size(), 1);
		assertEquals(results3.size(), 0);
		assertEquals(results4.size(), 1);
	}
	
	@Test
	public void searchRooms_time() throws DBConnectionException {
		RoomDao roomDao = RoomDaoFactory.getInstance();
		ReservationDao reservationDao = ReservationDaoFactory.getInstance();
		
		RoomAction action = new RoomAction(roomDao);

		Room room = roomDao.saveOrUpdate(EntityTestUtils.getDefaultRoom());
		Reservation reservation = reservationDao.saveOrUpdate(EntityTestUtils.getDefaultReservation());
		
		SearchRoomConstraint constraint = new SearchRoomConstraint();
		
		// reserve right after the slot (boundary)
		List<Room> results1 = action.searchRooms(constraint
				.setEventDate(EntityTestUtils.DEFAULT_EVENT_DATE)
				.setStartTime(EntityTestUtils.DEFAULT_END_TIME)
				.setEndTime(EntityTestUtils.DEFAULT_END_TIME_NEXT));
		
		// reserve the exact time slot
		List<Room> results2 = action.searchRooms(constraint
				.setEventDate(EntityTestUtils.DEFAULT_EVENT_DATE)
				.setStartTime(EntityTestUtils.DEFAULT_START_TIME)
				.setEndTime(EntityTestUtils.DEFAULT_END_TIME));
		
		// reserve right before the slot (boundary)
		List<Room> results3 = action.searchRooms(constraint
				.setEventDate(EntityTestUtils.DEFAULT_EVENT_DATE)
				.setStartTime(EntityTestUtils.DEFAULT_START_TIME_PREV)
				.setEndTime(EntityTestUtils.DEFAULT_START_TIME));
		
		// reserve with time overlap
		List<Room> results4 = action.searchRooms(constraint
				.setEventDate(EntityTestUtils.DEFAULT_EVENT_DATE)
				.setStartTime(EntityTestUtils.DEFAULT_START_TIME_PREV)
				.setEndTime(EntityTestUtils.DEFAULT_START_TIME_NEXT));
		
		roomDao.remove(room);
		reservationDao.remove(reservation);

		assertEquals(results1.size(), 1);
		assertEquals(results2.size(), 0);
		assertEquals(results3.size(), 1);
		assertEquals(results4.size(), 0);
	}
	
	@Test
	public void searchRooms_combined() throws DBConnectionException {
		RoomDao roomDao = RoomDaoFactory.getInstance();
		ReservationDao reservationDao = ReservationDaoFactory.getInstance();
		
		RoomAction action = new RoomAction(roomDao);

		Room room = roomDao.saveOrUpdate(EntityTestUtils.getDefaultRoom());
		Reservation reservation = reservationDao.saveOrUpdate(EntityTestUtils.getDefaultReservation());
		
		SearchRoomConstraint constraint = new SearchRoomConstraint();
		
		List<Room> results1 = action.searchRooms(constraint
				.setEventDate(EntityTestUtils.DEFAULT_EVENT_DATE)
				.setStartTime(EntityTestUtils.DEFAULT_END_TIME)
				.setEndTime(EntityTestUtils.DEFAULT_END_TIME_NEXT)
				.setRoomName(EntityTestUtils.DEFAULT_ROOM_NAME));
		
		List<Room> results2 = action.searchRooms(constraint
				.setEventDate(EntityTestUtils.DEFAULT_EVENT_DATE)
				.setStartTime(EntityTestUtils.DEFAULT_START_TIME)
				.setRoomName(EntityTestUtils.DEFAULT_ROOM_NAME));
		
		List<Room> results3 = action.searchRooms(constraint
				.setEventDate(EntityTestUtils.DEFAULT_EVENT_DATE)
				.setStartTime(EntityTestUtils.DEFAULT_START_TIME_PREV)
				.setEndTime(EntityTestUtils.DEFAULT_START_TIME)
				.setRoomName(""));
		
		List<Room> results4 = action.searchRooms(constraint
				.setEventDate(EntityTestUtils.DEFAULT_EVENT_DATE)
				.setStartTime(EntityTestUtils.DEFAULT_START_TIME_PREV)
				.setEndTime(EntityTestUtils.DEFAULT_START_TIME_NEXT)
				.setCapacity(EntityTestUtils.DEFAULT_CAPACITY - 1));
		
		roomDao.remove(room);
		reservationDao.remove(reservation);

		assertEquals(results1.size(), 1);
		assertEquals(results2.size(), 0);
		assertEquals(results3.size(), 1);
		assertEquals(results4.size(), 0);
		
	}
}
