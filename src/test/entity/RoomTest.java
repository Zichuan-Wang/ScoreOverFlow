package entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import utils.EntityTestUtils;

//The JUnit tests for Room
public class RoomTest {
	@Test
	public void createAndUpdateRoom() {
		Room room = EntityTestUtils.getDefaultRoom();

		assertEquals(room.getId(), EntityTestUtils.DEFAULT_ROOM_ID);
		assertEquals(room.getName(), EntityTestUtils.DEFAULT_ROOM_NAME);
		assertEquals(room.getCapacity(), EntityTestUtils.DEFAULT_CAPACITY);
	}
}
