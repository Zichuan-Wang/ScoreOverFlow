package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;


public class ReservePanelTest {
	private ReservePanel reservePane;
	private final String RESERVE_PANEL_LABEL = "Reserve a Room";
	private JPanel topPane,middlePane,bottomPane;
	private LocalTime now;
	private int minuteDiff;
	
	@BeforeEach
	protected void onSetUp() {
		reservePane = new ReservePanel(null, null, null, null);
		topPane = (JPanel)reservePane.getComponent(0);
		middlePane = (JPanel)reservePane.getComponent(1); // 0: SearchPanel, 1: JscrollPane with TablePanel 2:Back Button
		bottomPane = (JPanel)reservePane.getComponent(2);
		now = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
		minuteDiff = now.getMinute()%10 == 0? 0: 10 - now.getMinute()%10;
	}
	
	@Test
	protected void checkTitle() {
		assertEquals(RESERVE_PANEL_LABEL,((JLabel)topPane.getComponent(0)).getText());
	}
	
	@Test
	protected void middlePanelHasSearchPanel() {
		assertTrue(middlePane.getComponent(0) instanceof JPanel);
		JPanel searchPane = (JPanel) middlePane.getComponent(0);
		// Date
		assertTrue(searchPane.getComponent(0) instanceof JLabel);
		assertEquals("Date",((JLabel)searchPane.getComponent(0)).getText());
		assertTrue(searchPane.getComponent(1) instanceof DatePicker);
		DatePicker datePicker = (DatePicker)searchPane.getComponent(1);
		assertEquals(datePicker.getDate(),LocalDate.now());
		//Time
		// Start Time
		assertTrue(searchPane.getComponent(2) instanceof JLabel);
		assertEquals("Start Time",((JLabel)searchPane.getComponent(2)).getText());
		assertTrue(searchPane.getComponent(3) instanceof TimePicker);
		TimePicker startTimePicker = (TimePicker)searchPane.getComponent(3);
		assertEquals(startTimePicker.getTime(),now.plusMinutes(minuteDiff));
		// End Time
		assertTrue(searchPane.getComponent(4) instanceof JLabel);
		assertEquals("End Time",((JLabel)searchPane.getComponent(4)).getText());
		assertTrue(searchPane.getComponent(5) instanceof TimePicker);
		TimePicker endTimePicker = (TimePicker)searchPane.getComponent(5);
		assertEquals(endTimePicker.getTime(),now.plusMinutes(minuteDiff+10));
		// Capacity
		assertTrue(searchPane.getComponent(6) instanceof JLabel);
		assertEquals("Capacity",((JLabel)searchPane.getComponent(6)).getText());
		assertTrue(searchPane.getComponent(7) instanceof JFormattedTextField);
		//JFormattedTextField capacity = (JFormattedTextField) searchPane.getComponent(7);
		
		// Name
		assertTrue(searchPane.getComponent(8) instanceof JLabel);
		assertEquals("Name",((JLabel)searchPane.getComponent(8)).getText());
		assertTrue(searchPane.getComponent(9) instanceof JTextField);
		//Search Button
		assertTrue(searchPane.getComponent(10) instanceof JButton);
		assertEquals("Search",((JButton)searchPane.getComponent(10)).getText());
	}
	
	@Test
	protected void middlePanelHasTablePanel() {
		assertTrue(middlePane.getComponent(1) instanceof JScrollPane);
	}
	
	@Test
	protected void middlePanelHasBackButton() {
		assertTrue(middlePane.getComponent(2) instanceof JButton);
		JButton backButton = (JButton) middlePane.getComponent(2);
		assertEquals("Back",backButton.getText());
	}
	

	@AfterEach
	protected void cleanUp() {
	}
}
