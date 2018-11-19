package server.action;

import java.util.List;

import dao.FacilityDao;
import entity.Facility;

// Handle all actions related to facility
public class FacilityAction {

	private FacilityDao dao;

	public FacilityAction(FacilityDao dao) {
		this.dao = dao;
	}

	public List<Facility> findAllFacilities() {
		return dao.findAllFacilities();
	}
}