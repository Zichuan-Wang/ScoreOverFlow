package utils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import dao.UserDao;
import entity.User;

public class UserDaoTestUtils {
	
	public static UserDao getUserDao() {
		
		EntityManager manager = mock(EntityManager.class);
		EntityTransaction transaction = mock(EntityTransaction.class);
		Query query = mock(Query.class);
		
		when(manager.getTransaction()).thenReturn(transaction);
		
		doAnswer(new Answer<User>() {
			public User answer(InvocationOnMock invocation) {
				int id = invocation.getArgument(1);
				return EntityTestUtils.getDefaultUser().setId(id);
				
			}
		}).when(manager).find(User.class, anyInt());
		
		when(manager.createQuery(any(String.class))).thenReturn(query);
		
		when(query.setParameter(any(String.class), eq(EntityTestUtils.DEFAULT_UNI))).thenReturn(query);
		when(query.setParameter(any(String.class), eq(EntityTestUtils.DEFAULT_EMAIL))).thenReturn(query);
		
		when(query.getSingleResult()).thenReturn(EntityTestUtils.getDefaultUser());
		
		doAnswer(new Answer<User>() {
            public User answer(InvocationOnMock invocation) {
                return invocation.getArgument(0);
            }
        }).when(manager).merge(any(User.class));

		return new UserDao(manager);
	}
	

}
