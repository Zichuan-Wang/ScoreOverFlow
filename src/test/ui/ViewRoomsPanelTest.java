package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exception.DBConnectionException;
import utils.PanelTestUtils;
import utils.UiTestUtils;

public class ViewRoomsPanelTest {
	private ViewRoomsPanel viewRoomsPane;
	private final String VIEW_ROOMS_PANEL_LABEL = "My Reservations";
	private JPanel topPane, middlePane;

	@BeforeEach
	protected void onSetUp() throws DBConnectionException {
		viewRoomsPane = PanelTestUtils.getViewRoomsPanel();
		viewRoomsPane.setAlert(false); // disable alert
		topPane = (JPanel) viewRoomsPane.getComponent(0);
		middlePane = (JPanel) viewRoomsPane.getComponent(1);
	}

	@Test
	protected void checkTitle() {
		assertEquals(VIEW_ROOMS_PANEL_LABEL, ((JLabel) topPane.getComponent(0)).getText());
	}

	@Test
	protected void middlePanelHasTablePanel() {
		assertTrue(middlePane.getComponent(0) instanceof TablePanel);
	}

	@Test
	protected void listGeneratedWithCorrectItems() {
		viewRoomsPane.showReservationList();
		List<JTable> objects = UiTestUtils.getObjects(middlePane, JTable.class);
		assertFalse(objects.isEmpty());
		JTable table = objects.get(0);
		assertEquals(table.getRowCount(), 4);
		// each row length 5, first 4 strings, last button
		assertTrue(table.getValueAt(0, 0) instanceof String);
		assertTrue(table.getValueAt(1, 4) instanceof JButton);
	}

	@Test
	protected void canCancelFromListGenerated() {
		viewRoomsPane.showReservationList();
		JTable table = (JTable) UiTestUtils.getObjects(middlePane, JTable.class).get(0);
		JButton cancelButton = (JButton) table.getValueAt(2, 4);
		System.out.println(cancelButton.getText());
		cancelButton.doClick();

		table = (JTable) UiTestUtils.getObjects(middlePane, JTable.class).get(0);
	}
	/*
	@Test
	protected void canOverridelFromListGenerated() throws DBConnectionException {
		viewRoomsPane = PanelTestUtils.getHighUserViewRoomsPanel();
		viewRoomsPane.showReservationList();
		JTable table = (JTable) UiTestUtils.getObjects(middlePane, JTable.class).get(0);
		JButton cancelButton = (JButton) table.getValueAt(2, 4);
		
		cancelButton.doClick();
		System.out.println(cancelButton.getText());

		table = (JTable) UiTestUtils.getObjects(middlePane, JTable.class).get(0);
	}
	*/
	
	protected void pareparePanelTest() {
		viewRoomsPane.pareparePanel();
		List<JTable> objects = UiTestUtils.getObjects(middlePane, JTable.class);
		assertFalse(objects.isEmpty());
	}

	@AfterEach
	protected void cleanUp() {
	}
}
