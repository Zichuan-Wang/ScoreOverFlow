package utils;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.fixture.FrameFixture;

public class UiTestUtils {
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
