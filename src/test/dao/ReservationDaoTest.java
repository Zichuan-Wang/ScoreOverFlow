package dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import dao.factory.ReservationDaoFactory;
import entity.Reservation;
import exception.DBConnectionException;
import utils.DaoTestUtils;

public class ReservationDaoTest {
	@Test
	public void reservationDatabaseTest() throws DBConnectionException {
		Reservation reservation = DaoTestUtils.getDefaultReservation();
	
		EntityManager manager = mock(EntityManager.class);
		EntityTransaction transaction = mock(EntityTransaction.class);
		
		when(manager.getTransaction()).thenReturn(transaction);
		when(manager.find(Reservation.class, DaoTestUtils.DEFAULT_RESERVATION_ID)).thenReturn(reservation);
		
		doAnswer(new Answer<Reservation>() {
            public Reservation answer(InvocationOnMock invocation) {
            	Reservation reservation = invocation.getArgument(0);
            	reservation.setId(DaoTestUtils.NEW_RESERVATION_ID);
                return reservation;
            }
        }).when(manager).merge(any(Reservation.class));
		
		ReservationDao dao = ReservationDaoFactory.getInstance();
		dao.setEntityManager(manager);
		Reservation newReservation = dao.saveOrUpdate(reservation);
		dao.remove(newReservation);
		
		assertEquals(reservation, dao.findById(DaoTestUtils.DEFAULT_RESERVATION_ID));
		assertEquals(newReservation.getId(), DaoTestUtils.NEW_RESERVATION_ID);
		verify(manager, times(1)).remove(newReservation);
	}
}
