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
		batchPane = new BatchPanel(null, null, null,null,null);
		topPane = (JPanel) batchPane.getComponent(0);
		middlePane = (JPanel) batchPane.getComponent(1);
	}

	@Test
	protected void checkTitle() {
		assertEquals(BATCH_PANEL_LABEL, ((JLabel) topPane.getComponent(0)).getText());
	}

	@Test
	protected void middlePanelTest() {
		assertEquals(7, middlePane.getComponentCount());
		assertTrue(middlePane.getComponent(2) instanceof JButton);
		JButton downloadButton = (JButton) middlePane.getComponent(2);
		JButton uploadButton = (JButton) middlePane.getComponent(5);
		assertNotNull(uploadButton);
		assertEquals("Upload File", uploadButton.getText());
		assertEquals("Download Template", downloadButton.getText());
	}
}
