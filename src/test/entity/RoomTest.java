package entity;

import org.junit.jupiter.api.Test;

import entity.Room;
import utils.DaoTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

//The JUnit tests for Room
public class RoomTest {
	@Test
	public void createAndUpdateRoom() {
		Room room = DaoTestUtils.getDefaultRoom();
		
		assertEquals(room.getId(), DaoTestUtils.DEFAULT_ROOM_ID);
		assertEquals(room.getName(), DaoTestUtils.DEFAULT_NAME);
		assertEquals(room.getCapacity(), DaoTestUtils.DEFAULT_CAPACITY);
	}
}
