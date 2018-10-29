package ui;

import javax.swing.JButton;

import org.assertj.swing.core.GenericTypeMatcher;

public class UiUtils {
	
	static GenericTypeMatcher<JButton> MatchButtonByName(String name){
		return new GenericTypeMatcher<JButton>(JButton.class) {
			  protected boolean isMatching(JButton button) {
				    return name.equals(button.getText());
				  }
		  };
		
	}

}
