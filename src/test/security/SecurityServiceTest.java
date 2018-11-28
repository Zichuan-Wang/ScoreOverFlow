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

public class SecurityServiceTest {
	static UserDao dao;
	static User user;
	@BeforeAll
	public static void prepareDAOAndUser() throws DBConnectionException {
		dao = UserDaoFactory.getInstance();
		user = dao.saveOrUpdate(new User().setUni("test").setPassword(PasswordHashing.getHash("123")).setUserGroup(0));
		dao.remove(user);
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
		 Assert.assertTrue(SecurityService.Login("test", "123").isSuccess());
		
	}

	@AfterAll
	public static void cleanUp() {
		
	}

}
