package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

// The parent class of all DAO classes
public abstract class BaseDao<T> {

	@PersistenceContext
	protected EntityManager manager;
	
	protected final Class<T> paramClass;
	
	public void setEntityManager(EntityManager manager) {
		this.manager = manager;
	}
	
	public BaseDao(Class<T> paramClass, EntityManager manager) {
		this.paramClass = paramClass;
		this.manager = manager;
	}
	
	public T findById(int id) {
		return manager.find(paramClass, id);
	}
	
	/*
	 * Save the entity or update its fields, returns the managed 
	 * entity
	 */
	public T saveOrUpdate(T t) {
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
		T saved = manager.merge(t);
		transaction.commit();
		manager.clear();
		return saved;
	}
	
	/*
	 * Remove the entity
	 * 
	 * @param t managed entity 
	 */
	public void remove(T t) {
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
		manager.remove(manager.contains(t)? t: manager.merge(t));
		transaction.commit();
	}
	
	
}
