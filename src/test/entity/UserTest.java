package entity;

import entity.User;
import utils.DaoTestUtils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//The JUnit tests for User
public class UserTest {
	@Test
	public void createAndUpdateUser() {
		User user = DaoTestUtils.getDefaultUser();
		
		assertEquals(user.getId(), DaoTestUtils.DEFAULT_USER_ID);
		assertEquals(user.getPassword(), DaoTestUtils.DEFAULT_PASSWORD);
		assertEquals(user.getUni(), DaoTestUtils.DEFAULT_UNI);
		assertEquals(user.getIsAdmin(), DaoTestUtils.DEFAULT_ADMIN);
		assertEquals(user.getUserGroup(), DaoTestUtils.DEFAULT_USER_GROUP);
	}
}
