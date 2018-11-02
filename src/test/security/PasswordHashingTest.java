package security;

import static org.junit.Assert.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

import org.junit.jupiter.api.Test;

public class PasswordHashingTest {

	private static final int MAX_LEN = 20;
	private static final int ITERATIONS = 100;

	@Test
	public void checkPasswordConsistency()
			throws IllegalArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
		for (int i = 0; i < ITERATIONS; i++) {
			int length = new Random().nextInt(MAX_LEN)+1;
			String password = generateRandomString(length);
			String hashed = PasswordHashing.getHash(password);
			assertTrue(PasswordHashing.check(password, hashed));
		}
	}

	private String generateRandomString(int length) {
		byte[] array = new byte[length];
		new Random().nextBytes(array);
		return new String(array);
	}

}
