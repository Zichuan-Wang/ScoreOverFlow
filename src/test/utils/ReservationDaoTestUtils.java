package utils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import dao.ReservationDao;
import entity.Reservation;

public class ReservationDaoTestUtils {

	public static final int[] RESERVATION_IDS = new int[] { 1, 2 };

	public static ReservationDao getReservationDao() {

		EntityManager manager = mock(EntityManager.class);
		EntityTransaction transaction = mock(EntityTransaction.class);
		Query query = mock(Query.class);

		when(manager.getTransaction()).thenReturn(transaction);

		doAnswer(new Answer<Reservation>() {
			public Reservation answer(InvocationOnMock invocation) {
				int id = invocation.getArgument(1);
				return EntityTestUtils.getDefaultReservation().setId(id);

			}
		}).when(manager).find(eq(Reservation.class), anyInt());

		List<Reservation> reservations = new ArrayList<>();
		for (int id : RESERVATION_IDS) {
			reservations.add(EntityTestUtils.getDefaultReservation().setId(id));
		}

		when(manager.createQuery(any(String.class))).thenReturn(query);
		when(query.setParameter(any(String.class), any())).thenReturn(query);
		when(query.getResultList()).thenReturn(reservations);

		doAnswer(new Answer<Reservation>() {
			public Reservation answer(InvocationOnMock invocation) {
				return invocation.getArgument(0);
			}
		}).when(manager).merge(any(Reservation.class));

		return new ReservationDao(manager);
	}

}
