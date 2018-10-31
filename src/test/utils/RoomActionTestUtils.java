package utils;

import server.action.RoomAction;

public class RoomActionTestUtils {

	public static RoomAction getRoomAction() {
		return new RoomAction(RoomDaoTestUtils.getRoomDao());
	}

}
