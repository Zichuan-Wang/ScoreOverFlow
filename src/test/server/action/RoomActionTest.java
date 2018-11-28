package server.action;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.util.List;
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
		dao.remove(room);
		
		assertNotNull(action.getRoomById(EntityTestUtils.DEFAULT_ROOM_ID));
		assertEquals(EntityTestUtils.DEFAULT_UNI, action.getRoomById(EntityTestUtils.DEFAULT_ROOM_ID).getId());
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
		dao.remove(room);
		
		SearchRoomConstraint constraint = new SearchRoomConstraint()
				.setCapacity(EntityTestUtils.DEFAULT_CAPACITY);
		assertEquals(constraint.getCapacity(), EntityTestUtils.DEFAULT_CAPACITY);
		
		List<Room> results = action.searchRooms(constraint);
		assertNotNull(results);
		assertEquals(results.size(), 1);
		assertNotNull(results.get(0));
		assertTrue(results.get(0).getCapacity() >= EntityTestUtils.DEFAULT_CAPACITY);
	}
}
