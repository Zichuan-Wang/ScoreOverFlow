package dao.factory;

import dao.RoomDao;
import exception.DBConnectionException;

public class RoomDaoFactory {

	private static RoomDao dao;
	
	public static RoomDao getInstance() throws DBConnectionException {
		//if (dao == null) {
			dao = new RoomDao();
		//}
		return dao;
	}
	
}
