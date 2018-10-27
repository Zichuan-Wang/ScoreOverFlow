package entity;

import static org.mockito.Mockito.*;

import entity.User;
import testUtils.TestUtils;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

//The JUnit tests for User
public class UserTest {

	@Mock
	private EntityManager entityManager;
	
	@Mock
    private EntityTransaction transaction;
	
	@Test
	public void createAndUpdateUser() {
		User user = TestUtils.getDefaultUser();
		
		assertEquals(user.getId(), TestUtils.DEFAULT_USER_ID);
		assertEquals(user.getPassword(), TestUtils.DEFAULT_PASSWORD);
		assertEquals(user.getUni(), TestUtils.DEFAULT_UNI);
		assertEquals(user.getIsAdmin(), TestUtils.DEFAULT_ADMIN);
		assertEquals(user.getUserGroup(), TestUtils.DEFAULT_USER_GROUP);
	}
	
	@Test
	public void userDatabaseTest() {
		User user = TestUtils.getDefaultUser();
		
		when(entityManager.getTransaction()).thenReturn(transaction);
		when(entityManager.find(User.class, 1L)).thenReturn(user);
		
	}
}
