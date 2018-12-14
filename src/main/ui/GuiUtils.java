package ui;

import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import entity.User;

public class GuiUtils {

	public static JButton createButton(String buttonName, ActionListener... actions) {
		JButton button = new JButton(buttonName);
		for (ActionListener action : actions) {
			button.addActionListener(action);
		}
		return button;
	}

	public static void jumpToPanel(JPanel rootPane, String id) {
		CardLayout cl = (CardLayout) rootPane.getLayout();
		cl.show(rootPane, id);
	}

	public static String userGroupToString(User user) {
		switch (user.getUserGroup()) {
		case 0:
			return "Administrator";
		case 1:
			return "High priority user";
		case 2:
			return "Program supervisor";
		case 3:
			return "Normal User";
		default:
			return "";
		}
	}

	public static JTextField getNumTextField(int maxCharacters) {
		class CustomDocumentFilter extends DocumentFilter {

			private Pattern regexCheck = Pattern.compile("[0-9]+");

			@Override
			public void insertString(FilterBypass fb, int offs, String str, AttributeSet a)
					throws BadLocationException {
				if (str == null) {
					return;
				}

				if (regexCheck.matcher(str).matches()
						&& (fb.getDocument().getLength() + str.length()) <= maxCharacters) {
					super.insertString(fb, offs, str, a);
				}
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String str, AttributeSet attrs)
					throws BadLocationException {
				if (str == null) {
					return;
				}
				
				if (str.equals("")) {
					fb.replace(offset, length, "", attrs);
				}

				if (regexCheck.matcher(str).matches()
						&& (fb.getDocument().getLength() + str.length()) <= maxCharacters) {
					fb.replace(offset, length, str, attrs);
				}
			}
		}

		JTextField textField = new JTextField(maxCharacters);
		((AbstractDocument) textField.getDocument()).setDocumentFilter(new CustomDocumentFilter());
		return textField;
	}
}
