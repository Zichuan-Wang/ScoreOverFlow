

import org.junit.jupiter.api.Test;

import server.SearchRoomConstraint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;

public class SearchRoomConstraintTest {
	private static final Date DEFAULT_EVENT_DATE = new Date(1234567);
	private static final Time DEFAULT_START_TIME = new Time(12345);
	private static final Time DEFAULT_END_TIME = new Time(54321);
	private static final String DEFAULT_ROOM_NAME = "***";
	private static final int DEFAULT_CAPACITY = 10;
	
	@Test
	public void createAndChangeSearchConstraint() {
		SearchRoomConstraint constraint = new SearchRoomConstraint();
		assertNotNull(constraint);
		
		constraint.setCapacity(DEFAULT_CAPACITY)
		.setStartTime(DEFAULT_START_TIME)
		.setEndTime(DEFAULT_END_TIME)
		.setRoomName(DEFAULT_ROOM_NAME)
		.setEventDate(DEFAULT_EVENT_DATE);
		
		assertEquals(constraint.getCapacity(), DEFAULT_CAPACITY);
		assertEquals(constraint.getStartTime(), DEFAULT_START_TIME);
		assertEquals(constraint.getEndTime(), DEFAULT_END_TIME);
		assertEquals(constraint.getRoomName(), DEFAULT_ROOM_NAME);
		assertEquals(constraint.getEventDate(), DEFAULT_EVENT_DATE);
	}
}
