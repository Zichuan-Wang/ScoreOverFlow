package entity;

import entity.User;
import utils.EntityTestUtils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//The JUnit tests for User
public class UserTest {
	@Test
	public void createAndUpdateUser() {
		User user = EntityTestUtils.getDefaultUser();
		
		assertEquals(user.getId(), EntityTestUtils.DEFAULT_USER_ID);
		assertEquals(user.getPassword(), EntityTestUtils.DEFAULT_PASSWORD);
		assertEquals(user.getUni(), EntityTestUtils.DEFAULT_UNI);
		assertEquals(user.getIsAdmin(), EntityTestUtils.DEFAULT_ADMIN);
		assertEquals(user.getUserGroup(), EntityTestUtils.DEFAULT_USER_GROUP);
	}
}
