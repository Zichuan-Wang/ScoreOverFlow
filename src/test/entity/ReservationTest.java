package entity;

import org.junit.jupiter.api.Test;

import entity.Reservation;
import utils.TestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

//The JUnit tests for Reservation
public class ReservationTest {
	@Test
	public void createAndUpdateReservation() {
		Reservation reservation = TestUtils.getDefaultReservation();
		
		assertEquals(reservation.getId(), TestUtils.DEFAULT_RESERVATION_ID);
		assertEquals(reservation.getUserId(), TestUtils.DEFAULT_USER_ID);
		assertEquals(reservation.getEventDate(), TestUtils.DEFAULT_EVENT_DATE);
		assertEquals(reservation.getStartTime(), TestUtils.DEFAULT_START_TIME);
		assertEquals(reservation.getEndTime(), TestUtils.DEFAULT_END_TIME);
		assertEquals(reservation.getCreated(), TestUtils.DEFAULT_CREATED);
		assertEquals(reservation.getModified(), TestUtils.DEFAULT_MODIFIED);
	}
}
