package server.action;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dao.UserDao;
import dao.factory.UserDaoFactory;
import entity.User;
import exception.DBConnectionException;
import utils.EntityTestUtils;

public class UserActionTest {
	@Test
	public void findUserByUni() throws DBConnectionException {
		UserDao dao = UserDaoFactory.getInstance();
		User user = dao.findUserByUni(EntityTestUtils.DEFAULT_UNI);

		assertEquals(user.getId(), EntityTestUtils.DEFAULT_UNI);
	}
}
