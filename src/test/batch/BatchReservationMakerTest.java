package batch;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import server.constraint.SearchRoomConstraint;
import utils.EntityTestUtils;
import utils.ReservationActionTestUtils;
import utils.RoomActionTestUtils;

public class BatchReservationMakerTest {

	@Test
	public void testMockedMakeReservationsGreedy() {
		List<SearchRoomConstraint> constraints = new ArrayList<>();
		constraints.add(new SearchRoomConstraint());
		constraints.add(new SearchRoomConstraint());

		BatchReservationMaker maker = new BatchReservationMaker(RoomActionTestUtils.getRoomAction(),
				ReservationActionTestUtils.getReservationAction(), EntityTestUtils.getDefaultUser());
		
		List<Integer> unsuccessful = maker.makeReservationsGreedy(constraints);
		assertEquals(0, unsuccessful.size());
	}

}
