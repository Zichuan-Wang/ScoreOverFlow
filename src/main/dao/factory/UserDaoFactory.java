package dao.factory;

import dao.UserDao;
import exception.DBConnectionException;

public class UserDaoFactory {

	private static UserDao dao;
	
	public UserDao getInstance() throws DBConnectionException {
		if (dao == null) {
			dao = new UserDao();
		}
		return dao;
	}
	
}
