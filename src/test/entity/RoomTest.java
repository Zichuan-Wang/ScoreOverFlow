package entity;

import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import dao.RoomDao;
import entity.Room;
import exception.DBConnectionException;

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

//The JUnit tests for Room
public class RoomTest {
	private static final int DEFAULT_ID = 1;
	private static final int NEW_ID = 2;
	private static final String DEFAULT_NAME = "Mudd 282";
	private static final int DEFAULT_CAPACITY = 15;
	
	@Test
	public void createAndUpdateRoom() {
		Room room = new Room();
		room.setId(DEFAULT_ID).setName(DEFAULT_NAME).setCapacity(DEFAULT_CAPACITY);
		
		assertEquals(room.getId(), DEFAULT_ID);
		assertEquals(room.getName(), DEFAULT_NAME);
		assertEquals(room.getCapacity(), DEFAULT_CAPACITY);
	}
	
	@Test
	public void roomDatabaseTest() throws DBConnectionException {
		Room room = new Room();
		room.setId(DEFAULT_ID).setName(DEFAULT_NAME).setCapacity(DEFAULT_CAPACITY);
	
		EntityManager manager = mock(EntityManager.class);
		EntityTransaction transaction = mock(EntityTransaction.class);
		Query query = mock(Query.class);
		
		when(manager.getTransaction()).thenReturn(transaction);
		when(manager.find(Room.class, DEFAULT_ID)).thenReturn(room);
		when(manager.createQuery(any(String.class))).thenReturn(query);
		when(query.setParameter(any(String.class), eq(DEFAULT_NAME))).thenReturn(query);
		when(query.getSingleResult()).thenReturn(room);
		
		doAnswer(new Answer<Room>() {
            public Room answer(InvocationOnMock invocation) {
                Room room = invocation.getArgument(0);
                room.setId(NEW_ID);
                return room;
            }
        }).when(manager).merge(any(Room.class));
		
		RoomDao dao = new RoomDao();
		dao.setEntityManager(manager);
		Room newRoom = dao.saveOrUpdate(room);
		dao.remove(newRoom);
		
		assertEquals(room, dao.findById(DEFAULT_ID));
		assertEquals(room, dao.findRoomByName(DEFAULT_NAME));
		assertEquals(newRoom.getId(), NEW_ID);
		verify(query, times(1)).setParameter(any(String.class), eq(DEFAULT_NAME));
		verify(manager, times(1)).remove(newRoom);
	}
}
