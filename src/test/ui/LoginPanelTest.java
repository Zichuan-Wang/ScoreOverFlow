package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.CardLayout;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import server.action.UserAction;
import utils.UserActionTestUtils;

public class LoginPanelTest {
	private final String LOGIN_PANEL_LABEL = "Login";
	private final String LOGIN_PANEL_LOGIN_BUTTON_TEXT = "Login";
	private final String MAIN_PANEL_EXIT_BUTTON_TEXT = "Exit";

	private JPanel cards;
	private LoginPanel loginPane;
	private JPanel topPane, middlePane;

	@BeforeEach
	protected void onSetUp() {
		loginPane = new LoginPanel(null, null, null, null, null);
		topPane = (JPanel) loginPane.getComponent(0);
		middlePane = (JPanel) loginPane.getComponent(1);
	}

	@Test
	protected void checkTitle() {
		assertEquals(LOGIN_PANEL_LABEL, ((JLabel) topPane.getComponent(0)).getText());
	}
	
	@Test
	protected void checkMiddlePane() {
		assertTrue(middlePane.getComponent(0) instanceof JLabel);
		assertTrue(middlePane.getComponent(1) instanceof JTextField);
		assertTrue(middlePane.getComponent(2) instanceof JLabel);
		assertTrue(middlePane.getComponent(3) instanceof JPasswordField);
		assertTrue(middlePane.getComponent(4) instanceof JButton);
		assertTrue(middlePane.getComponent(5) instanceof JButton);
	}
	

	@Test
	protected void checkLogin() {
		JTextField userName = (JTextField) middlePane.getComponent(1);
		JPasswordField password = (JPasswordField) middlePane.getComponent(3);
		userName.setText("");
		password.setText("");
		JButton loginButton = (JButton) middlePane.getComponent(4);
		// failed to load class for login
	}
	
	@Test
	protected void middlePanelHasTwoButtons() {
		// reserveButton
		assertTrue(middlePane.getComponent(4) instanceof JButton);
		assertEquals(LOGIN_PANEL_LOGIN_BUTTON_TEXT, ((JButton) middlePane.getComponent(4)).getText());
		// viewRoomsButton
		assertTrue(middlePane.getComponent(5) instanceof JButton);
		assertEquals(MAIN_PANEL_EXIT_BUTTON_TEXT, ((JButton) middlePane.getComponent(5)).getText());
	}
}
