package ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import entity.User;
import security.LoginStatus;
import security.SecurityService;
import server.action.FacilityAction;
import server.action.ReservationAction;
import server.action.RoomAction;
import server.action.UserAction;

public class LoginPanel extends BasePanel {

	private static final long serialVersionUID = 1L;
	private final static String TITLE = "Login";
	private JButton loginButton;
	private JButton exitButton;
	private JTextField userNameField;
	private JPasswordField passwordField;
	private UserAction userAction;
	private ReservationAction reservationAction;
	private RoomAction roomAction;
	private FacilityAction facilityAction;

	public LoginPanel(JPanel cards, UserAction userAction, ReservationAction reservationAction, RoomAction roomAction,
			FacilityAction facilityAction) {
		super(TITLE, cards);
		initPanels();
		this.userAction = userAction;
		this.reservationAction = reservationAction;
		this.roomAction = roomAction;
		this.facilityAction = facilityAction;
	}

	// Partially based on Java Tutorials Code Sample – TextSamplerDemo.java
	@Override
	public JPanel getMiddlePanel() {
		// middle Panel
		JPanel middlePane = new JPanel();

		middlePane.setLayout(new BoxLayout(middlePane, BoxLayout.Y_AXIS));

		JLabel userNameLabel = new JLabel("User Name");
		userNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		userNameField = new JTextField();
		JLabel passwordLabel = new JLabel("Password");
		passwordField = new JPasswordField();
		passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		loginButton = GuiUtils.createButton("Login", e -> login());
		exitButton = GuiUtils.createButton("Exit", e -> System.exit(0));

		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();

		middlePane.setLayout(gridbag);

		JLabel[] labels = { userNameLabel, passwordLabel };
		JTextField[] textFields = { userNameField, passwordField };
		addLabelTextRows(labels, textFields, middlePane);

		c.gridwidth = GridBagConstraints.REMAINDER; // last
		c.anchor = GridBagConstraints.WEST;
		c.weightx = 1.0;

		middlePane.add(loginButton);
		middlePane.add(exitButton);

		return middlePane;

	}

	// From Java Tutorials Code Sample – TextSamplerDemo.java
	private void addLabelTextRows(JLabel[] labels, JTextField[] textFields, Container container) {
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.EAST;
		int numLabels = labels.length;

		for (int i = 0; i < numLabels; i++) {
			c.gridwidth = GridBagConstraints.RELATIVE; // next-to-last
			c.fill = GridBagConstraints.NONE; // reset to default
			c.weightx = 0.0; // reset to default
			container.add(labels[i], c);

			c.gridwidth = GridBagConstraints.REMAINDER; // end row
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1.0;
			container.add(textFields[i], c);
		}
	}

	private void login() {

		LoginStatus status = SecurityService.Login(userNameField.getText(),
				String.valueOf(passwordField.getPassword()));
		if (status.isSuccess()) {
			User user = userAction.findUserByUni(userNameField.getText());
			// Create three panels
			ReservePanel reservePane = new ReservePanel(cards, user, reservationAction, roomAction, facilityAction);
			ViewRoomsPanel viewRoomsPane = new ViewRoomsPanel(cards, user, reservationAction, roomAction);
			MainPanel mainPane = new MainPanel(cards, user, reservePane, viewRoomsPane);

			// add panels to card
			cards.add(mainPane, "main");
			cards.add(reservePane, "reserve");
			cards.add(viewRoomsPane, "view rooms");
			GuiUtils.jumpCard(cards, "main");
		} else {
			if (alert) {
				JOptionPane.showMessageDialog(null, status.getMessage());
			}
		}
	}

}
