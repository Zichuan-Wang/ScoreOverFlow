package ui;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ui.MainWindow;


public class MainPanelTest {
private FrameFixture window;
	
	@BeforeEach
	protected void onSetUp() {
		MainWindow frame = GuiActionRunner.execute(() -> new MainWindow());	
		window = new FrameFixture(frame);
		window.show(); // shows the frame to test
	}
	
	@Test
	protected void buttonsPresentAndClickable() {
		window.button(UITestUtil.MatchButtonByName("Reserve a Room"));
		window.button(UITestUtil.MatchButtonByName("View booked Rooms"));
	}
	
	@Test
	protected void reserveButtonNavigatesToCorrectPanel() {
		window.panel(UITestUtil.MatchPanelByName("Main"));
		window.button(UITestUtil.MatchButtonByName("Reserve a Room")).click();
		window.panel(UITestUtil.MatchPanelByName("Reserve a Room"));
	}
	
	@Test
	protected void viewButtonNavigatesToCorrectPanel() {
		window.panel(UITestUtil.MatchPanelByName("Main"));
		window.button(UITestUtil.MatchButtonByName("View booked Rooms")).click();
		window.panel(UITestUtil.MatchPanelByName("Booked Rooms"));
	}
	
	
	
	@AfterEach
	protected void cleanUp() {
		window.cleanUp();
	}

}
