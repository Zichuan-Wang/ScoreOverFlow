package dao.factory;

import javax.persistence.EntityManager;

import org.hibernate.service.spi.ServiceException;

import dao.DaoFactory;
import dao.UserDao;
import exception.DBConnectionException;

public class UserDaoFactory {

	private static UserDao dao;

	public static UserDao getInstance() throws DBConnectionException {
		if (dao == null) {
			try {
				EntityManager manager = DaoFactory.factory.createEntityManager();
				dao = new UserDao(manager);
			} catch (ServiceException e) {
				// Unable to connect to database
				throw new DBConnectionException(e);
			}
		}
		return dao;
	}

}
