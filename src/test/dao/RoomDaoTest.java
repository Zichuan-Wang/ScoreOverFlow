package dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import dao.factory.RoomDaoFactory;
import entity.Room;
import exception.DBConnectionException;
import utils.DaoTestUtils;

public class RoomDaoTest {
	@Test
	public void roomDatabaseTest() throws DBConnectionException {
		Room room = DaoTestUtils.getDefaultRoom();
	
		EntityManager manager = mock(EntityManager.class);
		EntityTransaction transaction = mock(EntityTransaction.class);
		Query query = mock(Query.class);
		
		when(manager.getTransaction()).thenReturn(transaction);
		when(manager.find(Room.class, DaoTestUtils.DEFAULT_ROOM_ID)).thenReturn(room);
		when(manager.createQuery(any(String.class))).thenReturn(query);
		when(query.setParameter(any(String.class), eq(DaoTestUtils.DEFAULT_NAME))).thenReturn(query);
		when(query.getSingleResult()).thenReturn(room);
		
		doAnswer(new Answer<Room>() {
            public Room answer(InvocationOnMock invocation) {
                Room room = invocation.getArgument(0);
                room.setId(DaoTestUtils.NEW_ROOM_ID);
                return room;
            }
        }).when(manager).merge(any(Room.class));
		
		RoomDao dao = RoomDaoFactory.getInstance();
		dao.setEntityManager(manager);
		Room newRoom = dao.saveOrUpdate(room);
		dao.remove(newRoom);
		
		assertEquals(room, dao.findById(DaoTestUtils.DEFAULT_ROOM_ID));
		assertEquals(room, dao.findRoomByName(DaoTestUtils.DEFAULT_NAME));
		assertEquals(newRoom.getId(), DaoTestUtils.NEW_ROOM_ID);
		verify(query, times(1)).setParameter(any(String.class), eq(DaoTestUtils.DEFAULT_NAME));
		verify(manager, times(1)).remove(newRoom);
	}
}
