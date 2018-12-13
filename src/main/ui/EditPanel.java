package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.components.TimePickerSettings.TimeIncrement;

import entity.Facility;
import entity.Room;

public class EditPanel extends BasePanel{
	
	private static final long serialVersionUID = 1L;
	
	private Room room;
	
	private JTextField nameField;
	private JTextField capacity;
	
	public EditPanel(JPanel cards, String title, Room room) {
		super(cards, title);
		this.room = room;
		setMiddlePanel();
	}
	
	private void setMiddlePanel() {
		middlePane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JPanel infoPanel = getInfoPanel();

		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		c.weightx = 1.0;
		c.weighty = 1.0;
		middlePane.add(infoPanel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.weightx = 0.0;
		c.weighty = 1.0;

		JButton backButton = GuiUtils.createButton("Back", e -> GuiUtils.jumpToPanel(rootPane, "manage"));
		middlePane.add(backButton, c);
	}
	
	private JPanel getInfoPanel() {

		JPanel infoPanel = new JPanel();
		GroupLayout layout = new GroupLayout(infoPanel);
		infoPanel.setLayout(layout);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);

		// Name
		JLabel nameLabel = new JLabel("Name");
		nameField = new JTextField(50);
		
		// Capacity
		JLabel capacityLabel = new JLabel("Capacity");
		capacity = GuiUtils.getNumTextField(5);

		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(nameLabel)
				.addGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(nameField))
				.addComponent(capacityLabel).addComponent(capacity));

		layout.setVerticalGroup(layout.createParallelGroup(Alignment.CENTER).addGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(nameLabel))
				.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(nameField))
				.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(capacityLabel))
				.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(capacity))));
		
		return infoPanel;
	}

}
