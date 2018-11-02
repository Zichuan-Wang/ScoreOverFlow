package utils;

import server.action.UserAction;

public class UserActionTestUtils {

	public static UserAction getUserAction() {
		return new UserAction(UserDaoTestUtils.getUserDao());
	}
}
