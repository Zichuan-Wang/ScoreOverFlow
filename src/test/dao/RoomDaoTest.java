package dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.Test;

import entity.Room;
import exception.DBConnectionException;
import server.constraint.SearchRoomConstraint;
import utils.EntityTestUtils;
import utils.RoomDaoTestUtils;

public class RoomDaoTest {
	@Test
	public void roomDatabaseTest() throws DBConnectionException {
		RoomDao dao = RoomDaoTestUtils.getRoomDao();
		Room room = dao.findById(EntityTestUtils.DEFAULT_ROOM_ID);
		dao.saveOrUpdate(room);
		dao.remove(room);
		System.out.println(dao);
		List<Room> rooms = dao.searchRooms(new SearchRoomConstraint());
		System.out.println(rooms);
		
		assertEquals(room.getId(), EntityTestUtils.DEFAULT_ROOM_ID);
		assertEquals(dao.findRoomByName(EntityTestUtils.DEFAULT_NAME).getName(), EntityTestUtils.DEFAULT_NAME);
		for (int i = 0; i < rooms.size(); i++) {
			assertEquals(rooms.get(i).getId(), RoomDaoTestUtils.ROOM_IDS[i]);
		}
		verify(dao.manager, times(1)).remove(room);
	}
}
