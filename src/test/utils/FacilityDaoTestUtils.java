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

import dao.FacilityDao;
import entity.Facility;

public class FacilityDaoTestUtils {

	public static final int[] FACILITY_IDS = new int[] { 1, 2 };

	public static FacilityDao getFacilityDao() {

		EntityManager manager = mock(EntityManager.class);
		EntityTransaction transaction = mock(EntityTransaction.class);
		Query query = mock(Query.class);

		when(manager.getTransaction()).thenReturn(transaction);

		doAnswer(new Answer<Facility>() {
			public Facility answer(InvocationOnMock invocation) {
				int id = invocation.getArgument(1);
				return EntityTestUtils.getDefaultFacility().setId(id);

			}
		}).when(manager).find(eq(Facility.class), anyInt());

		List<Facility> facilities = new ArrayList<>();
		for (int id : FACILITY_IDS) {
			facilities.add(EntityTestUtils.getDefaultFacility().setId(id));
		}

		when(manager.createQuery(any(String.class))).thenReturn(query);
		when(query.setParameter(any(String.class), any(String.class))).thenReturn(query);
		when(query.getSingleResult()).thenReturn(EntityTestUtils.getDefaultFacility());
		when(query.getResultList()).thenReturn(facilities);

		doAnswer(new Answer<Facility>() {
			public Facility answer(InvocationOnMock invocation) {
				return invocation.getArgument(0);
			}
		}).when(manager).merge(any(Facility.class));

		return new FacilityDao(manager);
	}

}
