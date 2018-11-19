package dao.factory;

import javax.persistence.EntityManager;

import org.hibernate.service.spi.ServiceException;

import dao.FacilityDao;
import exception.DBConnectionException;

public class FacilityDaoFactory {

	private static FacilityDao dao;

	public static FacilityDao getInstance() throws DBConnectionException {
		if (dao == null) {
			try {
				EntityManager manager = DaoFactory.factory.createEntityManager();
				dao = new FacilityDao(manager);
			} catch (ServiceException e) {
				// Unable to connect to database
				throw new DBConnectionException(e);
			}
		}
		return dao;
	}

}
