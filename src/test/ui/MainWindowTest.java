package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.CardLayout;
import java.awt.Component;

import javax.swing.JPanel;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exception.DBConnectionException;
import utils.MainWindowTestUtils;
import utils.UiTestUtils;

public class MainWindowTest {
	private final String DEFAULT_MAIN_WINDOW_TITLE = "Schedule++";
	private final int CARDS_COMPONENT_COUNT = 1;

	private MainWindow mainWindow;

	@BeforeEach
	protected void onSetUp() throws DBConnectionException {
		mainWindow = MainWindowTestUtils.getMainWindow();
	}

	@Test
	protected void titleIsCorrect() {
		assertEquals(DEFAULT_MAIN_WINDOW_TITLE, mainWindow.getTitle());
	}

	@Test
	protected void hasCardsAndPanelsDisplayingCorrectly() {
		JPanel cards = null;
		for (Component component : UiTestUtils.getAllComponents(mainWindow.getRootPane())) {
			if (component instanceof JPanel && ((JPanel) component).getLayout() instanceof CardLayout)
				cards = (JPanel) component;
		}
		assertNotNull(cards);

		// Assuming order is correct
		assertEquals(CARDS_COMPONENT_COUNT, cards.getComponentCount());

		JPanel loginPane = (JPanel) cards.getComponent(0);

		assertNotNull(loginPane);
		assertTrue(loginPane.isVisible());
	}

	@AfterEach
	protected void cleanUp() {
	}

}
