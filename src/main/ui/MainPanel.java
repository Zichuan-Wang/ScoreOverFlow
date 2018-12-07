package ui;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import entity.User;
import server.action.FacilityAction;
import server.action.ReservationAction;
import server.action.RoomAction;
import server.action.UserAction;

public class MainPanel extends BasePanel {
	private static final long serialVersionUID = 1L;
	private final static String TITLE = "Main";
	
	private JButton reserveButton;
	private JButton viewRoomsButton;
	private JButton manageButton;
	
	private User user;
	
	private UserAction userAction;
	private ReservationAction reservationAction;
	private RoomAction roomAction;
	private FacilityAction facilityAction;

	public MainPanel(JPanel rootPane, User user, UserAction userAction, ReservationAction reservationAction,
			RoomAction roomAction, FacilityAction facilityAction) {
		super(rootPane, TITLE);
		this.user = user;
		this.userAction = userAction;
		this.reservationAction = reservationAction;
		this.roomAction = roomAction;
		this.facilityAction = facilityAction;
		setMiddlePanel();
	}

	private void setMiddlePanel() {
		middlePane.setLayout(new BoxLayout(middlePane, BoxLayout.Y_AXIS));
		
		// create text
		JLabel welcomeMessage = new JLabel("Welcome back " + GuiUtils.userGroupToString(user)+ " " + user.getUni());
		welcomeMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
		middlePane.add(welcomeMessage);
		
		middlePane.add(Box.createVerticalGlue()); // beginning
		
		// add or edit room and facilities for admin
		if (user.getUserGroup() == 0) {
			ManagePanel managePane = new ManagePanel(rootPane, user, userAction, reservationAction, roomAction, facilityAction);
			rootPane.add(managePane,"manage");
			
			manageButton = GuiUtils.createButton("Manage Rooms and Facilities", e -> GuiUtils.jumpToPanel(rootPane, "manage"));
			manageButton.addActionListener( e -> managePane.pareparePanel());
			
			manageButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			middlePane.add(manageButton);
			
			middlePane.add(Box.createVerticalGlue());
		}
		
		// Reserve
		ReservePanel reservePane = new ReservePanel(rootPane, user, userAction, reservationAction, roomAction, facilityAction);
		rootPane.add(reservePane,"reserve");
		
		reserveButton = GuiUtils.createButton("Reserve a Room", e -> GuiUtils.jumpToPanel(rootPane, "reserve"));
		reserveButton.addActionListener( e -> reservePane.pareparePanel());
		
		reserveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		middlePane.add(reserveButton);

		middlePane.add(Box.createVerticalGlue());

		// View Reservations
		ViewRoomsPanel viewRoomsPane = new ViewRoomsPanel(rootPane, user, reservationAction, roomAction);
		rootPane.add(viewRoomsPane,"view rooms");
		
		viewRoomsButton = GuiUtils.createButton("View Reservations", e -> GuiUtils.jumpToPanel(rootPane, "view rooms"));
		viewRoomsButton.addActionListener( e -> viewRoomsPane.pareparePanel());
		
		viewRoomsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		middlePane.add(viewRoomsButton);
		
		middlePane.add(Box.createVerticalGlue()); // end
	}
	

}
