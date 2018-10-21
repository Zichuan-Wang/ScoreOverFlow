package dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entity.User;

public class UserDAO {
	
	private EntityManager manager;
	
	public UserDAO() {
		manager = DAOUtils.factory.createEntityManager();
	}
	
	public User findUserByUNI(String uni) {
		Query query = manager.createQuery("SELECT u FROM User u WHERE u.uni = :uni").setParameter("uni", uni);
		return (User) query.getSingleResult();
	}
}
