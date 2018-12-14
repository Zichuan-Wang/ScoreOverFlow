package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;

import entity.Facility;
import entity.User;
import exception.DBConnectionException;
import security.SecurityService;
import server.action.FacilityAction;
import server.action.ReservationAction;
import server.action.RoomAction;
import server.action.UserAction;
import utils.EntityTestUtils;
import utils.FacilityActionTestUtils;
import utils.ReservationActionTestUtils;
import utils.RoomActionTestUtils;
import utils.UiTestUtils;
import utils.UserActionTestUtils;
import utils.UserDaoTestUtils;

public class ReservePanelTest {

	private ReservePanel reservePane;
	private final String RESERVE_PANEL_LABEL = "Reserve a Room";
	private JPanel topPane, middlePane;
	private LocalTime now;
	private User user;
	private int minuteDiff;

	private UserAction userAction;
	private ReservationAction reservationAction;
	private RoomAction roomAction;
	private FacilityAction facilityAction;

	@BeforeEach
	protected void onSetUp() throws DBConnectionException {
		// initialize security
		SecurityService.initialize(UserDaoTestUtils.getUserDao());
		// initialize action
		userAction = UserActionTestUtils.getUserAction();
		reservationAction = ReservationActionTestUtils.getReservationAction();
		roomAction = RoomActionTestUtils.getRoomAction();
		facilityAction = FacilityActionTestUtils.getFacilityAction();
		// create user and reservePanel
		user = userAction.findUserById(EntityTestUtils.DEFAULT_USER_ID);
		reservePane = new ReservePanel(null, user, userAction, reservationAction, roomAction, facilityAction);
		reservePane.setAlert(false);
		topPane = (JPanel) reservePane.getComponent(0);
		middlePane = (JPanel) reservePane.getComponent(1);
		// set now
		now = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
		minuteDiff = now.getMinute() % 10 == 0 ? 0 : 10 - now.getMinute() % 10;
		now = now.plusMinutes(minuteDiff);

	}

	@Test
	protected void checkTitle() {
		assertEquals(RESERVE_PANEL_LABEL, ((JLabel) topPane.getComponent(0)).getText());
	}

	@Test
	protected void normalUserSearchPanelDisplayCorrectly() {
		assertTrue(middlePane.getComponent(0) instanceof JPanel);
		JPanel searchPane = (JPanel) middlePane.getComponent(0);
		// Date
		assertTrue(searchPane.getComponent(0) instanceof JLabel);
		assertEquals("Date", ((JLabel) searchPane.getComponent(0)).getText());
		assertTrue(searchPane.getComponent(1) instanceof DatePicker);
		DatePicker datePicker = (DatePicker) searchPane.getComponent(1);
		assertEquals(LocalDate.now(), datePicker.getDate());
		// Time
		// Start Time
		assertTrue(searchPane.getComponent(2) instanceof JLabel);
		assertEquals("Start Time", ((JLabel) searchPane.getComponent(2)).getText());
		assertTrue(searchPane.getComponent(3) instanceof TimePicker);
		TimePicker startTimePicker = (TimePicker) searchPane.getComponent(3);
		assertEquals(now, startTimePicker.getTime());
		// End Time
		assertTrue(searchPane.getComponent(4) instanceof JLabel);
		assertEquals("End Time", ((JLabel) searchPane.getComponent(4)).getText());
		assertTrue(searchPane.getComponent(5) instanceof TimePicker);
		TimePicker endTimePicker = (TimePicker) searchPane.getComponent(5);
		assertEquals(now.plusMinutes(10), endTimePicker.getTime());
		// Capacity
		assertTrue(searchPane.getComponent(6) instanceof JLabel);
		assertEquals("Capacity", ((JLabel) searchPane.getComponent(6)).getText());
		assertTrue(searchPane.getComponent(7) instanceof JTextField);

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
	protected void highUserSearchPanelDisplayCorrectly() throws DBConnectionException {
		user.setUserGroup(EntityTestUtils.HIGH_USER_GROUP);
		reservePane = new ReservePanel(null, user, userAction, reservationAction, roomAction, facilityAction);
		reservePane.setAlert(false);
		topPane = (JPanel) reservePane.getComponent(0);
		middlePane = (JPanel) reservePane.getComponent(1);
		JPanel searchPane = (JPanel) middlePane.getComponent(0);
		// previous ones are the same as normal user, ignoring here
		// Check Box
		assertTrue(searchPane.getComponent(12) instanceof JLabel);
		assertEquals("Show Overridable Rooms", ((JLabel) searchPane.getComponent(12)).getText());
		assertTrue(searchPane.getComponent(13) instanceof JCheckBox);
		// Search Button
		assertTrue(searchPane.getComponent(14) instanceof JButton);
		assertEquals("Search", ((JButton) searchPane.getComponent(14)).getText());
	}

	@Test
	protected void middlePanelHasTablePanel() {
		assertTrue(middlePane.getComponent(1) instanceof TablePanel);
	}

	@Test
	protected void searchButtonWorking() {
		JPanel searchPane = (JPanel) middlePane.getComponent(0);
		JButton searchButton = (JButton) searchPane.getComponent(12);
		searchButton.doClick();
		List<JTable> tables = UiTestUtils.getObjects(middlePane, JTable.class);
		assertFalse(tables.isEmpty());
	}

	@Test
	void overrideButtonWorking() throws DBConnectionException {
		// change to high user and find override
		user = user.setUserGroup(EntityTestUtils.HIGH_USER_GROUP);
		reservePane = new ReservePanel(null, user, userAction, reservationAction, roomAction, facilityAction);
		reservePane.setAlert(false);
		topPane = (JPanel) reservePane.getComponent(0);
		middlePane = (JPanel) reservePane.getComponent(1);

		JPanel searchPane = (JPanel) middlePane.getComponent(0);
		// check box to do override
		((JCheckBox) searchPane.getComponent(13)).setSelected(true);
		;

		JButton searchButton = (JButton) searchPane.getComponent(14);
		searchButton.doClick();

		TablePanel tablePane = (TablePanel) middlePane.getComponent(1);
		JScrollPane scrollPane = (JScrollPane) tablePane.getComponent(0);
		JViewport viewport = scrollPane.getViewport();
		JTable table = (JTable) viewport.getView();
		assertNotNull(table);
		JButton overrideButton = (JButton) table.getValueAt(1, 3);
		assertEquals("Override", overrideButton.getText());
		overrideButton.doClick();
		assertFalse(overrideButton.isEnabled());
	}

	@Test
	protected void reserveButtonWorking() {
		JPanel searchPane = (JPanel) middlePane.getComponent(0);
		JButton searchButton = (JButton) searchPane.getComponent(12);
		searchButton.doClick();
		JTable table = (JTable) UiTestUtils.getObjects(middlePane, JTable.class).get(0);
		assertTrue(table.getRowCount() != 0);
		// Room name, Capacities, Facilities, Reserve button
		assertTrue(table.getValueAt(0, 0) instanceof String);
		assertEquals(EntityTestUtils.DEFAULT_ROOM_NAME, table.getValueAt(0, 0));
		assertTrue(table.getValueAt(0, 1) instanceof Integer);
		assertEquals(EntityTestUtils.DEFAULT_CAPACITY, table.getValueAt(0, 1));
		assertTrue(table.getValueAt(0, 2) instanceof String);
		assertEquals("", table.getValueAt(0, 2));
		JButton reserveButton = (JButton) table.getValueAt(0, 3);
		assertNotNull(reserveButton);
		assertTrue(reserveButton.isEnabled());
		// reserve
		reserveButton.doClick();
		assertFalse(reserveButton.isEnabled());
	}

	@Test
	protected void exitPanelTest() {
		reservePane.exitPanel();
		JPanel searchPane = (JPanel) ((JPanel) reservePane.getComponent(1)).getComponent(0);
		LocalDate today = LocalDate.now();
		DatePicker datePicker = (DatePicker) searchPane.getComponent(1);
		assertEquals(today, datePicker.getDate());
		// Time
		// Start Time
		TimePicker startTimePicker = (TimePicker) searchPane.getComponent(3);
		assertEquals(now, startTimePicker.getTime());
		// End Time
		TimePicker endTimePicker = (TimePicker) searchPane.getComponent(5);
		assertEquals(now.plusMinutes(10), endTimePicker.getTime());
		// Capacity
		JTextField capacity = (JTextField) searchPane.getComponent(7);
		assertEquals("", capacity.getText());

		// Name
		JTextField name = (JTextField) searchPane.getComponent(9);
		assertEquals("", name.getText());

		// Facility
		@SuppressWarnings("unchecked")
		JList<Facility> facilityList = (JList<Facility>) searchPane.getComponent(11);
		assertEquals(0, facilityList.getSelectedValuesList().size());
	}

	@AfterEach
	protected void cleanUp() {
	}
}
