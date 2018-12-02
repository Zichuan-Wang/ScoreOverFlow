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

import security.SecurityService;
import utils.EntityTestUtils;
import utils.FacilityActionTestUtils;
import utils.UserActionTestUtils;
import utils.UserDaoTestUtils;

public class LoginPanelTest {
	private final String LOGIN_PANEL_LABEL = "Login";
	private final String LOGIN_PANEL_LOGIN_BUTTON_TEXT = "Login";
	private final String MAIN_PANEL_EXIT_BUTTON_TEXT = "Exit";
	private final int CARDS_COMPONENT_INITIAL_COUNT = 1;
	//private final int CARDS_COMPONENT_FINAL_COUNT = 4;

	private LoginPanel loginPane;
	private JPanel topPane, middlePane;
	private JPanel cards;

	@BeforeEach
	protected void onSetUp() {
		cards = new JPanel(new CardLayout());
		loginPane = new LoginPanel(cards, UserActionTestUtils.getUserAction(), null, null, FacilityActionTestUtils.getFacilityAction());
		cards.add(loginPane);
		topPane = (JPanel) loginPane.getComponent(0);
		middlePane = (JPanel) loginPane.getComponent(1);
		loginPane.setAlert(false);
		// initialize security
		SecurityService.initialize(UserDaoTestUtils.getUserDao());
	}

	@Test
	protected void checkTitle() {
		assertEquals(LOGIN_PANEL_LABEL, ((JLabel) topPane.getComponent(0)).getText());
	}

	@Test
	protected void middlePaneHasComponents() {
		assertTrue(middlePane.getComponent(0) instanceof JLabel);
		assertTrue(middlePane.getComponent(1) instanceof JTextField);
		assertTrue(middlePane.getComponent(2) instanceof JLabel);
		assertTrue(middlePane.getComponent(3) instanceof JPasswordField);
		assertTrue(middlePane.getComponent(4) instanceof JButton);
		assertTrue(middlePane.getComponent(5) instanceof JButton);
	}

	@Test
	protected void checkLogin() {
		assertEquals(cards.getComponents().length, CARDS_COMPONENT_INITIAL_COUNT);
		JTextField userName = (JTextField) middlePane.getComponent(1);
		JPasswordField password = (JPasswordField) middlePane.getComponent(3);

		// Fail Login
		userName.setText("");
		password.setText("");
		JButton loginButton = (JButton) middlePane.getComponent(4);
		loginButton.doClick();
		assertEquals(CARDS_COMPONENT_INITIAL_COUNT, cards.getComponents().length);

		// Success Login
		userName.setText(EntityTestUtils.DEFAULT_UNI);
		password.setText(EntityTestUtils.DEFAULT_PASSWORD);
		//System.out.println(userName.getText());
		//System.out.println(password.getPassword());
		loginButton.doClick();
		//assertEquals(CARDS_COMPONENT_FINAL_COUNT, cards.getComponents().length);

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

	@AfterEach
	protected void cleanUp() {
	}
}
