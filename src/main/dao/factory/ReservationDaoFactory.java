package dao.factory;

import dao.ReservationDao;
import exception.DBConnectionException;

public class ReservationDaoFactory {

	private static ReservationDao dao;
	
	public static ReservationDao getInstance() throws DBConnectionException {
		if (dao == null) {
			dao = new ReservationDao();
		}
		return dao;
	}
	
}
