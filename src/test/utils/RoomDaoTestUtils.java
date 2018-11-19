package utils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import dao.RoomDao;
import entity.Room;

public class RoomDaoTestUtils {

	public static final int[] ROOM_IDS = new int[] { 1, 2 };

	public static RoomDao getRoomDao() {

		EntityManager manager = mock(EntityManager.class);
		EntityTransaction transaction = mock(EntityTransaction.class);
		Query query = mock(Query.class);

		when(manager.getTransaction()).thenReturn(transaction);

		doAnswer(new Answer<Room>() {
			public Room answer(InvocationOnMock invocation) {
				int id = invocation.getArgument(1);
				return EntityTestUtils.getDefaultRoom().setId(id);

			}
		}).when(manager).find(eq(Room.class), anyInt());

		List<Room> rooms = new ArrayList<>();
		for (int id : ROOM_IDS) {
			rooms.add(EntityTestUtils.getDefaultRoom().setId(id));
		}

		when(manager.createQuery(any(String.class))).thenReturn(query);
		when(query.setParameter(any(String.class), any(String.class))).thenReturn(query);
		when(query.setParameter(any(String.class), anyInt())).thenReturn(query);
		when(query.setParameter(any(String.class), any(Date.class), any(TemporalType.class))).thenReturn(query);
		when(query.setParameter(any(String.class), any(Set.class))).thenReturn(query);
		when(query.setParameter(any(String.class), any(long.class))).thenReturn(query);
		when(query.getSingleResult()).thenReturn(EntityTestUtils.getDefaultRoom());
		when(query.getResultList()).thenReturn(rooms);

		doAnswer(new Answer<Room>() {
			public Room answer(InvocationOnMock invocation) {
				return invocation.getArgument(0);
			}
		}).when(manager).merge(any(Room.class));

		return new RoomDao(manager);
	}

}
