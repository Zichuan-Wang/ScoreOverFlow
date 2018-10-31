package dao.factory;

import javax.persistence.EntityManager;

import org.hibernate.service.spi.ServiceException;

import dao.DaoUtils;
import dao.RoomDao;
import exception.DBConnectionException;

public class RoomDaoFactory {

	private static RoomDao dao;

	public static RoomDao getInstance() throws DBConnectionException {
		if (dao == null) {
			try {
				EntityManager manager = DaoUtils.factory.createEntityManager();
				dao = new RoomDao(manager);
			} catch (ServiceException e) {
				// Unable to connect to database
				throw new DBConnectionException(e);
			}
		}
		return dao;
	}

}
