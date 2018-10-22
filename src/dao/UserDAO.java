package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import entity.User;

public class UserDAO {
	
	private EntityManager manager;
	
	public UserDAO() {
		manager = DAOUtils.factory.createEntityManager();
	}
	
	/*
	 * Find the user by uni, return null if not found
	 */
	public User findUserByUNI(String uni) {
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
	
	/*
	 * Save the user or update its fields, returns the managed entity
	 */
	public User saveOrUpdateUser(User user) {
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
		User saved = manager.merge(user);
		transaction.commit();
		return saved;
	}
	
	/*
	 * Remove the user
	 * 
	 * @param user managed user entity 
	 */
	public void removeUser(User user) {
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
		manager.remove(user);
		transaction.commit();
	}
}
