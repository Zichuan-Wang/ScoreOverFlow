package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class BasePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	protected boolean alert = true; // For disabling message box

	protected JPanel rootPane;
	protected JPanel topPane;
	protected JPanel middlePane;
	protected JPanel bottomPane;

	public BasePanel(JPanel rootPane) {
		this.rootPane = rootPane;
		topPane = new JPanel();
		middlePane = new JPanel();
		bottomPane = new JPanel();
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.BOTH;
		c.ipady = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		add(topPane, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		c.ipady = 0;
		middlePane.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		add(middlePane, c);

		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 3;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.BOTH;
		c.ipady = 0;
		add(bottomPane, c);
	}

	public BasePanel(JPanel rootPane, String title) {
		this(rootPane);
		JLabel titleLabel = new JLabel(title);
		topPane.add(titleLabel);
	}

	public JPanel getTopPane() {
		return topPane;
	}

	public JPanel getMiddlePane() {
		return middlePane;
	}

	public JPanel getBottomPane() {
		return bottomPane;
	}

	public void setAlert(boolean changedAlertState) {
		alert = changedAlertState;
	}

	// things to do when switching to this panel
	public void pareparePanel() {
	}

	// things to do when quitting this panel
	public void exitPanel() {
	}
}
