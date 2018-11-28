package server.action;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import dao.RoomDao;
import dao.factory.RoomDaoFactory;
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
		assertEquals(results2.size(), 1);
		assertEquals(results3.size(), 0);
		assertEquals(results4.size(), 1);
	}	
}
