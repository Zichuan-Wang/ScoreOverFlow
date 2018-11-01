package ui;

import java.awt.Component;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class TablePanel extends JPanel {
	/**
	 * Default serial version id
	 */
	private static final long serialVersionUID = 1L;
	final int MAX_LISTING = 10;
	public TablePanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	// Print objects (Strings and one button) in a table
	public void populateList(Object[] columnNames,List<Object[]> rows, String buttonName) {
		removeAll();

		int rowLength = Math.min(rows.size(), MAX_LISTING);
		DefaultTableModel dm = new DefaultTableModel(columnNames,rowLength);
		JTable table = new JTable(dm);
		table.getColumn(buttonName).setCellRenderer(new ButtonRenderer());
        table.getColumn(buttonName).setCellEditor(new ButtonEditor());
        	
		for (int i = 0; i < rowLength; i++) {
			Object[] column = rows.get(i);
			int columnLength = column.length;
			for (int j =0; j<columnLength -1; j++) {
				dm.setValueAt(column[j], i, j);
			}
			JButton actionButton = (JButton) column[columnLength-1];
			dm.setValueAt(actionButton,i,columnLength-1);
		}
		add(table);
		revalidate();
		repaint();
	}

	// Renderer class for Buttons in JTabel
	private class ButtonRenderer extends JButton implements TableCellRenderer {
		private static final long serialVersionUID = 1L;

		public ButtonRenderer() {
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value,
				boolean isSelected, boolean hasFocus, int row, int column) {
			return (JButton)value;
		}
	}

	// Editor class for Buttons in JTabel
	private class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
		private static final long serialVersionUID = 1L;
		
		public ButtonEditor() {
		}

		@Override
		public Object getCellEditorValue() {
			return null;
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			return (JButton) value;
		}
	}

}
