package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.CardLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dao.UserDao;
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

public class MainPanelTest {
	private final String MAIN_PANEL_LABEL = "Main";
	private final String MAIN_PANEL_MANAGE_BUTTON_TEXT = "Manage Rooms and Facilities";
	private final String MAIN_PANEL_RESERVE_BUTTON_TEXT = "Reserve a Room";
	private final String MAIN_PANEL_VIEW_ROOMS_BUTTON_TEXT = "View Reservations";
	private final String MAIN_PANEL_BATCH_BUTTON_TEXT = "Reserve Rooms in Batch";
	private final String WELCOME_MESSAGE_DEFAULT_USER = "Welcome back Normal User x";

	private final int ADMIN_BUTTON_COUNT = 3;
	private final int PS_BUTTON_COUNT = 3;
	private final int BUTTON_COUNT = 3; // search, batch, view panels

	private MainPanel mainPane;
	private JPanel topPane, middlePane;

	private UserDao dao;
	private User user;
	FacilityAction facilityAction;
	ReservationAction reservationAction;
	RoomAction roomAction;
	UserAction userAction;

	@BeforeEach
	protected void onSetUp() throws DBConnectionException {
		// create account to test
		JPanel rootPane = new JPanel(new CardLayout());
		dao = UserDaoTestUtils.getUserDao();
		user = dao.findById(EntityTestUtils.DEFAULT_USER_ID);
		facilityAction = FacilityActionTestUtils.getFacilityAction();
		reservationAction = ReservationActionTestUtils.getReservationAction();
		userAction = UserActionTestUtils.getUserAction();
		roomAction = RoomActionTestUtils.getRoomAction();
		mainPane = new MainPanel(rootPane, user, userAction, reservationAction, roomAction, facilityAction);
		topPane = (JPanel) mainPane.getComponent(0);
		middlePane = (JPanel) mainPane.getComponent(1);
		rootPane.add(mainPane, "main");

		// initialize security
		SecurityService.initialize(dao);
	}

	@Test
	protected void checkTitle() {
		assertEquals(MAIN_PANEL_LABEL, ((JLabel) topPane.getComponent(0)).getText());
	}

	@Test
	protected void welcomeMessageDisplaysCorrectly() {
		assertTrue(middlePane.getComponent(0) instanceof JLabel);
		assertEquals(WELCOME_MESSAGE_DEFAULT_USER, ((JLabel) middlePane.getComponent(0)).getText());
	}

	@Test
	protected void adminMiddlePanelHasCorrectButtons() {
		// Manage, Reserve, View Rooms
		JPanel rootPane = new JPanel(new CardLayout());
		user.setIsAdmin(true);
		user.setUserGroup(0);
		mainPane = new MainPanel(rootPane, user, userAction, reservationAction, roomAction, facilityAction);
		rootPane.add(mainPane, "main");
		middlePane = (JPanel) mainPane.getComponent(1);
		// check buttons
		List<JButton> buttons = UiTestUtils.getObjects(middlePane, JButton.class);
		assertEquals(ADMIN_BUTTON_COUNT, buttons.size());

		JButton manageButton = buttons.get(0);
		assertEquals(MAIN_PANEL_MANAGE_BUTTON_TEXT, manageButton.getText());
	}

	@Test
	protected void psMiddlePanelHasCorrectButtons() {
		// Reserve, Reserve Batch View, View Rooms
		JPanel rootPane = new JPanel(new CardLayout());
		user.setUserGroup(2);
		mainPane = new MainPanel(rootPane, user, userAction, reservationAction, roomAction, facilityAction);
		rootPane.add(mainPane, "main");
		middlePane = (JPanel) mainPane.getComponent(1);
		// check buttons
		List<JButton> buttons = UiTestUtils.getObjects(middlePane, JButton.class);
		assertEquals(PS_BUTTON_COUNT, buttons.size());

		JButton batchButton = buttons.get(1);
		assertEquals(MAIN_PANEL_BATCH_BUTTON_TEXT, batchButton.getText());
	}

	@Test
	protected void defaultMiddlePanelHasCorrectButtons() {
		// Reserve, View Rooms
		List<JButton> buttons = UiTestUtils.getObjects(middlePane, JButton.class);
		assertEquals(BUTTON_COUNT, buttons.size());
		JButton reserveButton = buttons.get(0);
		assertEquals(MAIN_PANEL_RESERVE_BUTTON_TEXT, reserveButton.getText());
		JButton viewRoomsButton = buttons.get(1);
		assertEquals(MAIN_PANEL_VIEW_ROOMS_BUTTON_TEXT, viewRoomsButton.getText());
	}

	@Test
	protected void reserveButtonClick() {
		JButton reserveButton = (JButton) UiTestUtils.getObjects(middlePane, JButton.class).get(0);
		reserveButton.doClick();
		assertFalse(mainPane.isVisible());
	}

	@Test
	protected void viewRoomsButtonClick() {
		JButton viewRoomsButton = (JButton) UiTestUtils.getObjects(middlePane, JButton.class).get(1);
		viewRoomsButton.doClick();
		assertFalse(mainPane.isVisible());
	}

	@Test
	protected void manageButtonClick() {
		JPanel rootPane = new JPanel(new CardLayout());
		user.setIsAdmin(true);
		user.setUserGroup(0);
		mainPane = new MainPanel(rootPane, user, userAction, reservationAction, roomAction, facilityAction);
		rootPane.add(mainPane, "main");
		middlePane = (JPanel) mainPane.getComponent(1);

		JButton manageButton = (JButton) UiTestUtils.getObjects(middlePane, JButton.class).get(0);
		manageButton.doClick();
		assertFalse(mainPane.isVisible());
	}

	@AfterEach
	protected void cleanUp() {
	}
}
