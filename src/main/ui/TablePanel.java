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
	
	
	public TablePanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	// Print objects (Strings and one button) in a table
	public void populateList(Object[] columnNames,List<Object[]> rows, String buttonName) {
		removeAll();
		int columnLength = columnNames.length;
		int rowLength = rows.size();
		// anonymous class dm to disable editing
		DefaultTableModel dm = new DefaultTableModel(columnNames,rowLength) {
			private static final long serialVersionUID = 1L;
			// button needs editable to be clicked
			public boolean isCellEditable(int rowIndex, int columnIndex) {
			    return columnIndex == columnLength-1;
			}
		};
		JTable table = new JTable(dm);
		table.getColumn(buttonName).setCellRenderer(new ButtonRenderer());
        table.getColumn(buttonName).setCellEditor(new ButtonEditor());
        	
		for (int i = 0; i < rowLength; i++) {
			Object[] column = rows.get(i);
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
	
	public void reset() {
		removeAll();
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
		JButton editorValue;
		
		public ButtonEditor() {
		}

		@Override
		public Object getCellEditorValue() {
			return editorValue;
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			this.editorValue = (JButton) value;
			return editorValue;
		}
		
	}

}
