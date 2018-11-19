package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import entity.Facility;

public class FacilityDao extends BaseDao<Facility> {

	public FacilityDao(EntityManager manager) {
		super(Facility.class, manager);
	}

	/*
	 * Find the facility by name, return null if not found
	 */
	public Facility findFacilityByName(String name) {
		Query query = manager.createQuery("SELECT u FROM Facility u WHERE u.name = :name").setParameter("name", name);
		try {
			return (Facility) query.getSingleResult();
		} catch (NoResultException exception) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Facility> findAllFacilities() {
		return (List<Facility>) manager.createQuery("SELECT u from Facility u").getResultList();
	}

}
