package utils;

import java.awt.Component;
import java.awt.LayoutManager2;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.fixture.FrameFixture;

public class UiTestUtils {
	public static final String DEFAULT_MAIN_WINDOW_TITLE = "Schedule++";
	public static final String DEFAULT_RESERVATION_PANEL_TITLE = "Reserve a Room";
	public static final String DEFAULT_VIEW_ROOMS__PANEL_TITLE = "My Reservations";

	public static GenericTypeMatcher<JButton> matchButtonByName(String name){
		return new GenericTypeMatcher<JButton>(JButton.class) {
			protected boolean isMatching(JButton button) {
				return name.equals(button.getText());
			}
		};
	}

	public static GenericTypeMatcher<JPanel> matchPanelByName(String name){
		return new GenericTypeMatcher<JPanel>(JPanel.class) {
			protected boolean isMatching(JPanel panel) {
				Component[] components = panel.getComponents();
				for (int i =0;i<components.length;i++) {
					if(components[i] instanceof JLabel && name.equals(((JLabel)components[i]).getText()) 
							&& components[i].isShowing()) {
						return true;
					}
				}
				return false;
			}
		};
	}

	public static GenericTypeMatcher<JTextComponent> matchTextFieldByName(String name){
		return new GenericTypeMatcher<JTextComponent>(JTextComponent.class) {
			protected boolean isMatching(JTextComponent textField) {
				if(textField instanceof JTextComponent && name.equals(textField.getText()) 
					&& textField.isVisible()) {
					return true;
				}
				return false;
			}
		};
	}

	public static void ensureClicked(FrameFixture frame, String buttonName) {
		while (true) {
			try {
				frame.button(UiTestUtils.matchButtonByName(buttonName)).click();
				Thread.sleep(200);
				frame.button(UiTestUtils.matchButtonByName(buttonName));
			}
			catch (Exception e) {
				break;
			}
		}
	}

}
