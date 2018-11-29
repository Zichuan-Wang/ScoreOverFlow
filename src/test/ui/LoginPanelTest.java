package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoginPanelTest {
	private final String LOGIN_PANEL_LABEL = "Login";
	private final String LOGIN_PANEL_LOGIN_BUTTON_TEXT = "Login";
	private final String MAIN_PANEL_EXIT_BUTTON_TEXT = "Exit";

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
	protected void middlePanelHasTwoButtons() {
		// reserveButton
		assertTrue(middlePane.getComponent(4) instanceof JButton);
		assertEquals(LOGIN_PANEL_LOGIN_BUTTON_TEXT, ((JButton) middlePane.getComponent(4)).getText());
		// viewRoomsButton
		assertTrue(middlePane.getComponent(5) instanceof JButton);
		assertEquals(MAIN_PANEL_EXIT_BUTTON_TEXT, ((JButton) middlePane.getComponent(5)).getText());
	}
}
