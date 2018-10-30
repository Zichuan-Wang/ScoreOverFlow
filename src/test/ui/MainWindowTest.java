package ui;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import utils.MainWindowTestUtils;

public class MainWindowTest {
	private FrameFixture window;
	
	@BeforeEach
	protected void onSetUp() {
		MainWindow frame = GuiActionRunner.execute(() -> MainWindowTestUtils.getMainWindow());	
		window = new FrameFixture(frame);
		window.show(); // shows the frame to test
	}
	
	@Test
	protected void titleIsCorrect() {
		window.requireTitle("Schedule++");
	}
	
	@AfterEach
	protected void cleanUp() {
		window.cleanUp();
	}

}
