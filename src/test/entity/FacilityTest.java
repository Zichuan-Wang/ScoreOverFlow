package entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import utils.EntityTestUtils;

//The JUnit tests for Room
public class FacilityTest {
	@Test
	public void createAndUpdateFacility() {
		Facility facility = EntityTestUtils.getDefaultFacility();
		assertEquals(facility.getId(), EntityTestUtils.DEFAULT_FACILITY_ID);
		assertEquals(facility.getName(), EntityTestUtils.DEFAULT_FACILITY_NAME);
	}
}
