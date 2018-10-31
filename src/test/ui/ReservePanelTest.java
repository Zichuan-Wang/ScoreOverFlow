package ui;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import utils.MainWindowTestUtils;
import utils.UiTestUtils;

public class ReservePanelTest {
	private FrameFixture window;

	@BeforeEach
	protected void onSetUp() {
		MainWindow frame = GuiActionRunner.execute(() -> MainWindowTestUtils.getMainWindow());
		window = new FrameFixture(frame);
		UiTestUtils.ensureClicked(window, "Reserve a Room");
		window.show(); // shows the frame to test
	}

	@Test
	protected void fieldsTest() {
		// Name
		window.textBox(UiTestUtils.matchTextFieldByName(""));
		// Time
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
		window.textBox(UiTestUtils.matchTextFieldByName(LocalTime.of(0, 0).format(dtf)));
		window.textBox(UiTestUtils.matchTextFieldByName(LocalTime.of(0, 10).format(dtf)));
		// Date
		window.textBox(
				UiTestUtils.matchTextFieldByName(LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM dd, YYYY"))));
	}

	@Test
	protected void reserveButtonsPresentAndClickable() {
		window.button(UiTestUtils.matchButtonByName("Search"));
		UiTestUtils.ensureClicked(window, "Search");
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
