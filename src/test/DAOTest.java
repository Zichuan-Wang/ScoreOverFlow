package test;

import dao.UserDAO;
import entity.User;

public class DAOTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UserDAO dao = new UserDAO();
		User user = dao.findUserByUNI("hx2209");
		System.out.print(user.getEmail());
	}

}
