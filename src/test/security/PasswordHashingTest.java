package security;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import utils.UserDaoTestUtils;

public class PasswordHashingTest {
	@BeforeEach
	public void onSetUp() {
		SecurityService.initialize(UserDaoTestUtils.getUserDao());
	}

	@Test
	public void getHashNotNull() {
		Assert.assertNotNull(PasswordHashing.getHash("666"));
	}

	@Test
	public void getHashNotEmpty() {
		Assert.assertNotEquals("", PasswordHashing.getHash("666"));
	}

	@Test
	public void getHashDoNotAcceptEmpty() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			PasswordHashing.getHash("");
		});
	}

	@Test
	public void emptyHashedCheckThrowsException() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			PasswordHashing.check("123456", "");
		});
	}

	@Test
	public void falseCheckReturnsFalse() {
		Assert.assertFalse(PasswordHashing.check("123456", " $ "));
	}

	@Test
	public void getSaltAndHashedPasswordNotNull() {
		Assert.assertNotNull(PasswordHashing.getSaltAndHashedPassword(" $ "));
	}

	@AfterEach
	public void cleanUp() {
	}

}
