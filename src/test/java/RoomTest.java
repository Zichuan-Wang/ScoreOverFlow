package java;

import org.junit.jupiter.api.Test;

import entity.Room;

import static org.junit.jupiter.api.Assertions.assertEquals;

//The JUnit tests for Room
public class RoomTest {
	private static final int DEFAULT_ID = 1;
	private static final String DEFAULT_NAME = "Mudd 282";
	private static final int DEFAULT_CAPACITY = 15;
	
	@Test
	public void createAndUpdateRoom() {
		Room room = new Room();
		room.setId(DEFAULT_ID).setName(DEFAULT_NAME).setCapacity(DEFAULT_CAPACITY);
		
		assertEquals(room.getId(), DEFAULT_ID);
		assertEquals(room.getName(), DEFAULT_NAME);
		assertEquals(room.getCapacity(), DEFAULT_CAPACITY);
	}
}
