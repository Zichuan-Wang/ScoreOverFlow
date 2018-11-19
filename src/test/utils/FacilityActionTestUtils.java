package utils;

import server.action.FacilityAction;

public class FacilityActionTestUtils {

	public static FacilityAction getFacilityAction() {
		return new FacilityAction(FacilityDaoTestUtils.getFacilityDao());
	}

}
