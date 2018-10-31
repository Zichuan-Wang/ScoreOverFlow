package ui;

import static org.assertj.swing.finder.WindowFinder.findFrame;
import static org.assertj.swing.launcher.ApplicationLauncher.application;

import javax.swing.JFrame;

import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import utils.MainWindowTestUtils;
import utils.UiTestUtils;

public class Testing extends AssertJSwingJUnitTestCase {
	private FrameFixture frame;

	@BeforeEach
	@Override
	protected void onSetUp() {
		Class<? extends MainWindow> mainWindowClass = MainWindowTestUtils.getMainWindow().getClass();
		application(mainWindowClass).start();
		frame = findFrame(new GenericTypeMatcher<JFrame>(JFrame.class) {
			protected boolean isMatching(JFrame frame) {
				return frame.isShowing();
			}
		}).using(robot());
		System.out.println(frame);
	}

	@Test
	protected void titleIsCorrect() {
		frame.requireTitle(UiTestUtils.DEFAULT_MAIN_WINDOW_TITLE);
	}

}
