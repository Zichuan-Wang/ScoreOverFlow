package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BasePanelTest {
	private String BASEPANEL_TEST_TITLE = "test";
	BasePanel basePane;

	@BeforeEach
	protected void onSetUp() {
		basePane = new BasePanel(BASEPANEL_TEST_TITLE, null);
		basePane.initPanels();
	}

	@Test
	protected void hasTopPanelAndHasTitle() {
		assertNotNull(basePane.getComponent(0));
		JPanel topPanel = (JPanel) basePane.getComponent(0);
		assertEquals(BASEPANEL_TEST_TITLE, ((JLabel) topPanel.getComponent(0)).getText());
	}

	@Test
	protected void hasMiddlePanel() {
		assertNotNull(basePane.getComponent(1));
		// JPanel middlePanel = (JPanel)basePane.getComponent(1);

	}

	@Test
	protected void hasBottomPanel() {
		assertNotNull(basePane.getComponent(2));
		// JPanel bottomPanel = (JPanel)basePane.getComponent(2);
	}

	@AfterEach
	protected void cleanUp() {
	}
}
