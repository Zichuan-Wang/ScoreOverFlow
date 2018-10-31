package dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;

import entity.User;
import exception.DBConnectionException;
import utils.EntityTestUtils;
import utils.UserDaoTestUtils;

public class UserDaoTest {

	@Test
	public void userDatabaseTest() throws DBConnectionException {
		UserDao dao = UserDaoTestUtils.getUserDao();
		User user = dao.findById(EntityTestUtils.DEFAULT_USER_ID);
		dao.saveOrUpdate(user);
		dao.remove(user);

		assertEquals(user.getId(), EntityTestUtils.DEFAULT_USER_ID);
		assertEquals(dao.findUserByUni(EntityTestUtils.DEFAULT_UNI).getUni(), EntityTestUtils.DEFAULT_UNI);
		assertEquals(dao.findUserByEmail(EntityTestUtils.DEFAULT_EMAIL).getEmail(), EntityTestUtils.DEFAULT_EMAIL);
		verify(dao.manager, times(1)).remove(user);
	}
}
