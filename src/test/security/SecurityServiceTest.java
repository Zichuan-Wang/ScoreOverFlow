package security;

import org.apache.shiro.SecurityUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dao.UserDao;
import dao.factory.UserDaoFactory;
import entity.User;
import exception.DBConnectionException;

public class SecurityServiceTest {
	@BeforeEach
	public void onSetUp() throws DBConnectionException {
		SecurityService.initialize(UserDaoFactory.getInstance());
	}

	@Test
	public void hasSubject() {
		SecurityUtils.getSubject();
	}

	@Test
	public void canLogin() throws DBConnectionException {
		UserDao dao = UserDaoFactory.getInstance();
		dao.saveOrUpdate(new User().setUni("test").setPassword(PasswordHashing.getHash("123")));
	}

	@AfterEach
	public void cleanUp() {
	}

}
