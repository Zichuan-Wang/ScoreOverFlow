package exception;

import org.hibernate.service.spi.ServiceException;

public class DBConnectionException extends Exception {

	public DBConnectionException(ServiceException e) {
		super(e);
	}}
