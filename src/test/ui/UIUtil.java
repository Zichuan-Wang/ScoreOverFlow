package UI;

import javax.swing.JButton;

import org.assertj.swing.core.GenericTypeMatcher;

public class UIUtil {
	
	static GenericTypeMatcher<JButton> MatchButtonByName(String name){
		return new GenericTypeMatcher<JButton>(JButton.class) {
			  protected boolean isMatching(JButton button) {
				    return name.equals(button.getText());
				  }
		  };
		
	}

}
