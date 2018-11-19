package ui;

import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.text.NumberFormatter;

public class GuiUtils {

	public static NumberFormatter getNumberFormatter(int minimum, int maximum) {
		NumberFormat integerFormat = NumberFormat.getIntegerInstance();
		integerFormat.setGroupingUsed(false);
		NumberFormatter integerFormatter = new NumberFormatter(integerFormat);
		integerFormatter.setValueClass(Integer.class);
		integerFormatter.setMinimum(minimum);
		integerFormatter.setMaximum(maximum);
		integerFormatter.setAllowsInvalid(false);
		return integerFormatter;
	}

	public static JButton createButton(String buttonName, ActionListener... actions) {
		JButton button = new JButton(buttonName);
		for (ActionListener action : actions) {
			button.addActionListener(action);
		}
		return button;
	}

	public static ActionListener getJumpCardActionListener(JPanel cards, String id) {
		return e -> {
			CardLayout cl = (CardLayout) cards.getLayout();
			cl.show(cards, id);
		};
	}

	public static JButton getBackButton(BasePanel pane, JPanel cards) {
		ActionListener initAction = e -> pane.reset();
		JButton backButton = createButton("Back", getJumpCardActionListener(cards, "main"), initAction);
		return backButton;
	}

}
