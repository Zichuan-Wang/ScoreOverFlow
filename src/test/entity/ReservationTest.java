package entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import utils.EntityTestUtils;

//The JUnit tests for Reservation
public class ReservationTest {
	@Test
	public void createAndUpdateReservation() {
		Reservation reservation = EntityTestUtils.getDefaultReservation();

		assertEquals(reservation.getId(), EntityTestUtils.DEFAULT_RESERVATION_ID);
		assertEquals(reservation.getUserId(), EntityTestUtils.DEFAULT_USER_ID);
		assertEquals(reservation.getEventDate(), EntityTestUtils.DEFAULT_EVENT_DATE);
		assertEquals(reservation.getStartTime(), EntityTestUtils.DEFAULT_START_TIME);
		assertEquals(reservation.getEndTime(), EntityTestUtils.DEFAULT_END_TIME);
		assertEquals(reservation.getCreated(), EntityTestUtils.DEFAULT_CREATED);
		assertEquals(reservation.getModified(), EntityTestUtils.DEFAULT_MODIFIED);
	}
}
