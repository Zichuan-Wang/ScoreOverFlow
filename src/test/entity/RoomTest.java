package entity;

import org.junit.jupiter.api.Test;

import entity.Room;
import testUtils.TestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

//The JUnit tests for Room
public class RoomTest {
	@Test
	public void createAndUpdateRoom() {
		Room room = TestUtils.getDefaultRoom();
		
		assertEquals(room.getId(), TestUtils.DEFAULT_ROOM_ID);
		assertEquals(room.getName(), TestUtils.DEFAULT_NAME);
		assertEquals(room.getCapacity(), TestUtils.DEFAULT_CAPACITY);
	}
}
