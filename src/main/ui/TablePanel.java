package ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class TablePanel extends JPanel {
	final int MAX_LISTING = 10;

	public TablePanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	// Print each line with a word from column and a button
	public void populateList(List<Object[]> rows) {
		int width = Math.min(rows.size(), MAX_LISTING);
		removeAll();
		for (int i = 0; i < width; i++) {
			JPanel linePane = new JPanel();
			linePane.setLayout(new BoxLayout(linePane, BoxLayout.X_AXIS));
			JLabel nameLabel = new JLabel((String) rows.get(i)[0]);
			JButton actionButton = (JButton) rows.get(i)[1];
			linePane.add(nameLabel);
			linePane.add(Box.createHorizontalGlue());
			linePane.add(actionButton);
			add(linePane);
		}
		revalidate();
		repaint();
	}

	public void reset() {
		removeAll();
		revalidate();
		repaint();
	}

}
