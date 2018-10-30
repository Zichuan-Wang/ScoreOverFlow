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
		window.button(UiTestUtils.matchButtonByName("Reserve a Room"));
		window.button(UiTestUtils.matchButtonByName("View Reservations"));
	}
	
	@Test
	protected void reserveButtonNavigatesToCorrectPanel() {
		window.panel(UiTestUtils.matchPanelByName("Main"));
		UiTestUtils.ensureClicked(window, "Reserve a Room");
		window.panel(UiTestUtils.matchPanelByName("Reserve a Room"));
	}
	
	@Test
	protected void viewButtonNavigatesToCorrectPanel() {
		window.panel(UiTestUtils.matchPanelByName("Main"));
		UiTestUtils.ensureClicked(window, "View Reservations");
		window.panel(UiTestUtils.matchPanelByName("My Reservations"));
	}
	
	@AfterEach
	protected void cleanUp() {
		window.cleanUp();
	}

}
