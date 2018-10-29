package ui;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.jupiter.api.Test;

import ui.MainWindow;


public class MainPanelTest extends AssertJSwingJUnitTestCase {

	private FrameFixture window;
	@Test
	protected void onSetUp() {
		// TODO Auto-generated method stub
		  MainWindow frame = GuiActionRunner.execute(() -> new MainWindow());
	
		  window = new FrameFixture(frame);
		  window.show(); // shows the frame to test
		  window.button(UITestUtil.MatchButtonByName("Reserve a Room")).click();
		  //window.button("Reserve a Room").click();
	}


}
