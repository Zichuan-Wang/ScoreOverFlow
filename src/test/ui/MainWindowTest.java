package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.CardLayout;
import java.awt.Component;

import javax.swing.JPanel;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import utils.MainWindowTestUtils;
import utils.UiTestUtils;

public class MainWindowTest {
	private final String DEFAULT_MAIN_WINDOW_TITLE = "Schedule++";
	private final int CARDS_COMPONENT_COUNT = 4;

	private MainWindow mainWindow;

	@BeforeEach
	protected void onSetUp() {
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

		JPanel mainPane = (JPanel) cards.getComponent(1);
		JPanel reservePane = (JPanel) cards.getComponent(2);
		JPanel viewRoomsPane = (JPanel) cards.getComponent(3);

		assertNotNull(mainPane);
		assertNotNull(reservePane);
		assertNotNull(viewRoomsPane);

		((CardLayout) cards.getLayout()).show(cards, "main");
		assertTrue(mainPane.isVisible());
		assertFalse(reservePane.isVisible());
		assertFalse(viewRoomsPane.isVisible());

		((CardLayout) cards.getLayout()).show(cards, "reserve");
		assertFalse(mainPane.isVisible());
		assertTrue(reservePane.isVisible());
		assertFalse(viewRoomsPane.isVisible());

		((CardLayout) cards.getLayout()).show(cards, "view rooms");
		assertFalse(mainPane.isVisible());
		assertFalse(reservePane.isVisible());
		assertTrue(viewRoomsPane.isVisible());
	}

	@AfterEach
	protected void cleanUp() {
	}

}
