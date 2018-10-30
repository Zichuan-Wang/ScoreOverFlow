package ui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BasePanel extends JPanel {
	private GridBagConstraints c;
	protected JPanel cards;
	
	// Initialize a page without the middle panel
	public BasePanel(String title, JPanel cards) {
		this.cards = cards;
		
		setLayout(new GridBagLayout());
		c = new GridBagConstraints();

		JPanel upperPane = new JPanel();
		JLabel titleLabel = new JLabel(title);
		upperPane.add(titleLabel);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.BOTH;
		c.ipady = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;

		add(upperPane, c);
		
		JPanel middlePane = getMiddlePanel();
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		c.ipady = 10;
		middlePane.setBorder(BorderFactory.createLineBorder(Color.black));
		add(middlePane, c);
		
		
		JPanel lowerPane = new JPanel();
		JLabel emptyLabel = new JLabel(" ");
		lowerPane.add(emptyLabel);
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 3;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.BOTH;
		c.ipady = 0;
		add(lowerPane, c);
	}

	/*
	protected void getMiddlePanel(JPanel middlePane) {
		// middle pane uses JPanel from other classes
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		c.ipady = 10;
		middlePane.setBorder(BorderFactory.createLineBorder(Color.black));
		add(middlePane, c);
	}
	*/
	
	public JPanel getMiddlePanel() {
		return new JPanel();
	}
	
	public void reset() {
		removeAll();
		revalidate();
		repaint();
	}

	public void showPanel() {
		// TODO Auto-generated method stub
		System.out.println("show");
	}
}
