package exception;

import org.hibernate.service.spi.ServiceException;

public class DBConnectionException extends Exception {

	/**
	 * Default serial version id
	 */
	private static final long serialVersionUID = 1L;

	public DBConnectionException(ServiceException e) {
		super(e);
	}
}
