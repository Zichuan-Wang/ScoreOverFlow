package entity;

import entity.User;
import utils.TestUtils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//The JUnit tests for User
public class UserTest {
	@Test
	public void createAndUpdateUser() {
		User user = TestUtils.getDefaultUser();
		
		assertEquals(user.getId(), TestUtils.DEFAULT_USER_ID);
		assertEquals(user.getPassword(), TestUtils.DEFAULT_PASSWORD);
		assertEquals(user.getUni(), TestUtils.DEFAULT_UNI);
		assertEquals(user.getIsAdmin(), TestUtils.DEFAULT_ADMIN);
		assertEquals(user.getUserGroup(), TestUtils.DEFAULT_USER_GROUP);
	}
}
