package main.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import main.entity.User;
import main.exception.DBConnectionException;

public class UserDao extends BaseDao<User>{
	
	public UserDao() throws DBConnectionException {
		super(User.class);
	}

	/*
	 * Find the user by UNI, return null if not found
	 */
	public User findUserByUni(String uni) {
		Query query = manager.createQuery("SELECT u FROM User u WHERE u.uni = :uni").setParameter("uni", uni);
		try {
			return (User) query.getSingleResult();
		} catch (NoResultException exception) {
			return null;
		}
	}
	
	/*
	 * Find the user by email, return null if not found
	 */
	public User findUserByEmail(String email) {
		Query query = manager.createQuery("SELECT u FROM User u WHERE u.email = :email").setParameter("email", email);
		try {
			return (User) query.getSingleResult();
		} catch (NoResultException exception) {
			return null;
		}
	}
}
