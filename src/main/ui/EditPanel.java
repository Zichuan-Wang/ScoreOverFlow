package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import entity.Facility;
import entity.Room;
import server.action.FacilityAction;
import server.action.RoomAction;

public class EditPanel extends BasePanel{
	
	private static final long serialVersionUID = 1L;
	
	private Room room;
	
	private FacilityAction facilityAction;
	private RoomAction roomAction;
	
	private JTextField nameField;
	private JTextField capacity;
	
	private TablePanel facilityTable;
	
	private Runnable callback;
	
	public EditPanel(JPanel cards, String title, FacilityAction facilityAction, RoomAction roomAction, Runnable callback) {
		super(cards, title);
		this.facilityAction = facilityAction;
		this.roomAction = roomAction;
		this.callback = callback;
		setMiddlePanel();
	}
	
	public void setRoom(Room room) {
		this.room = room;
	}
	
	@Override
	public void pareparePanel() {
		if (room != null) {
			nameField.setText(room.getName());
			if (room.getCapacity() == 0) {
				capacity.setText("");
			} else {
				capacity.setText(Integer.toString(room.getCapacity()));
			}
		}
		prepareTable();
	}
	
	private void prepareTable() {
		List<Facility> facilities = facilityAction.findAllFacilities();
		String[] columnNames = new String[] { "Select", "Facility", "Action" };
		
		List<Object[]> rows = new ArrayList<>();
		for (Facility facility : facilities) {
			Object[] row = new Object[3];
			row[0] = getFacilityCheckBox(facility);
			row[1] = facility.getName();
			row[2] = getRemoveButton(facility);
			rows.add(row);
		}
		facilityTable.populateList(columnNames, rows, new int[]{0,2});
	}
	
	private JCheckBox getFacilityCheckBox(Facility facility) {
		return new JCheckBox();
	}
	
	private JButton getRemoveButton(Facility facility) {
		return new JButton("remove");
	}
	
	private void setMiddlePanel() {
		middlePane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JPanel infoPanel = getInfoPanel();
		facilityTable = new TablePanel();

		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		c.weightx = 1.0;
		c.weighty = 1.0;
		middlePane.add(infoPanel, c);
		
		c.gridx = 0;
		c.gridy = 1;
		middlePane.add(facilityTable, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.weightx = 0.0;
		c.weighty = 1.0;
		JButton saveButton = GuiUtils.createButton("Save", e -> {
			saveRoom();
			JOptionPane.showMessageDialog(null, "Success!");
			GuiUtils.jumpToPanel(rootPane, "manage");
			callback.run();
		});
		middlePane.add(saveButton, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		c.weightx = 0.0;
		c.weighty = 1.0;
		
		JButton backButton = GuiUtils.createButton("Back", e -> GuiUtils.jumpToPanel(rootPane, "manage"));
		middlePane.add(backButton, c);
	}
	
	private void saveRoom() {
		room.setName(nameField.getText());
		room.setCapacity(capacity.getText().length() == 0 ? 0 : Integer.parseInt(capacity.getText()));
		roomAction.saveRoom(room);
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
		
		// Facility
		JLabel facilityLabel = new JLabel("Facility");

		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(nameLabel)
				.addGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(nameField))
				.addComponent(capacityLabel).addComponent(capacity)
				.addGroup(layout.createSequentialGroup().addComponent(facilityLabel)));

		layout.setVerticalGroup(layout.createParallelGroup(Alignment.CENTER).addGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(nameLabel))
				.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(nameField))
				.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(capacityLabel))
				.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(capacity))
				.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(facilityLabel))));
		
		return infoPanel;
	}

}
