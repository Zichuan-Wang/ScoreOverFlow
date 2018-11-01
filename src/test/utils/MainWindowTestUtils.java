package utils;

import dao.factory.UserDaoFactory;
import entity.User;
import exception.DBConnectionException;
import ui.MainWindow;

public class MainWindowTestUtils {

	public static MainWindow getMainWindow() {
		User user = null;
		try {
			user = UserDaoFactory.getInstance().findById(1);
		} catch (DBConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new MainWindow(user);
	}

}
