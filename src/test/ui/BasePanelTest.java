package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BasePanelTest {
	private String BASEPANEL_TEST_TITLE = "test";
	private final int PANELNUM = 3;
	BasePanel basePane;

	@BeforeEach
	protected void onSetUp() {
		basePane = new BasePanel(null, BASEPANEL_TEST_TITLE);
	}

	@Test
	protected void hasTopPanelAndHasTitle() {
		assertNotNull(basePane.getComponent(0));
		JPanel topPanel = (JPanel) basePane.getComponent(0);
		assertEquals(BASEPANEL_TEST_TITLE, ((JLabel) topPanel.getComponent(0)).getText());
	}

	@Test
	protected void hasCorrectPanel() {
		assertEquals(PANELNUM, basePane.getComponentCount());
		assertEquals(basePane.getTopPane(), basePane.getComponent(0));
		assertEquals(basePane.getMiddlePane(), basePane.getComponent(1));
		assertEquals(basePane.getBottomPane(), basePane.getComponent(2));
	}

	@Test
	protected void backButtonTest() {
		basePane.setBackButtonBottomPanel();
		assertEquals(1,basePane.getBottomPane().getComponentCount());
		JButton backButton = (JButton) basePane.bottomPane.getComponent(0);
		assertEquals("Back",backButton.getText());
		
	}
	
	@AfterEach
	protected void cleanUp() {
	}
}
