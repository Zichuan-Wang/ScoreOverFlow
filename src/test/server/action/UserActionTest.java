package server.action;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
		User user = action.findUserByUni("dw2735");
		assertEquals(user.getUni(), "dw2735");
	}
	
	@Test
	public void findUserByUni_userNotExist() throws DBConnectionException {
		UserDao dao = UserDaoFactory.getInstance();
		UserAction action = new UserAction(dao);
		User user = action.findUserByUni(EntityTestUtils.DEFAULT_UNI);

		assertNull(user.getId());
	}
}
