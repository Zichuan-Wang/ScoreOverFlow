package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.CardLayout;

import javax.swing.JPanel;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exception.DBConnectionException;
import utils.MainWindowTestUtils;
import utils.UiTestUtils;

public class MainWindowTest {

	private final String DEFAULT_MAIN_WINDOW_TITLE = "Schedule++";
	private final int CARDS_COMPONENT_INITIAL_COUNT = 1;

	private MainWindow mainWindow;
	private JPanel rootPane;

	@BeforeEach
	protected void onSetUp() throws DBConnectionException {
		mainWindow = MainWindowTestUtils.getMainWindow();
		for (Object jPane : UiTestUtils.getObjects(mainWindow, JPanel.class)) {
			if (((JPanel) jPane).getLayout() instanceof CardLayout)
				rootPane = (JPanel) jPane;
		}
	}

	@Test
	protected void titleIsCorrect() {
		assertEquals(DEFAULT_MAIN_WINDOW_TITLE, mainWindow.getTitle());
	}

	@Test
	protected void sizeIsCorrect() {
		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		assertEquals(screenSize.getHeight() / 2, mainWindow.getHeight());
		assertEquals(screenSize.getWidth() / 4, mainWindow.getWidth());
	}

	@Test
	protected void cardInitializedWithLoginPanel() {
		assertNotNull(rootPane);
		assertEquals(CARDS_COMPONENT_INITIAL_COUNT, rootPane.getComponentCount());
		assertTrue(rootPane.getComponent(0) instanceof LoginPanel);
	}

	@AfterEach
	protected void cleanUp() {
	}

}
