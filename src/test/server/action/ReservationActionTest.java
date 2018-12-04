package server.action;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.Test;

import dao.ReservationDao;
import dao.factory.ReservationDaoFactory;
import entity.Reservation;
import exception.DBConnectionException;
import server.constraint.SearchReservationConstraint;
import utils.EntityTestUtils;

public class ReservationActionTest {

	@Test
	public void testReserveRoom() throws DBConnectionException {
		ReservationDao dao = ReservationDaoFactory.getInstance();
		ReservationAction action = new ReservationAction(dao);

		assertThrows(NullPointerException.class, () -> action.reserveRoom(null));

		action.reserveRoom(EntityTestUtils.getDefaultReservation());
		Reservation reservation = dao.findById(EntityTestUtils.DEFAULT_RESERVATION_ID);
		dao.remove(reservation);
		assertNotNull(reservation);
	}

	@Test
	public void testCancelReservation() throws DBConnectionException {
		ReservationDao dao = ReservationDaoFactory.getInstance();
		ReservationAction action = new ReservationAction(dao);

		assertThrows(IllegalArgumentException.class, () -> action.cancelReservation(null));
		assertAll(() -> action.cancelReservation(EntityTestUtils.getDefaultReservation()));

		Reservation reservation = dao.saveOrUpdate(EntityTestUtils.getDefaultReservation());
		action.cancelReservation(reservation);
		assertNull(dao.findById(EntityTestUtils.DEFAULT_RESERVATION_ID));
	}

	@Test
	public void testSearchReservations() throws DBConnectionException {
		ReservationDao dao = ReservationDaoFactory.getInstance();
		ReservationAction action = new ReservationAction(dao);
		SearchReservationConstraint constraint = new SearchReservationConstraint()
				.setUserId(EntityTestUtils.DEFAULT_USER_ID);

		// No reservation
		assertEquals(0, action.searchReservations(constraint).size());
		
		Calendar calendar = Calendar.getInstance();
		
		// End of yesterday
		calendar.add(Calendar.DATE, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		Reservation reservation1 = EntityTestUtils.getDefaultReservation().setEventDate(calendar.getTime()).setEndTime(calendar.getTime()).setId(1);
		
		// End of today
		calendar.add(Calendar.DATE, 1);
		Reservation reservation2 = EntityTestUtils.getDefaultReservation().setEventDate(calendar.getTime()).setEndTime(calendar.getTime()).setId(2);
		
		// Start of today
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Reservation reservation3 = EntityTestUtils.getDefaultReservation().setEventDate(calendar.getTime()).setEndTime(calendar.getTime()).setId(3);

		// Start of tomorrow
		calendar.add(Calendar.DATE, 1);
		Reservation reservation4 = EntityTestUtils.getDefaultReservation().setEventDate(calendar.getTime()).setEndTime(calendar.getTime()).setId(4);
		
		Reservation saved1 = dao.saveOrUpdate(reservation1);
		Reservation saved2 = dao.saveOrUpdate(reservation2);
		Reservation saved3 = dao.saveOrUpdate(reservation3);
		Reservation saved4 = dao.saveOrUpdate(reservation4);
		List<Reservation> reservations = action.searchReservations(constraint);
		dao.remove(saved1);
		dao.remove(saved2);
		dao.remove(saved3);
		dao.remove(saved4);
		
		assertEquals(2, reservations.size());
	}
}
