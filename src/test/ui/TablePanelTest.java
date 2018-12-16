package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.TableModel;

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
	
	protected void fillCheckLayout() {
		assertTrue(tablePane.getLayout() instanceof BorderLayout);
		tablePane = new TablePanel(false); //fill
		assertTrue(tablePane.getLayout() instanceof BorderLayout);
		tablePane = new TablePanel(true); //fill
		assertTrue(tablePane.getLayout() instanceof BoxLayout);
	}

	@Test
	protected void testPopulateList() {
		String[] columnNames = new String[] { "column 1", "column 2", "botton row" };
		List<Object[]> rows = new ArrayList<>();
		rows.add(new Object[] { "1,1", "1,2", new JButton() });
		tablePane.populateList(columnNames, rows, new int[]{2});
		assertTrue(tablePane.getComponent(0) instanceof JScrollPane);
		JScrollPane scrollPane = (JScrollPane) tablePane.getComponent(0);
		JViewport viewport = scrollPane.getViewport();
		JTable table = (JTable) viewport.getView();
		assertNotNull(table);
		assertTrue(table.getValueAt(0, 0) instanceof String);
		assertEquals("1,1", table.getValueAt(0, 0));
		assertTrue(table.getValueAt(0, 1) instanceof String);
		assertEquals("1,2", table.getValueAt(0, 1));
		assertTrue(table.getValueAt(0, 2) instanceof JButton);
		// test dm
		TableModel model = table.getModel();
		assertFalse(model.isCellEditable(0, 0));
		assertFalse(model.isCellEditable(0, 1));
		assertTrue(model.isCellEditable(0, 2));
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
