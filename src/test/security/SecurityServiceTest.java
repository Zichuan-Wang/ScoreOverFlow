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
		LoginStatus status = SecurityService.Login(EntityTestUtils.DEFAULT_UNI, EntityTestUtils.DEFAULT_PASSWORD);
		Assert.assertTrue(status.isSuccess());
		Assert.assertEquals("Success", status.getMessage());
		SecurityUtils.getSubject().logout();
	}

	@Test
	public void loginWithCorrectUsernameWrongPassword() {
		LoginStatus status = SecurityService.Login(EntityTestUtils.DEFAULT_UNI, "123");
		Assert.assertFalse(status.isSuccess());
		Assert.assertEquals("Wrong password", status.getMessage());
		SecurityUtils.getSubject().logout();
	}

	@Test
	public void loginWithWrongUsernameSomePassword() {
		LoginStatus status = SecurityService.Login("666", "123");
		Assert.assertFalse(status.isSuccess());
		Assert.assertEquals("Unknown account", status.getMessage());
		SecurityUtils.getSubject().logout();

	}

	@Test
	public void loginWithNoUsernameSomePassword() {
		LoginStatus status = SecurityService.Login("", "123");
		Assert.assertFalse(status.isSuccess());
		Assert.assertEquals("Unknown account", status.getMessage());
		SecurityUtils.getSubject().logout();
	}

	@Test
	public void loginWithSomeUsernameNoPassword() {
		LoginStatus status = SecurityService.Login(EntityTestUtils.DEFAULT_UNI, "");
		Assert.assertFalse(status.isSuccess());
		Assert.assertEquals("Wrong password", status.getMessage());
		SecurityUtils.getSubject().logout();
	}

	@Test
	public void loginWithNoUsernameNoPassword() {
		LoginStatus status = SecurityService.Login("", "");
		Assert.assertFalse(status.isSuccess());
		Assert.assertEquals("Unknown account", status.getMessage());
		SecurityUtils.getSubject().logout();
	}

	@Test
	public void repeatLoginThrowsException() throws InterruptedException {
		SecurityService.Login(EntityTestUtils.DEFAULT_UNI, EntityTestUtils.DEFAULT_PASSWORD);
		LoginStatus status = SecurityService.Login(EntityTestUtils.DEFAULT_UNI, EntityTestUtils.DEFAULT_PASSWORD);
		Assert.assertFalse(status.isSuccess());
		Assert.assertEquals("The user is already authenticated", status.getMessage());
		SecurityUtils.getSubject().logout();
	}

	@AfterAll
	public static void cleanUp() {
		dao.remove(user);
	}

}
