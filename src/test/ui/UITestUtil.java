package ui;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.assertj.swing.core.GenericTypeMatcher;

public class UITestUtil {
	
	static GenericTypeMatcher<JButton> MatchButtonByName(String name){
		return new GenericTypeMatcher<JButton>(JButton.class) {
			  protected boolean isMatching(JButton button) {
				    return name.equals(button.getText());
				  }
		  };
		
	}
	
	static GenericTypeMatcher<JPanel> MatchPanelByName(String name){
		return new GenericTypeMatcher<JPanel>(JPanel.class) {
			  protected boolean isMatching(JPanel panel) {
				  Component[] components = panel.getComponents();
				  for (int i =0;i<components.length;i++) {
					if(components[i] instanceof JLabel && name.equals(((JLabel)components[i]).getText())&&components[i].isShowing()) {
						return true;
					}
				
				  }
			  return false;
		      }
		  };
		
	}

}
