package ui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import entity.Reservation;
import entity.User;
import security.LoginStatus;
import security.SecurityService;
import server.action.FacilityAction;
import server.action.ReservationAction;
import server.action.RoomAction;
import server.action.UserAction;
import server.constraint.SearchReservationConstraint;

public class LoginPanel extends BasePanel {

	private static final long serialVersionUID = 1L;
	private final static String TITLE = "Login";

	private UserAction userAction;
	private ReservationAction reservationAction;
	private RoomAction roomAction;
	private FacilityAction facilityAction;

	private JButton loginButton;
	private JButton exitButton;
	private JTextField userNameField;
	private JPasswordField passwordField;

	public LoginPanel(JPanel rootPane, UserAction userAction, ReservationAction reservationAction,
			RoomAction roomAction, FacilityAction facilityAction) {
		super(rootPane, TITLE);
		this.userAction = userAction;
		this.reservationAction = reservationAction;
		this.roomAction = roomAction;
		this.facilityAction = facilityAction;
		setMiddlePanel();
	}

	private void setMiddlePanel() {
		middlePane.setLayout(new GridBagLayout());

		JPanel loginBox = new JPanel();
		GroupLayout layout = new GroupLayout(loginBox);
		loginBox.setLayout(layout);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);

		// user name
		JLabel userNameLabel = new JLabel("Username");
		userNameField = new JTextField();
		// password
		JLabel passwordLabel = new JLabel("Password");
		passwordField = new JPasswordField();
		// buttons
		loginButton = GuiUtils.createButton("Login", e -> login());
		exitButton = GuiUtils.createButton("Exit", e -> System.exit(0));

		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.CENTER).addComponent(userNameLabel)
				.addGroup(layout.createParallelGroup(Alignment.CENTER).addComponent(userNameField))
				.addComponent(passwordLabel).addComponent(passwordField)
				.addGroup(layout.createSequentialGroup().addComponent(loginButton).addComponent(exitButton)));

		layout.setVerticalGroup(layout.createParallelGroup(Alignment.CENTER).addGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(userNameLabel))
				.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(userNameField))
				.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(passwordLabel))
				.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(passwordField)).addGroup(layout
						.createParallelGroup(Alignment.BASELINE).addComponent(loginButton).addComponent(exitButton))));

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		middlePane.add(loginBox, c);

	}

	private void login() {
		LoginStatus status = SecurityService.Login(userNameField.getText(),
				String.valueOf(passwordField.getPassword()));
		if (status.isSuccess()) {
			User user = userAction.findUserByUni(userNameField.getText());
			
			// Create three panels
			MainPanel mainPane = new MainPanel(rootPane, user, userAction, reservationAction, roomAction,
					facilityAction);
			rootPane.add(mainPane, "main");
			userNameField.setText("");
			passwordField.setText("");
			GuiUtils.jumpToPanel(rootPane, "main");
			
			SearchReservationConstraint src = new SearchReservationConstraint();
			src.setUserId(user.getId());
			
			if (reservationAction != null) {
				List<Reservation> overridenReservationList = reservationAction.searchOverridenReservations(src);
				
				
				if (alert && overridenReservationList.size() != 0) {
					//JOptionPane.showMessageDialog(null, "Welcome");
					int response = JOptionPane.showConfirmDialog(
						    null,
						    "Unfortunately one or more of your reservations is(are) overriden.\nDo you want to find replacement room(s) now?",
						    "Reservation overriden",
						    JOptionPane.YES_NO_OPTION);
					if (response == 0) {
						GuiUtils.jumpToPanel(rootPane, "view rooms");
						Component[] components = rootPane.getComponents();
						for (Component c : components) {
							if(c instanceof ViewRoomsPanel) {
								ViewRoomsPanel panel = (ViewRoomsPanel) c;
								panel.pareparePanel();
								break;
							}
						}
						
					}
					
				}
			}	
			
			
		} else {
			if (alert) {
				JOptionPane.showMessageDialog(null, status.getMessage());
			}
			if (status.getMessage().equals("Unknown account")) {
				userNameField.setText("");
			}
			passwordField.setText("");
		}
	}

}
