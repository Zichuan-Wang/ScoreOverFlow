package entity;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.eq;
import org.mockito.stubbing.Answer;

import dao.UserDao;
import entity.User;
import exception.DBConnectionException;
import utils.TestUtils;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

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
	public void userDatabaseTest() throws DBConnectionException {
		User user = TestUtils.getDefaultUser();
	
		EntityManager manager = mock(EntityManager.class);
		EntityTransaction transaction = mock(EntityTransaction.class);
		Query query = mock(Query.class);
		
		when(manager.getTransaction()).thenReturn(transaction);
		when(manager.find(User.class, TestUtils.DEFAULT_USER_ID)).thenReturn(user);
		when(manager.createQuery(any(String.class))).thenReturn(query);
		when(query.setParameter(any(String.class), eq(TestUtils.DEFAULT_UNI))).thenReturn(query);
		when(query.setParameter(any(String.class), eq(TestUtils.DEFAULT_EMAIL))).thenReturn(query);
		when(query.getSingleResult()).thenReturn(user);
		
		doAnswer(new Answer<User>() {
            public User answer(InvocationOnMock invocation) {
                User user = invocation.getArgument(0);
                user.setId(TestUtils.NEW_USER_ID);
                return user;
            }
        }).when(manager).merge(any(User.class));

		UserDao dao = new UserDao();
		dao.setEntityManager(manager);
		User newUser = dao.saveOrUpdate(user);
		dao.remove(newUser);
		
		assertEquals(user, dao.findById(TestUtils.DEFAULT_USER_ID));
		assertEquals(user, dao.findUserByUni(TestUtils.DEFAULT_UNI));
		assertEquals(user, dao.findUserByEmail(TestUtils.DEFAULT_EMAIL));
		assertEquals(newUser.getId(), TestUtils.NEW_USER_ID);
		verify(query, times(1)).setParameter(any(String.class), eq(TestUtils.DEFAULT_UNI));
		verify(query, times(1)).setParameter(any(String.class), eq(TestUtils.DEFAULT_EMAIL));
		verify(manager, times(1)).remove(newUser);
	}
}
