package server.action;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

		// Exactly one reservation
		Reservation saved = dao.saveOrUpdate(EntityTestUtils.getDefaultReservation());
		List<Reservation> reservations = action.searchReservations(constraint);
		dao.remove(saved);
		assertEquals(1, reservations.size());
	}
}
