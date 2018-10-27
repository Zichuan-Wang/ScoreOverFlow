package java;

import org.junit.jupiter.api.Test;

import entity.Reservation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

//The JUnit tests for Reservation
public class ReservationTest {
	private static final int DEFAULT_ID = 1;
	private static final int DEFAULT_USER_ID = 27;
	private static final Date DEFAULT_EVENT_DATE = new Date(1234567);
	private static final Time DEFAULT_START_TIME = new Time(12345);
	private static final Time DEFAULT_END_TIME = new Time(54321);
	private static final Timestamp DEFAULT_CREATED = new Timestamp(123);
	private static final Timestamp DEFAULT_MODIFIED = new Timestamp(321);
	
	@Test
	public void createAndUpdateReservation() {
		Reservation reservation = new Reservation();
		reservation.setId(DEFAULT_ID)
		    .setUserId(DEFAULT_USER_ID)
		    .setEventDate(DEFAULT_EVENT_DATE)
		    .setStartTime(DEFAULT_START_TIME)
		    .setEndTime(DEFAULT_END_TIME)
		    .setCreated(DEFAULT_CREATED)
		    .setModified(DEFAULT_MODIFIED);
		
		assertEquals(reservation.getId(), DEFAULT_ID);
		assertEquals(reservation.getUserId(), DEFAULT_USER_ID);
		assertEquals(reservation.getEventDate(), DEFAULT_EVENT_DATE);
		assertEquals(reservation.getStartTime(), DEFAULT_START_TIME);
		assertEquals(reservation.getEndTime(), DEFAULT_END_TIME);
		assertEquals(reservation.getCreated(), DEFAULT_CREATED);
		assertEquals(reservation.getModified(), DEFAULT_MODIFIED);
	}
}
