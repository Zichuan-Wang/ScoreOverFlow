package ui;

import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TablePanel extends JPanel {
	/**
	 * Default serial version id
	 */
	private static final long serialVersionUID = 1L;
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


}
