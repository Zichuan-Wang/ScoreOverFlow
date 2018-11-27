package security;

import org.apache.shiro.SecurityUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import utils.UserDaoTestUtils;

public class SecurityServiceTest {
	@BeforeEach
	public void onSetUp() {
		SecurityService.initialize(UserDaoTestUtils.getUserDao());
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
