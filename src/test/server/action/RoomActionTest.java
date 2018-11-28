package server.action;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import dao.RoomDao;
import dao.factory.RoomDaoFactory;
import entity.Room;
import exception.DBConnectionException;
import utils.EntityTestUtils;

public class RoomActionTest {
	@Test
	public void findRoomById_roomExists() throws DBConnectionException {
		RoomDao dao = RoomDaoFactory.getInstance();
		RoomAction action = new RoomAction(dao);
		
		Room room = dao.saveOrUpdate(EntityTestUtils.getDefaultRoom());
		assertNotNull(action.getRoomById(EntityTestUtils.DEFAULT_ROOM_ID));
		assertEquals(EntityTestUtils.DEFAULT_UNI, action.getRoomById(EntityTestUtils.DEFAULT_ROOM_ID).getId());
		dao.remove(room);
	}
	
	@Test
	public void findRoomById_roomNotExist() throws DBConnectionException {
		RoomDao dao = RoomDaoFactory.getInstance();
		RoomAction action = new RoomAction(dao);
		
		assertNull(action.getRoomById(EntityTestUtils.DEFAULT_ROOM_ID));
	}
}
