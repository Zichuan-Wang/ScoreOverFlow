

import org.junit.jupiter.api.Test;

import server.SearchReservationConstraint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SearchReservationConstraintTest {
	private static final int DEFAULT_USER_ID = 1;
	
	@Test
	public void createAndChangeSearchConstraint() {
		SearchReservationConstraint constraint = new SearchReservationConstraint();
		assertNotNull(constraint);
		
		constraint.setUserId(DEFAULT_USER_ID);
		
		assertEquals(constraint.getUserId(), DEFAULT_USER_ID);
	}
	
}
