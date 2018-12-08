package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTable;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TablePanelTest {
	private TablePanel tablePane;

	@BeforeEach
	protected void onSetUp() {
		tablePane = new TablePanel();
	}

	@Test
	protected void checkLayout() {
		assertTrue(tablePane.getLayout() instanceof BoxLayout);
	}

	@Test
	protected void testPopulateList() {
		String buttonName = "botton row";
		Object[] columnNames = new Object[] { "column 1", "column 2", buttonName };
		List<Object[]> rows = new ArrayList<>();
		rows.add(new Object[] { "1,1", "1,2", new JButton() });
		tablePane.populateList(columnNames, rows, buttonName);
		assertTrue(tablePane.getComponent(0) instanceof JTable);
		JTable table = (JTable) tablePane.getComponent(0);
		assertTrue(table.getValueAt(0, 0) instanceof String);
		assertEquals("1,1", table.getValueAt(0, 0));
		assertTrue(table.getValueAt(0, 1) instanceof String);
		assertEquals("1,2", table.getValueAt(0, 1));
		assertTrue(table.getValueAt(0, 2) instanceof JButton);
	}

	@Test
	protected void resetTest() {
		tablePane.reset();
		assertEquals(0, tablePane.getComponentCount());
	}

	@AfterEach
	protected void cleanUp() {
	}
}
