package test;

import org.junit.jupiter.api.Test;
import entity.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

//The JUnit tests for User
public class UserTest {
	private static final int DEFAULT_ID = 1;
	private static final String DEFAULT_EMAIL = "x@columbia.edu";
	private static final String DEFAULT_PASSWORD = "***";
	private static final String DEFAULT_UNI = "x";
	private static final boolean DEFAULT_ADMIN = true;
	private static final int DEFAULT_USER_GROUP = 0;
	
	@Test
	public void createAndUpdateUser() {
		User user = new User();
		user.setId(DEFAULT_ID)
		    .setEmail(DEFAULT_EMAIL)
		    .setPassword(DEFAULT_PASSWORD)
		    .setUni(DEFAULT_UNI)
		    .setIsAdmin(DEFAULT_ADMIN)
		    .setUserGroup(DEFAULT_USER_GROUP);
		
		assertEquals(user.getId(), DEFAULT_ID);
		assertEquals(user.getPassword(), DEFAULT_PASSWORD);
		assertEquals(user.getUni(), DEFAULT_UNI);
		assertEquals(user.getIsAdmin(), DEFAULT_ADMIN);
		assertEquals(user.getUserGroup(), DEFAULT_USER_GROUP);
	}
}
