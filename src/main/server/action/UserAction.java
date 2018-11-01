package server.action;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import dao.UserDao;
import entity.User;
import utils.PasswordUtils;

public class UserAction {

	private UserDao dao;
	
	public UserAction(UserDao dao) {
		this.dao = dao;
	}
	
	public User findUserByUni(String uni) {
		return dao.findUserByUni(uni);
	}
	
	public boolean verifyPassword(User user, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		try {
			return PasswordUtils.check(password, user.getPassword());
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
}
