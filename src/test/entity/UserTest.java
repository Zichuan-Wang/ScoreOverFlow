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
	@Test
	public void createAndUpdateUser() {
		User user = TestUtils.getDefaultUser();
		
		assertEquals(user.getId(), TestUtils.DEFAULT_USER_ID);
		assertEquals(user.getPassword(), TestUtils.DEFAULT_PASSWORD);
		assertEquals(user.getUni(), TestUtils.DEFAULT_UNI);
		assertEquals(user.getIsAdmin(), TestUtils.DEFAULT_ADMIN);
		assertEquals(user.getUserGroup(), TestUtils.DEFAULT_USER_GROUP);
	}
}
