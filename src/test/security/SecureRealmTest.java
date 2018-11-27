package security;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.shiro.SecurityUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dao.UserDao;
import utils.UserDaoTestUtils;

public class SecureRealmTest {
	private SecureRealm realm;
	@BeforeEach
	public void onSetUp() {
		SecurityService.initialize(UserDaoTestUtils.getUserDao());
		realm = new SecureRealm(UserDaoTestUtils.getUserDao());
	}
	

	@Test
	public void mockDBCanNotGetUser() {
		assertThrows(NullPointerException.class, () -> {realm.getUser("test");});
	}
	
	@Test
	public void canAddRole() {
		realm.addRole("test");
		assertTrue(realm.roleExists("test"));
	}
	
	@AfterEach
	public void cleanUp() {
	}

}
