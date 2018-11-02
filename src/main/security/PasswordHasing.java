package security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.commons.codec.binary.Base64;

public class PasswordHasing {

	private static final int ITERATIONS = 20000;
	private static final int SALT_LEN = 32;
	private static final int KEY_LEN = 256;

	private static final String RANDOM_ALGORITHM = "SHA1PRNG";
	private static final String KEY_ALGORITHM = "PBKDF2WithHmacSHA1";

	public static String getHash(String password)
			throws IllegalArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] salt = SecureRandom.getInstance(RANDOM_ALGORITHM).generateSeed(SALT_LEN);
		return Base64.encodeBase64String(salt) + "$" + getSaltedHash(password, salt);
	}

	public static boolean check(String password, String hashed)
			throws IllegalArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
		String[] saltAndHash = hashed.split("\\$");
		if (saltAndHash.length != 2) {
			throw new IllegalArgumentException("The stored password must have the form 'salt$hash'");
		}
		String hashOfInput = getSaltedHash(password, Base64.decodeBase64(saltAndHash[0]));
		return hashOfInput.equals(saltAndHash[1]);
	}

	private static String getSaltedHash(String password, byte[] salt)
			throws IllegalArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
		if (password == null || password.length() == 0) {
			throw new IllegalArgumentException("Empty passwords are not supported.");
		}
		SecretKeyFactory factory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
		PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LEN);
		SecretKey key = factory.generateSecret(spec);
		return Base64.encodeBase64String(key.getEncoded());
	}

}
