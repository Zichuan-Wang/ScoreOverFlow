package ui;

import java.awt.Component;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class TablePanel extends JPanel {
	/**
	 * Default serial version id
	 */
	private static final long serialVersionUID = 1L;

	public TablePanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	// Print objects (Strings and one button) in a table
	public void populateList(String[] columnNames, List<Object[]> rows, int[] editableIndex) {
		removeAll();
		int columnLength = columnNames.length;
		int rowLength = rows.size();
		// anonymous class dm to disable editing
		DefaultTableModel dm = new DefaultTableModel(columnNames, rowLength) {
			private static final long serialVersionUID = 1L;

			// button needs editable to be clicked
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				for (int index : editableIndex) {
					if (columnIndex == index) {
						return true;
					}
				}
				return false;
			}
		};
		JTable table = new JTable(dm);
		for (int index : editableIndex) {
			String buttonName = columnNames[index];
			table.getColumn(buttonName).setCellRenderer(new ComponentRenderer());
			table.getColumn(buttonName).setCellEditor(new ComponentEditor());
		}

		for (int i = 0; i < rowLength; i++) {
			Object[] column = rows.get(i);
			for (int j = 0; j < columnLength; j++) {
				dm.setValueAt(column[j], i, j);
			}
		}
		JScrollPane scrollTable = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollTable);
		revalidate();
		repaint();
	}

	public void reset() {
		removeAll();
		revalidate();
		repaint();

	}

	private class ComponentRenderer extends JButton implements TableCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			JComponent button = (JComponent) value;
			return button;
		}
	}

	private class ComponentEditor extends AbstractCellEditor implements TableCellEditor {
		private static final long serialVersionUID = 1L;
		JComponent editorValue;

		@Override
		public Object getCellEditorValue() {
			return editorValue;
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			this.editorValue = (JComponent) value;
			return editorValue;
		}

	}

}
