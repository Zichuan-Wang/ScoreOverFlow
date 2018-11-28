package security;

import org.apache.shiro.SecurityUtils;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dao.UserDao;
import dao.factory.UserDaoFactory;
import entity.User;
import exception.DBConnectionException;
import utils.EntityTestUtils;

public class SecurityServiceTest {
	static UserDao dao;
	static User user;
	@BeforeAll
	public static void prepareDAOAndUser() throws DBConnectionException {
		dao = UserDaoFactory.getInstance();
		user = dao.saveOrUpdate(EntityTestUtils.getDefaultUser());
	}
	@BeforeEach
	public void onSetUp() throws DBConnectionException {
		SecurityService.initialize(dao);
	}

	@Test
	public void hasSubject() {
		SecurityUtils.getSubject();
	}

	@Test
	public void loginWithCorrectCredentials() {
		 Assert.assertTrue(SecurityService.Login(EntityTestUtils.DEFAULT_UNI, EntityTestUtils.DEFAULT_PASSWORD).isSuccess());
		
	}

	@AfterAll
	public static void cleanUp() {
		dao.remove(user);
	}

}
