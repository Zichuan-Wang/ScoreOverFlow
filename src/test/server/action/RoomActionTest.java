package server.action;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import dao.RoomDao;
import dao.factory.RoomDaoFactory;
import entity.Room;
import exception.DBConnectionException;

public class RoomActionTest {
	@Test
	public void findRoomById_roomExists() throws DBConnectionException {
		RoomDao dao = RoomDaoFactory.getInstance();
		RoomAction action = new RoomAction(dao);
		Room room = action.getRoomById(0);

		assertEquals(room.getId(), 0);
	}
	
	@Test
	public void findRoomById_roomNotExist() throws DBConnectionException {
		RoomDao dao = RoomDaoFactory.getInstance();
		RoomAction action = new RoomAction(dao);
		Room room = action.getRoomById(12345);

		assertNull(room);
	}
}
