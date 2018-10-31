package ui;

import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.text.NumberFormatter;

public class GuiUtils {

	public static NumberFormatter getNumberFormatter(int minimum, int maximum) {
		NumberFormatter numberFormatter = new NumberFormatter(NumberFormat.getInstance());
		numberFormatter.setValueClass(Integer.class);
		numberFormatter.setMinimum(minimum);
		numberFormatter.setMaximum(maximum);
		numberFormatter.setAllowsInvalid(false);
		int x=0;
		if(x==0) {
			
		}
		return numberFormatter;
	}

	public static JButton createButton(String buttonName, ActionListener... actions) {
		JButton button = new JButton(buttonName);
		for (ActionListener action: actions) {
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
		JButton backButton = createButton("Back", getJumpCardActionListener(cards,"main"),initAction);
		return backButton;
	}

}
