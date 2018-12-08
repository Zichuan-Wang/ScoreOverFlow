package utils;

import server.action.RoomAction;
import server.constraint.SearchRoomConstraint;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import entity.Room;

public class RoomActionTestUtils {

	public static RoomAction getRoomAction() {
		// for override
		RoomAction roomAction = mock(RoomAction.class);
		List<Object[]> overridableRooms = new ArrayList<>();
		Object[] entry = {EntityTestUtils.getDefaultOverridableRoom(),EntityTestUtils.DEFAULT_CAPACITY,EntityTestUtils.DEFAULT_FACILITY_NAME,EntityTestUtils.DEFAULT_USER_ID};
		overridableRooms.add(entry);
		when(roomAction.searchReservedRooms(any(SearchRoomConstraint.class))).thenReturn(overridableRooms);
		
		// for reserve
		List<Room> reserveRoom = new ArrayList<>();
		reserveRoom.add(EntityTestUtils.getDefaultRoom());
		when(roomAction.searchRooms(any(SearchRoomConstraint.class))).thenReturn(reserveRoom);
		
		when(roomAction.getRoomById(anyInt())).thenReturn(EntityTestUtils.getDefaultRoom());
		return roomAction;
	}

}
