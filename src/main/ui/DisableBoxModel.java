package ui;

import java.awt.Component;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DisableBoxModel extends JComboBox{
	/**
	 * Default serial version id
	 */
	private static final long serialVersionUID = 1L;
	private Set<Integer> disabled_items = new HashSet<>();
	
	
	public DisableBoxModel() {
		super();
		super.setRenderer(new DisabledItemsRenderer());
	}
	
	public DisableBoxModel(String[] items) {
		super(items);
		super.setRenderer(new DisabledItemsRenderer());
	}

	public void addItem(Object obj, boolean disabled) {
		super.addItem(obj);
		if (disabled) {
			disabled_items.add(getItemCount() - 1);
		}
	}

	@Override
	public void removeAllItems() {
		super.removeAllItems();
		disabled_items = new HashSet<Integer>();
	}

	@Override
	public void setSelectedIndex(int i) {
		if (disabled_items.contains(i))
			return;
		super.setSelectedIndex(i);
	}

	private class DisabledItemsRenderer extends BasicComboBoxRenderer {

		/**
		 * Default serial version id
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Component getListCellRendererComponent(JList list,
				Object value,
				int index,
				boolean isSelected,
				boolean cellHasFocus) {

			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}
			if (disabled_items.contains(index)) {
				setBackground(list.getBackground());
				setForeground(UIManager.getColor("Label.disabledForeground"));
			}
			setFont(list.getFont());
			setText((value == null) ? "" : value.toString());
			return this;
		}
	}
}
