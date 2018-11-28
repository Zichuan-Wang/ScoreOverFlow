package security;

import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;

public class PasswordHashing {

	private static final int ITERATIONS = 20000;

	public static String getHash(String password) {
		
		RandomNumberGenerator rng = new SecureRandomNumberGenerator();
		byte[] salt = rng.nextBytes().getBytes();
		
		return Base64.encodeBase64String(salt) + "$" + getSaltedHash(password, salt);
	}

	public static boolean check(String password, String hashed) {
		String[] saltAndHash = hashed.split("\\$");
		if (saltAndHash.length != 2) {
			throw new IllegalArgumentException("The stored password must have the form 'salt$hash'");
		}
		String hashOfInput = getSaltedHash(password, Base64.decodeBase64(saltAndHash[0]));
		return hashOfInput.equals(saltAndHash[1]);
	}

	private static String getSaltedHash(String password, byte[] salt){
		if (password == null || password.length() == 0) {
			throw new IllegalArgumentException("Empty passwords are not supported.");
		}
		return new Sha256Hash(password, salt, ITERATIONS).toBase64();
	}
	public static String[] getSaltAndHashedPassword(String hashed) {
		return hashed.split("\\$");
		
	}

}
