package entity;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.stubbing.Answer;

import dao.UserDao;
import entity.User;
import exception.DBConnectionException;

import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

//The JUnit tests for User
public class UserTest {
	private static final int DEFAULT_ID = 1;
	private static final int NEW_ID = 2;
	private static final String DEFAULT_EMAIL = "x@columbia.edu";
	private static final String DEFAULT_PASSWORD = "***";
	private static final String DEFAULT_UNI = "x";
	private static final boolean DEFAULT_ADMIN = true;
	private static final int DEFAULT_USER_GROUP = 0;
	
	@Test
	public void createAndUpdateUser() {
		User user = new User();
		user.setId(DEFAULT_ID)
		    .setEmail(DEFAULT_EMAIL)
		    .setPassword(DEFAULT_PASSWORD)
		    .setUni(DEFAULT_UNI)
		    .setIsAdmin(DEFAULT_ADMIN)
		    .setUserGroup(DEFAULT_USER_GROUP);
		
		assertEquals(user.getId(), DEFAULT_ID);
		assertEquals(user.getPassword(), DEFAULT_PASSWORD);
		assertEquals(user.getUni(), DEFAULT_UNI);
		assertEquals(user.getIsAdmin(), DEFAULT_ADMIN);
		assertEquals(user.getUserGroup(), DEFAULT_USER_GROUP);
	}
	
	@Test
	public void userDatabaseTest() throws DBConnectionException {
		User user = new User();
		user.setId(DEFAULT_ID)
		    .setEmail(DEFAULT_EMAIL)
		    .setPassword(DEFAULT_PASSWORD)
		    .setUni(DEFAULT_UNI)
		    .setIsAdmin(DEFAULT_ADMIN)
		    .setUserGroup(DEFAULT_USER_GROUP);
	
		EntityManager manager = mock(EntityManager.class);
		EntityTransaction transaction = mock(EntityTransaction.class);
		
		when(manager.getTransaction()).thenReturn(transaction);
		when(manager.find(User.class, DEFAULT_ID)).thenReturn(user);
		doAnswer(new Answer<User>() {
            public User answer(InvocationOnMock invocation) {
                User user = invocation.getArgument(0);
                user.setId(NEW_ID);
                return user;
            }
        }).when(manager).merge(any(User.class));
		
		UserDao dao = new UserDao();
		dao.setEntityManager(manager);
		User newUser = dao.saveOrUpdate(user);
		dao.remove(newUser);
		
		assertEquals(user, dao.findById(DEFAULT_ID));
		assertEquals(newUser.getId(), NEW_ID);
		verify(manager, times(1)).remove(newUser);
		
	}
}
