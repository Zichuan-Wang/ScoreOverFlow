package ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import security.SecurityService;

public class LoginPanel extends BasePanel{
	
	private static final long serialVersionUID = 1L;
	private final static String TITLE = "Login";
	private JButton loginButton;
	private JButton exitButton;
	private JTextField userNameField;
	private JPasswordField passwordField;

	public LoginPanel(JPanel cards, MainPanel mainPane) {
		super(TITLE, cards);
		initPanels();
		loginButton.addActionListener(e -> mainPane.showPanel()); //TODO
		loginButton.addActionListener(e -> {
			try {
				System.out.println(SecurityService.Login("hx2209", "123456"));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
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
		
		loginButton = GuiUtils.createButton("Login", GuiUtils.getJumpCardActionListener(cards, "main")); //TODO change to real action
		exitButton = GuiUtils.createButton("Exit", e -> System.exit(0));
		
		
	    GridBagLayout gridbag = new GridBagLayout();
	    GridBagConstraints c = new GridBagConstraints();
	 
	    middlePane.setLayout(gridbag);
	 
	    JLabel[] labels = {userNameLabel, passwordLabel};
	    JTextField[] textFields = {userNameField, passwordField};
	    addLabelTextRows(labels, textFields, middlePane);
	 
	    c.gridwidth = GridBagConstraints.REMAINDER; //last
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
			c.gridwidth = GridBagConstraints.RELATIVE; //next-to-last
			c.fill = GridBagConstraints.NONE;      //reset to default
			c.weightx = 0.0;                       //reset to default
			container.add(labels[i], c);
			
			c.gridwidth = GridBagConstraints.REMAINDER;     //end row
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1.0;
			container.add(textFields[i], c);
		}
	}

}
