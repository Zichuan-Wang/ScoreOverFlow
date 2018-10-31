package ui;


import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import utils.MainWindowTestUtils;
import utils.UiTestUtils;

public class ViewRoomsPanelTest {
	private FrameFixture window;
	@BeforeEach
	protected void onSetUp() {
		MainWindow frame = GuiActionRunner.execute(() -> MainWindowTestUtils.getMainWindow());	
		window = new FrameFixture(frame);
		UiTestUtils.ensureClicked(window, "Reserve a Room");
		window.show(); // shows the frame to test
	}
	
	@Test
	protected void backButtonsPresentAndClickable() {
		UiTestUtils.ensureClicked(window, "Back");
		window.panel(UiTestUtils.matchPanelByName("Main"));
		UiTestUtils.ensureClicked(window, "Reserve a Room");
		window.panel(UiTestUtils.matchPanelByName("Reserve a Room"));
	}
	
	
	@AfterEach
	protected void cleanUp() {
		window.cleanUp();
	}
}
