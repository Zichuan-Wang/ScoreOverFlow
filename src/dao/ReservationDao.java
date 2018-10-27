package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import entity.Reservation;

public class ReservationDao {
	
	private EntityManager manager;
	
	public ReservationDao() {
		manager = DaoUtils.factory.createEntityManager();
	}
	
	/*
	 * Find the reservation by id, return null if not found
	 */
	public Reservation findRoomById (int id) {
		Query query = manager.createQuery("SELECT u FROM Reservation u WHERE u.id = :id").setParameter("id", id);
		try {
			return (Reservation) query.getSingleResult();
		} catch (NoResultException exception) {
			return null;
		}
	}
	
	/*
	 * Save the reservation or update its fields, returns the managed entity
	 */
	public Reservation saveOrUpdateRoom(Reservation reservation) {
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
		Reservation saved = manager.merge(reservation);
		transaction.commit();
		return saved;
	}
	
	/*
	 * Remove the reservation
	 * 
	 * @param reservation managed reservation entity 
	 */
	public void removeUser(Reservation reservation) {
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
		manager.remove(reservation);
		transaction.commit();
	}
}
