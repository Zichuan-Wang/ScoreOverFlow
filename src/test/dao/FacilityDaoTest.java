package dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.Test;

import entity.Facility;
import exception.DBConnectionException;
import utils.EntityTestUtils;
import utils.FacilityDaoTestUtils;

public class FacilityDaoTest {
	@Test
	public void facilityDatabaseTest() throws DBConnectionException {
		FacilityDao dao = FacilityDaoTestUtils.getFacilityDao();
		Facility facility = dao.findById(EntityTestUtils.DEFAULT_FACILITY_ID);
		dao.saveOrUpdate(facility);
		dao.remove(facility);
		List<Facility> facilities = dao.findAllFacilities();

		assertEquals(facility.getId(), EntityTestUtils.DEFAULT_FACILITY_ID);
		assertEquals(dao.findFacilityByName(EntityTestUtils.DEFAULT_FACILITY_NAME).getName(),
				EntityTestUtils.DEFAULT_FACILITY_NAME);
		for (int i = 0; i < facilities.size(); i++) {
			assertEquals(facilities.get(i).getId(), FacilityDaoTestUtils.FACILITY_IDS[i]);
		}
		verify(dao.manager, times(1)).remove(facility);
	}
}
