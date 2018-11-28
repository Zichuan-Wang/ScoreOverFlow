package server.action;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import dao.UserDao;
import dao.factory.UserDaoFactory;
import entity.User;
import exception.DBConnectionException;
import utils.EntityTestUtils;

public class UserActionTest {
	@Test
	public void findUserByUni_userExists() throws DBConnectionException {
		UserDao dao = UserDaoFactory.getInstance();
		UserAction action = new UserAction(dao);
		User user = dao.saveOrUpdate(EntityTestUtils.getDefaultUser());
		assertNotNull(action.findUserByUni(EntityTestUtils.DEFAULT_UNI));
		assertEquals(EntityTestUtils.DEFAULT_UNI, action.findUserByUni(EntityTestUtils.DEFAULT_UNI).getUni());
		dao.remove(user);
	}

	@Test
	public void findUserByUni_userNotExist() throws DBConnectionException {
		UserDao dao = UserDaoFactory.getInstance();
		UserAction action = new UserAction(dao);
		assertNull(action.findUserByUni(EntityTestUtils.DEFAULT_UNI));
	}
}
