package test;

import dao.UserDAO;
import entity.User;

public class DAOTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			UserDAO dao = new UserDAO();
			User user = dao.findUserByUni("hx2209");
			System.out.print(user.getEmail());
		} catch (Exception exception) {
			
		}
	}

}
