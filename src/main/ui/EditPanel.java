package ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.BoxLayout;
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
	private Set<Facility> facilities;
	
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
		facilities = new HashSet<>();
		setMiddlePanel();
		setBackButtonBottomPanel();
	}
	
	
	public void setRoom(Room room) {
		this.room = room;
		if (room.getName() == null){
			this.titleLabel.setText("Create a Room");
		}else {
			this.titleLabel.setText("Edit Room " + room.getName());
		}
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
		facilities.clear();
		facilities.addAll(room.getFacilities());
		List<Facility> allFacilities = facilityAction.findAllFacilities();
		Set<Integer> roomFacilities = new HashSet<>();
		for (Facility facility : room.getFacilities()) {
			roomFacilities.add(facility.getId());
		}

		String[] columnNames = new String[] { "Select", "Facility" , "Action"};
		List<Object[]> rows = new ArrayList<>();
		for (Facility facility : allFacilities) {
			Object[] row = new Object[3];
			row[0] = getFacilityCheckBox(facility, roomFacilities.contains(facility.getId()));
			row[1] = facility.getName();
			row[2] = getFacilityButton(facility);
			rows.add(row);
		}
		
		facilityTable.populateList(columnNames, rows, new int[]{0, 2});

	}
	
	private JCheckBox getFacilityCheckBox(Facility facility, boolean selected) {
		JCheckBox checkBox = new JCheckBox();
		checkBox.setSelected(selected);
		checkBox.addItemListener(e -> {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					facilities.add(facility);
				} else {
					facilities.remove(facility);
				}
		});
		return checkBox;
	}
	
	private JButton getFacilityButton(Facility facility) {
		JButton button = new JButton("Delete Facility");
		button.addActionListener(e -> {
			facilities.remove(facility);
			facilityAction.removeFacility(facility);
			this.pareparePanel();
		});
		return button;
	}
	
	private void setMiddlePanel() {
		middlePane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JPanel infoPanel = getInfoPanel();
		JPanel facilityPanel = getFacilityPanel();

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		middlePane.add(infoPanel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		middlePane.add(facilityPanel, c);

		c.fill = GridBagConstraints.CENTER;
		c.gridx = 1;
		c.gridy = 2;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		JButton saveButton = GuiUtils.createButton("Save", e -> {
			saveRoom();
			JOptionPane.showMessageDialog(null, "Success!");
			GuiUtils.jumpToPanel(rootPane, "manage");
			callback.run();
		});
		middlePane.add(saveButton, c);
	}
	
	private void saveRoom() {
		room.setName(nameField.getText());
		room.setCapacity(capacity.getText().length() == 0 ? 0 : Integer.parseInt(capacity.getText()));
		room.getFacilities().clear();
		room.getFacilities().addAll(facilities);
		roomAction.saveRoom(room);	
	}
	
	private JPanel getFacilityPanel() {
		JPanel facilityPanel = new JPanel();
		facilityPanel.setLayout(new BoxLayout(facilityPanel, BoxLayout.Y_AXIS));
		
		facilityTable = new TablePanel();
		facilityPanel.add(facilityTable);
		
		JPanel addFacility = new JPanel();
		JLabel addFacilityLabel = new JLabel("New Facility:");
		JTextField addFacilityTextField = new JTextField(20);
		JButton addFacilityButton = new JButton("Add");
		addFacilityButton.addActionListener(e-> {
			facilityAction.saveFacility(new Facility().setName(addFacilityTextField.getText()));
			this.pareparePanel();
		});
		addFacility.add(addFacilityLabel,BorderLayout.LINE_START);
		addFacility.add(addFacilityTextField,BorderLayout.CENTER);
		addFacility.add(addFacilityButton,BorderLayout.LINE_END);
		
		facilityPanel.add(addFacility);
		return facilityPanel;
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
	
	@Override
	public void setBackButtonBottomPanel() {
		JButton backButton = GuiUtils.createButton("Back", e -> GuiUtils.jumpToPanel(rootPane, "manage"));
		bottomPane.add(backButton, BorderLayout.PAGE_END);
	}

}
