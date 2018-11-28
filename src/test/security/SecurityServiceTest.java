package security;

import org.apache.shiro.SecurityUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dao.factory.UserDaoFactory;
import exception.DBConnectionException;

public class SecurityServiceTest {
	@BeforeEach
	public void onSetUp() throws DBConnectionException {
		SecurityService.initialize(UserDaoFactory.getInstance());
	}

	@Test
	public void hasSubject() {
		SecurityUtils.getSubject();
	}

	@Test
	public void canLogin() {

		SecurityService.Login("Test", "test");
	}

	@AfterEach
	public void cleanUp() {
	}

}
