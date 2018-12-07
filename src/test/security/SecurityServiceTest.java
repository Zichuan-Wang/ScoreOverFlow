package security;

import org.apache.shiro.SecurityUtils;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import dao.UserDao;
import exception.DBConnectionException;
import utils.EntityTestUtils;
import utils.UserDaoTestUtils;

public class SecurityServiceTest {
	static UserDao dao;

	@BeforeAll
	public static void prepareDAOAndUser() throws DBConnectionException {
		dao = UserDaoTestUtils.getUserDao();
		SecurityService.initialize(dao);
	}
	
	@Test
	public void initializeWithDao() {
		SecurityService.initialize(dao);
	}
	
	@Test
	public void initializeWithNull() {
		SecurityService.initialize(null);
	}

	@Test
	public void hasSubject() {
		SecurityService.initialize(dao);
		SecurityUtils.getSubject();
	}

	@Test
	public void loginWithCorrectCredentials() {
		SecurityService.initialize(dao);
		LoginStatus status = SecurityService.Login(EntityTestUtils.DEFAULT_UNI, EntityTestUtils.DEFAULT_PASSWORD);
		Assert.assertTrue(status.isSuccess());
		Assert.assertEquals("Success", status.getMessage());
		SecurityUtils.getSubject().logout();
	}

	@Test
	public void loginWithCorrectUsernameWrongPassword() {
		SecurityService.initialize(dao);
		LoginStatus status = SecurityService.Login(EntityTestUtils.DEFAULT_UNI, "123");
		Assert.assertFalse(status.isSuccess());
		Assert.assertEquals("Wrong password", status.getMessage());
		SecurityUtils.getSubject().logout();
	}

	@Test
	public void loginWithWrongUsernameSomePassword() {
		SecurityService.initialize(dao);
		LoginStatus status = SecurityService.Login("666", "123");
		Assert.assertFalse(status.isSuccess());
		Assert.assertEquals("Unknown account", status.getMessage());
		SecurityUtils.getSubject().logout();

	}

	@Test
	public void loginWithNoUsernameSomePassword() {
		SecurityService.initialize(dao);
		LoginStatus status = SecurityService.Login("", "123");
		Assert.assertFalse(status.isSuccess());
		Assert.assertEquals("Unknown account", status.getMessage());
		SecurityUtils.getSubject().logout();
	}

	@Test
	public void loginWithSomeUsernameNoPassword() {
		SecurityService.initialize(dao);
		LoginStatus status = SecurityService.Login(EntityTestUtils.DEFAULT_UNI, "");
		Assert.assertFalse(status.isSuccess());
		Assert.assertEquals("Wrong password", status.getMessage());
		SecurityUtils.getSubject().logout();
	}

	@Test
	public void loginWithNoUsernameNoPassword() {
		SecurityService.initialize(dao);
		LoginStatus status = SecurityService.Login("", "");
		Assert.assertFalse(status.isSuccess());
		Assert.assertEquals("Unknown account", status.getMessage());
		SecurityUtils.getSubject().logout();
	}

	@Test
	public void repeatLoginThrowsException() throws InterruptedException {
		SecurityService.initialize(dao);
		SecurityService.Login(EntityTestUtils.DEFAULT_UNI, EntityTestUtils.DEFAULT_PASSWORD);
		LoginStatus status = SecurityService.Login(EntityTestUtils.DEFAULT_UNI, EntityTestUtils.DEFAULT_PASSWORD);
		Assert.assertFalse(status.isSuccess());
		Assert.assertEquals("The user is already authenticated", status.getMessage());
		SecurityUtils.getSubject().logout();
	}

	@AfterAll
	public static void cleanUp() {
	}

}
