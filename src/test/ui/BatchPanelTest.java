package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exception.DBConnectionException;

public class BatchPanelTest {
	private final String BATCH_PANEL_LABEL = "Reserve Rooms in Batch";

	private BatchPanel batchPane;
	private JPanel topPane, middlePane;

	@BeforeEach
	protected void onSetUp() throws DBConnectionException {
		batchPane = new BatchPanel(null, null, null);
		topPane = (JPanel) batchPane.getComponent(0);
		middlePane = (JPanel) batchPane.getComponent(1);
	}

	@Test
	protected void checkTitle() {
		assertEquals(BATCH_PANEL_LABEL, ((JLabel) topPane.getComponent(0)).getText());
	}

	@Test
	protected void middlePanelTest() {
		assertEquals(2, middlePane.getComponentCount()); // upload button and back button
		assertTrue(middlePane.getComponent(0) instanceof JButton);
		JButton uploadButton = (JButton) middlePane.getComponent(0);
		assertNotNull(uploadButton);
		assertEquals("Upload", uploadButton.getText());

	}
}
