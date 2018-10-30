package dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.Test;

import entity.Reservation;
import exception.DBConnectionException;
import server.constraint.SearchReservationConstraint;
import utils.EntityTestUtils;
import utils.ReservationDaoTestUtils;

public class ReservationDaoTest {
	@Test
	public void reservationDatabaseTest() throws DBConnectionException {
		ReservationDao dao = ReservationDaoTestUtils.getReservationDao();
		Reservation reservation = dao.findById(EntityTestUtils.DEFAULT_RESERVATION_ID);
		dao.saveOrUpdate(reservation);
		dao.remove(reservation);
		List<Reservation> reservations = dao.searchReservations(new SearchReservationConstraint());
		
		assertEquals(reservation.getId(), EntityTestUtils.DEFAULT_RESERVATION_ID);
		for (int i = 0; i < reservations.size(); i++) {
			assertEquals(reservations.get(i).getId(), ReservationDaoTestUtils.RESERVATION_IDS[i]);
		}
		verify(dao.manager, times(1)).remove(reservation);
	}
}
