package security;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dao.UserDao;
import utils.EntityTestUtils;
import utils.UserDaoTestUtils;

public class SecureRealmTest {
	private SecureRealm realm;

	@BeforeEach
	public void onSetUp() {
		SecurityService.initialize(UserDaoTestUtils.getUserDao());
		realm = new SecureRealm(UserDaoTestUtils.getUserDao());
	}
	
	@Test
	public void nullConstructorParameterTest() {
		new SecureRealm((UserDao)null);
	}
	
	@Test
	public void stringConstructorParameterTest() {
		new SecureRealm("test");
	}

	@Test
	public void mockDBCanNotGetUser() {
		assertThrows(NullPointerException.class, () -> {
			realm.getUser("test");
		});
	}

	@Test
	public void canAddAndCheckRole() {
		realm.addRole("test");
		assertTrue(realm.roleExists("test"));
	}
	
	@Test
	public void canCheckNonExistingRole() {
		assertFalse(realm.roleExists(""));
	}
	
	@Test
	public void canCheckExistingAccount() {
		assertTrue(realm.accountExists(EntityTestUtils.DEFAULT_UNI));
	}
	
	@Test
	public void canCheckNonExistingAccount() {
		assertFalse(new SecureRealm().accountExists(""));
	}

	@AfterEach
	public void cleanUp() {
	}

}
