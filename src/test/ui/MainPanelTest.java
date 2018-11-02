package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MainPanelTest {
	private final String MAIN_PANEL_LABEL = "Main";
	private final String MAIN_PANEL_RESERVE_BUTTON_TEXT = "Reserve a Room";
	private final String MAIN_PANEL_VIEW_ROOMS_BUTTON_TEXT = "View Reservations";
	
	private MainPanel mainPanel;
	private JPanel topPane,middlePane,bottomPane;
	
	@BeforeEach
	protected void onSetUp() {
		mainPanel = new MainPanel(null, null, null);
		topPane = (JPanel)mainPanel.getComponent(0);
		middlePane = (JPanel)mainPanel.getComponent(1); // 1: Search Button, 3ÅF View Rooms Button; 0, 2, and 4 are fillers
		bottomPane = (JPanel)mainPanel.getComponent(2);
	}
	
	@Test
	protected void checkTitle() {
		assertEquals(MAIN_PANEL_LABEL,((JLabel)topPane.getComponent(0)).getText());
	}
	
	@Test
	protected void middlePanelHasTwoButtons() {
		// reserveButton
		assertTrue(middlePane.getComponent(1) instanceof JButton);
		assertEquals(MAIN_PANEL_RESERVE_BUTTON_TEXT,((JButton)middlePane.getComponent(1)).getText());
		// viewRoomsButton
		assertTrue(middlePane.getComponent(3) instanceof JButton);
		assertEquals(MAIN_PANEL_VIEW_ROOMS_BUTTON_TEXT,((JButton)middlePane.getComponent(3)).getText());
	}

	@AfterEach
	protected void cleanUp() {
	}

}
