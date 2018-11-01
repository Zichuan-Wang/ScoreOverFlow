package server.action;

import dao.UserDao;
import entity.User;

public class UserAction {

	private UserDao dao;
	
	public UserAction(UserDao dao) {
		this.dao = dao;
	}
	
	public User findUserByUni(String uni) {
		return dao.findUserByUni(uni);
	}
}
