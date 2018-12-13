package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exception.DBConnectionException;

public class ManagePanelTest {
	private final String MANAGE_PANEL_LABEL = "Manage Rooms and Facilities";

	private ManagePanel managePane;
	private JPanel topPane, middlePane;

	@BeforeEach
	protected void onSetUp() throws DBConnectionException {
		managePane = new ManagePanel(null, null, null, null, null, null, null);
		topPane = (JPanel) managePane.getComponent(0);
		middlePane = (JPanel) managePane.getComponent(1);
	}

	@Test
	protected void checkTitle() {
		assertEquals(MANAGE_PANEL_LABEL, ((JLabel) topPane.getComponent(0)).getText());
	}

	@Test
	protected void middlePanelTest() {
		assertEquals(0, middlePane.getComponentCount());
	}
}
