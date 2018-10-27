package java;

import static org.mockito.Mockito.*;

import entity.User;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

//The JUnit tests for User
public class UserTest {
	private static final int DEFAULT_ID = 1;
	private static final String DEFAULT_EMAIL = "x@columbia.edu";
	private static final String DEFAULT_PASSWORD = "***";
	private static final String DEFAULT_UNI = "x";
	private static final boolean DEFAULT_ADMIN = true;
	private static final int DEFAULT_USER_GROUP = 0;
	
	@Mock
	private EntityManager entityManager;
	
	@Mock
    private EntityTransaction transaction;
	
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
	public void userDatabaseTest() {
		User user = new User();
		user.setId(DEFAULT_ID)
		    .setEmail(DEFAULT_EMAIL)
		    .setPassword(DEFAULT_PASSWORD)
		    .setUni(DEFAULT_UNI)
		    .setIsAdmin(DEFAULT_ADMIN)
		    .setUserGroup(DEFAULT_USER_GROUP);
		
		when(entityManager.getTransaction()).thenReturn(transaction);
		when(entityManager.find(User.class, 1L)).thenReturn(user);
		
	}
}
