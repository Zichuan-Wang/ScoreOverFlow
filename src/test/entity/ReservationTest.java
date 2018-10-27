package entity;

import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import dao.ReservationDao;
import entity.Reservation;
import exception.DBConnectionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

//The JUnit tests for Reservation
public class ReservationTest {
	private static final int DEFAULT_ID = 1;
	private static final int NEW_ID = 2; 
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
	
	@Test
	public void reservationDatabaseTest() throws DBConnectionException {
		Reservation reservation = new Reservation();
		reservation.setId(DEFAULT_ID)
		    .setUserId(DEFAULT_USER_ID)
		    .setEventDate(DEFAULT_EVENT_DATE)
		    .setStartTime(DEFAULT_START_TIME)
		    .setEndTime(DEFAULT_END_TIME)
		    .setCreated(DEFAULT_CREATED)
		    .setModified(DEFAULT_MODIFIED);
	
		EntityManager manager = mock(EntityManager.class);
		EntityTransaction transaction = mock(EntityTransaction.class);
		
		when(manager.getTransaction()).thenReturn(transaction);
		when(manager.find(Reservation.class, DEFAULT_ID)).thenReturn(reservation);
		
		doAnswer(new Answer<Reservation>() {
            public Reservation answer(InvocationOnMock invocation) {
            	Reservation reservation = invocation.getArgument(0);
            	reservation.setId(NEW_ID);
                return reservation;
            }
        }).when(manager).merge(any(Reservation.class));
		
		ReservationDao dao = new ReservationDao();
		dao.setEntityManager(manager);
		Reservation newReservation = dao.saveOrUpdate(reservation);
		dao.remove(newReservation);
		
		assertEquals(reservation, dao.findById(DEFAULT_ID));
		assertEquals(newReservation.getId(), NEW_ID);
		verify(manager, times(1)).remove(newReservation);
	}
}
