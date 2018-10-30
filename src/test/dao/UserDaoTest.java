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

import dao.factory.UserDaoFactory;
import entity.User;
import exception.DBConnectionException;
import utils.DaoTestUtils;

public class UserDaoTest {
	
	@Test
	public void userDatabaseTest() throws DBConnectionException {
		User user = DaoTestUtils.getDefaultUser();
	
		EntityManager manager = mock(EntityManager.class);
		EntityTransaction transaction = mock(EntityTransaction.class);
		Query query = mock(Query.class);
		
		when(manager.getTransaction()).thenReturn(transaction);
		when(manager.find(User.class, DaoTestUtils.DEFAULT_USER_ID)).thenReturn(user);
		when(manager.createQuery(any(String.class))).thenReturn(query);
		when(query.setParameter(any(String.class), eq(DaoTestUtils.DEFAULT_UNI))).thenReturn(query);
		when(query.setParameter(any(String.class), eq(DaoTestUtils.DEFAULT_EMAIL))).thenReturn(query);
		when(query.getSingleResult()).thenReturn(user);
		
		doAnswer(new Answer<User>() {
            public User answer(InvocationOnMock invocation) {
                User user = invocation.getArgument(0);
                user.setId(DaoTestUtils.NEW_USER_ID);
                return user;
            }
        }).when(manager).merge(any(User.class));

		UserDao dao = UserDaoFactory.getInstance();
		dao.setEntityManager(manager);
		User newUser = dao.saveOrUpdate(user);
		dao.remove(newUser);
		
		assertEquals(user, dao.findById(DaoTestUtils.DEFAULT_USER_ID));
		assertEquals(user, dao.findUserByUni(DaoTestUtils.DEFAULT_UNI));
		assertEquals(user, dao.findUserByEmail(DaoTestUtils.DEFAULT_EMAIL));
		assertEquals(newUser.getId(), DaoTestUtils.NEW_USER_ID);
		verify(query, times(1)).setParameter(any(String.class), eq(DaoTestUtils.DEFAULT_UNI));
		verify(query, times(1)).setParameter(any(String.class), eq(DaoTestUtils.DEFAULT_EMAIL));
		verify(manager, times(1)).remove(newUser);
	}
}
