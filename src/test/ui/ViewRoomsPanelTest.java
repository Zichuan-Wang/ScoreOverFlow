package ui;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exception.DBConnectionException;
import utils.PanelTestUtils;


public class ViewRoomsPanelTest {
	private ViewRoomsPanel viewRoomsPane;
	private final String VIEW_ROOMS_PANEL_LABEL = "My Reservations";
	private JPanel topPane,middlePane,bottomPane;
	
	@BeforeEach
	protected void onSetUp() throws DBConnectionException {
		viewRoomsPane = PanelTestUtils.getViewRoomsPanel();
		topPane = (JPanel)viewRoomsPane.getComponent(0);
		middlePane = (JPanel)viewRoomsPane.getComponent(1);  // 0: JscrollPane with TablePanel inside 1:Back Button
		bottomPane = (JPanel)viewRoomsPane.getComponent(2);
	}
	
	@Test
	protected void checkTitle() {
		assertEquals(VIEW_ROOMS_PANEL_LABEL,((JLabel)topPane.getComponent(0)).getText());
	}
	
	@Test
	protected void middlePanelHasTablePanel() {
		assertTrue(middlePane.getComponent(0) instanceof JScrollPane);
	}
	
	@Test
	protected void middlePanelHasBackButton() {
		assertTrue(middlePane.getComponent(1) instanceof JButton);
		JButton backButton = (JButton) middlePane.getComponent(1);
		assertEquals("Back",backButton.getText());
	}
	

	@AfterEach
	protected void cleanUp() {
	}
}
