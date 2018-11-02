package dao.factory;

import javax.persistence.EntityManager;

import org.hibernate.service.spi.ServiceException;

import dao.DaoFactory;
import dao.ReservationDao;
import exception.DBConnectionException;

public class ReservationDaoFactory {

	private static ReservationDao dao;

	public static ReservationDao getInstance() throws DBConnectionException {
		if (dao == null) {
			try {
				EntityManager manager = DaoFactory.factory.createEntityManager();
				dao = new ReservationDao(manager);
			} catch (ServiceException e) {
				// Unable to connect to database
				throw new DBConnectionException(e);
			}
		}
		return dao;
	}

}
