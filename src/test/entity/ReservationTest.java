package entity;

import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import dao.ReservationDao;
import entity.Reservation;
import exception.DBConnectionException;
import utils.TestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

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
	
	@Test
	public void reservationDatabaseTest() throws DBConnectionException {
		Reservation reservation = TestUtils.getDefaultReservation();
	
		EntityManager manager = mock(EntityManager.class);
		EntityTransaction transaction = mock(EntityTransaction.class);
		
		when(manager.getTransaction()).thenReturn(transaction);
		when(manager.find(Reservation.class, TestUtils.DEFAULT_RESERVATION_ID)).thenReturn(reservation);
		
		doAnswer(new Answer<Reservation>() {
            public Reservation answer(InvocationOnMock invocation) {
            	Reservation reservation = invocation.getArgument(0);
            	reservation.setId(TestUtils.NEW_RESERVATION_ID);
                return reservation;
            }
        }).when(manager).merge(any(Reservation.class));
		
		ReservationDao dao = new ReservationDao();
		dao.setEntityManager(manager);
		Reservation newReservation = dao.saveOrUpdate(reservation);
		dao.remove(newReservation);
		
		assertEquals(reservation, dao.findById(TestUtils.DEFAULT_RESERVATION_ID));
		assertEquals(newReservation.getId(), TestUtils.NEW_RESERVATION_ID);
		verify(manager, times(1)).remove(newReservation);
	}
}
