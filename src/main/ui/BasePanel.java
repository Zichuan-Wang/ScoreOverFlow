package ui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BasePanel extends JPanel{
	public BasePanel(String title, JPanel middlePane) {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
	    
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
	    
	    add(upperPane,c);
	    
	    // middle pane uses JPanel from other classes
	    c.gridx = 0;
	    c.gridy = 1;
	    c.gridwidth = 3;
	    c.weightx = 1.0;
	    c.weighty = 1.0;
	    c.fill = GridBagConstraints.BOTH;
	    c.ipady = 0;
	    middlePane.setBorder(BorderFactory.createLineBorder(Color.black));
	    add(middlePane,c);
	    
	    JPanel lowerPane = new JPanel();
	    JLabel emptyLabel = new JLabel(" ");
	    lowerPane.add(emptyLabel);
	    c.gridx = 0;
	    c.gridy = 2;
	    c.gridwidth = 3;
	    c.weightx = 1.0;
	    c.weighty = 0.0;
	    c.fill = GridBagConstraints.BOTH;
	    c.ipady=0;
	    add(lowerPane,c);
	}
}
