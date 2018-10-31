package ui;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import utils.MainWindowTestUtils;
import utils.UiTestUtils;

public class MainPanelTest {
	private FrameFixture window;

	@BeforeEach
	protected void onSetUp() {
		MainWindow frame = GuiActionRunner.execute(() -> MainWindowTestUtils.getMainWindow());
		window = new FrameFixture(frame);
		window.show(); // shows the frame to test
	}

	@Test
	protected void buttonsPresentAndClickable() {
		window.button(UiTestUtils.matchButtonByName("Reserve a Room"));
		window.button(UiTestUtils.matchButtonByName("View Reservations"));
		int x = 0;
		if (x == 0) {

		}
	}

	@Test
	protected void reserveButtonNavigatesToCorrectPanel() {
		window.panel(UiTestUtils.matchPanelByName("Main"));
		UiTestUtils.ensureClicked(window, "Reserve a Room");
		window.panel(UiTestUtils.matchPanelByName("Reserve a Room"));
	}

	@Test
	protected void viewButtonNavigatesToCorrectPanel() {
		UiTestUtils.ensureClicked(window, "View Reservations");
		window.panel(UiTestUtils.matchPanelByName("My Reservations"));
	}

	@AfterEach
	protected void cleanUp() {
		window.cleanUp();
	}

}
