package server.action;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

		Facility saved = dao.saveOrUpdate(new Facility().setName(EntityTestUtils.DEFAULT_FACILITY_NAME));
		assertEquals(1, action.findAllFacilities().size());
		assertEquals(EntityTestUtils.DEFAULT_FACILITY_NAME, action.findAllFacilities().get(0));
		dao.remove(saved);
	}
}
