package ui;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;


public class TablePanel extends JPanel{
	final int MAX_LISTING = 10;
	
	public TablePanel() {
	}
	
	public void displayList(List<String> column, String action) {
		int width = Math.min(column.size(),MAX_LISTING);
		
		setLayout(new GridLayout(2,width,0,10));
		for (int i = 0; i < width; i++) {
			JLabel nameLabel = new JLabel(column.get(i));
	        add(nameLabel);
	        JButton actionButton = new JButton(action);
	        add(actionButton);
		}
		
	}
	
	public void clear() {
		removeAll();
		revalidate();
		repaint();
	}

}

