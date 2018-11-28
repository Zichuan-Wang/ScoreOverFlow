package server.action;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import dao.FacilityDao;
import dao.factory.FacilityDaoFactory;
import entity.Facility;
import exception.DBConnectionException;
import utils.EntityTestUtils;

public class FacilityActionTest {

	@Test
	public void testFindAllFacilities() throws DBConnectionException {
		FacilityDao dao = FacilityDaoFactory.getInstance();
		FacilityAction action = new FacilityAction(dao);
		assertEquals(0, action.findAllFacilities().size());

		Facility facility = dao.saveOrUpdate(EntityTestUtils.getDefaultFacility());
		List<Facility> facilities = action.findAllFacilities();
		dao.remove(facility);
		assertEquals(1, facilities.size());
		assertEquals(EntityTestUtils.DEFAULT_FACILITY_NAME, facilities.get(0));
		
	}
}
