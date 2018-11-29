package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;


import exception.DBConnectionException;
import utils.PanelTestUtils;
import utils.UiTestUtils;

public class ReservePanelTest {
	private ReservePanel reservePane;
	private final String RESERVE_PANEL_LABEL = "Reserve a Room";
	private JPanel topPane, middlePane;
	private LocalTime now;
	private int minuteDiff;

	@BeforeEach
	protected void onSetUp() throws DBConnectionException {
		reservePane = PanelTestUtils.getReservePanel();
		reservePane.setAlert(false);
		topPane = (JPanel) reservePane.getComponent(0);
		middlePane = (JPanel) reservePane.getComponent(1); // 0: SearchPanel, 1: JscrollPane with TablePanel 2:Back
															// Button
		now = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
		minuteDiff = now.getMinute() % 10 == 0 ? 0 : 10 - now.getMinute() % 10;
	}

	@Test
	protected void checkTitle() {
		assertEquals(RESERVE_PANEL_LABEL, ((JLabel) topPane.getComponent(0)).getText());
	}

	@Test
	protected void middlePanelHasSearchPanel() {
		assertTrue(middlePane.getComponent(0) instanceof JPanel);
		JPanel searchPane = (JPanel) middlePane.getComponent(0);
		// Date
		assertTrue(searchPane.getComponent(0) instanceof JLabel);
		assertEquals("Date", ((JLabel) searchPane.getComponent(0)).getText());
		assertTrue(searchPane.getComponent(1) instanceof DatePicker);
		DatePicker datePicker = (DatePicker) searchPane.getComponent(1);
		assertEquals(datePicker.getDate(), LocalDate.now());
		// Time
		// Start Time
		assertTrue(searchPane.getComponent(2) instanceof JLabel);
		assertEquals("Start Time", ((JLabel) searchPane.getComponent(2)).getText());
		assertTrue(searchPane.getComponent(3) instanceof TimePicker);
		TimePicker startTimePicker = (TimePicker) searchPane.getComponent(3);
		assertEquals(startTimePicker.getTime(), now.plusMinutes(minuteDiff));
		// End Time
		assertTrue(searchPane.getComponent(4) instanceof JLabel);
		assertEquals("End Time", ((JLabel) searchPane.getComponent(4)).getText());
		assertTrue(searchPane.getComponent(5) instanceof TimePicker);
		TimePicker endTimePicker = (TimePicker) searchPane.getComponent(5);
		assertEquals(endTimePicker.getTime(), now.plusMinutes(minuteDiff + 10));
		// Capacity
		assertTrue(searchPane.getComponent(6) instanceof JLabel);
		assertEquals("Capacity", ((JLabel) searchPane.getComponent(6)).getText());
		assertTrue(searchPane.getComponent(7) instanceof JFormattedTextField);
		// JFormattedTextField capacity = (JFormattedTextField)
		// searchPane.getComponent(7);

		// Name
		assertTrue(searchPane.getComponent(8) instanceof JLabel);
		assertEquals("Name", ((JLabel) searchPane.getComponent(8)).getText());
		assertTrue(searchPane.getComponent(9) instanceof JTextField);

		// Facility
		assertTrue(searchPane.getComponent(10) instanceof JLabel);
		assertEquals("Facility", ((JLabel) searchPane.getComponent(10)).getText());
		assertTrue(searchPane.getComponent(11) instanceof JList<?>);

		// Search Button
		assertTrue(searchPane.getComponent(12) instanceof JButton);
		assertEquals("Search", ((JButton) searchPane.getComponent(12)).getText());
	}

	@Test
	protected void middlePanelHasTablePanel() {
		assertTrue(middlePane.getComponent(1) instanceof JScrollPane);
	}

	@Test
	protected void middlePanelHasBackButton() {
		assertTrue(middlePane.getComponent(2) instanceof JButton);
		JButton backButton = (JButton) middlePane.getComponent(2);
		assertEquals("Back", backButton.getText());
	}

	
	@Test
	protected void searchButtonWorking() {
		JPanel searchPane = (JPanel) middlePane.getComponent(0);
		JButton searchButton = (JButton) searchPane.getComponent(12);
		searchButton.doClick();
		List<Object> tables = UiTestUtils.getObjects(middlePane,JTable.class);
		assertFalse(tables.isEmpty());
	}
	
	@Test
	protected void reserveButtonWorking() {
		JPanel searchPane = (JPanel) middlePane.getComponent(0);
		JButton searchButton = (JButton) searchPane.getComponent(12);
		searchButton.doClick();
		JTable table = (JTable) UiTestUtils.getObjects(middlePane,JTable.class).get(0);
		JButton reserveButton = (JButton) table.getValueAt(0,1);
		assertNotNull(reserveButton);
		assertTrue(reserveButton.isEnabled());
		reserveButton.doClick();
		assertFalse(reserveButton.isEnabled());
	}
	
	@Test
	protected void overrideButtonWorking() throws DBConnectionException{
		reservePane = PanelTestUtils.getHighUserReservePanel();
		middlePane = (JPanel) reservePane.getComponent(1);
		JPanel searchPane = (JPanel) middlePane.getComponent(0);
		JButton searchButton = (JButton) searchPane.getComponent(13);
		searchButton.doClick();
		JTable table = (JTable) UiTestUtils.getObjects(middlePane,JTable.class).get(0);
		//no override button
	}
	
	@Test
	protected void resetWorking() {
		JPanel searchPane = (JPanel) middlePane.getComponent(0);
		DatePicker datePicker = (DatePicker)searchPane.getComponent(1);
		LocalDate optionalDate = LocalDate.of(1966, 6, 6);
		datePicker.setDate(optionalDate);
		reservePane.reset();
		assertTrue(UiTestUtils.getObjects(middlePane,JTable.class).isEmpty()); // No table
		assertNotEquals(optionalDate,datePicker.getDate());
	}

	@AfterEach
	protected void cleanUp() {
	}
}
