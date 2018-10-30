package entity;

import org.junit.jupiter.api.Test;

import entity.Reservation;
import utils.DaoTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

//The JUnit tests for Reservation
public class ReservationTest {
	@Test
	public void createAndUpdateReservation() {
		Reservation reservation = DaoTestUtils.getDefaultReservation();
		
		assertEquals(reservation.getId(), DaoTestUtils.DEFAULT_RESERVATION_ID);
		assertEquals(reservation.getUserId(), DaoTestUtils.DEFAULT_USER_ID);
		assertEquals(reservation.getEventDate(), DaoTestUtils.DEFAULT_EVENT_DATE);
		assertEquals(reservation.getStartTime(), DaoTestUtils.DEFAULT_START_TIME);
		assertEquals(reservation.getEndTime(), DaoTestUtils.DEFAULT_END_TIME);
		assertEquals(reservation.getCreated(), DaoTestUtils.DEFAULT_CREATED);
		assertEquals(reservation.getModified(), DaoTestUtils.DEFAULT_MODIFIED);
	}
}
