package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.CardLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exception.DBConnectionException;
import security.SecurityService;
import server.action.FacilityAction;
import server.action.UserAction;
import utils.EntityTestUtils;
import utils.FacilityActionTestUtils;
import utils.UserActionTestUtils;
import utils.UserDaoTestUtils;

public class LoginPanelTest {
	private final String LOGIN_PANEL_LABEL = "Login";
	private final String LOGIN_PANEL_LOGIN_BUTTON_TEXT = "Login";
	private final String MAIN_PANEL_EXIT_BUTTON_TEXT = "Exit";
	private final int CARDS_COMPONENT_INITIAL_COUNT = 1;
	private final int CARDS_COMPONENT_FINAL_COUNT = 4;

	private LoginPanel loginPane;
	private JPanel topPane, middlePane;
	private JPanel rootPane;

	private UserAction userAction;
	private FacilityAction facilityAction;

	@BeforeEach
	protected void onSetUp() {
		userAction = UserActionTestUtils.getUserAction();
		facilityAction = FacilityActionTestUtils.getFacilityAction();

		rootPane = new JPanel(new CardLayout());
		loginPane = new LoginPanel(rootPane, userAction, null, null, facilityAction);
		loginPane.setAlert(false);

		rootPane.add(loginPane);
		topPane = (JPanel) loginPane.getComponent(0);
		middlePane = (JPanel) loginPane.getComponent(1);

		// initialize security
		SecurityService.initialize(UserDaoTestUtils.getUserDao());
	}

	@Test
	protected void checkTitle() {
		assertEquals(LOGIN_PANEL_LABEL, ((JLabel) topPane.getComponent(0)).getText());
	}

	@Test
	protected void middlePaneHasLoginBox() {
		assertTrue(middlePane.getComponent(0) instanceof JPanel);
		JPanel loginBox = (JPanel) middlePane.getComponent(0);
		assertTrue(loginBox.getComponent(0) instanceof JLabel);
		assertTrue(loginBox.getComponent(1) instanceof JTextField);
		assertTrue(loginBox.getComponent(2) instanceof JLabel);
		assertTrue(loginBox.getComponent(3) instanceof JPasswordField);
		assertTrue(loginBox.getComponent(4) instanceof JButton);
		assertEquals(LOGIN_PANEL_LOGIN_BUTTON_TEXT, ((JButton) loginBox.getComponent(4)).getText());
		assertTrue(loginBox.getComponent(5) instanceof JButton);
		assertEquals(MAIN_PANEL_EXIT_BUTTON_TEXT, ((JButton) loginBox.getComponent(5)).getText());
	}

	@Test
	protected void checkLogin() throws DBConnectionException {
		assertEquals(rootPane.getComponents().length, CARDS_COMPONENT_INITIAL_COUNT);
		JPanel loginBox = (JPanel) middlePane.getComponent(0);
		JTextField userName = (JTextField) loginBox.getComponent(1);
		JPasswordField password = (JPasswordField) loginBox.getComponent(3);
		// Fail Login
		userName.setText("");
		password.setText("");
		JButton loginButton = (JButton) loginBox.getComponent(4);
		loginButton.doClick();
		assertEquals(CARDS_COMPONENT_INITIAL_COUNT, rootPane.getComponents().length);

		// Success Login
		userName.setText(EntityTestUtils.DEFAULT_UNI);
		password.setText(EntityTestUtils.DEFAULT_PASSWORD);
		loginButton.doClick();

		assertEquals(CARDS_COMPONENT_FINAL_COUNT, rootPane.getComponents().length);

	}

	@AfterEach
	protected void cleanUp() {
	}
}
