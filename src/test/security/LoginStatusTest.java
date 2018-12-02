package security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

public class LoginStatusTest {
	private final String TEST_STRING = "test";
	
	@Test
	public void constructorTest() {
		new LoginStatus(true, null);
	}
	
	@Test
	public void infoTest() {
		LoginStatus loginStatus = new LoginStatus(true, TEST_STRING);
		assertTrue(loginStatus.isSuccess());
		assertEquals(TEST_STRING, loginStatus.getMessage());
	}

}
