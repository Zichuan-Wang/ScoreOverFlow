package ui;


import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import dao.factory.RoomDaoFactory;
import entity.EntityUtils;
import entity.Reservation;
import entity.Room;
import exception.DBConnectionException;
import server.action.ReserveRoomAction;
import server.constraint.SearchRoomConstraint;

public class ReservePanel extends BasePanel {
	/**
	 * Default serial version id
	 */
	private static final long serialVersionUID = 1L;
	private final static String TITLE = "Reserve a Room";
	private JDatePickerImpl datePicker;
	private JComboBox<String> startTime;
	private DisableBoxModel endTime;
	private JFormattedTextField capacity;
	private JTextField nameField;
	private JButton searchButton, backButton;
	private TablePanel roomPane;

	public ReservePanel(JPanel cards) {
		super(TITLE,cards);
	}

	@Override
	public JPanel getMiddlePanel() {
		// middle Panel
		JPanel middlePane = new JPanel();

		middlePane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JPanel searchPane = createSearchPanel();

		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		c.weightx = 1.0;
		c.weighty = 1.0;
		middlePane.add(searchPane, c);

		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;
		c.weightx = 1.0;
		c.weighty = 1.0;
		roomPane = new TablePanel();
		JScrollPane scrollRoomPane = new JScrollPane(roomPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollRoomPane.setPreferredSize(new Dimension(600, 200));
		middlePane.add(scrollRoomPane, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.weightx = 0.0;
		c.weighty = 1.0;
		backButton = GuiUtils.getBackButton(this,cards);
		middlePane.add(backButton, c);
		return middlePane;
	}

	private JPanel createSearchPanel() {
		// Date
		JPanel searchPane = new JPanel();
		JLabel dateLabel = new JLabel("Date");
		searchPane.add(dateLabel);

		//set default date to today
		LocalDate today = LocalDate.now();
		datePicker = GuiUtils.getDatePicker();
		UtilDateModel model = (UtilDateModel) datePicker.getModel();
		// MonthValue -1 ?
		model.setDate(today.getYear(),today.getMonthValue()-1,today.getDayOfMonth());
		model.setSelected(true);
		searchPane.add(datePicker);
		searchPane.add(Box.createRigidArea(new Dimension(5, 0)));

		// Start Time and EndTime

		String[] timeString = getTimeString();
		JLabel startTimeLabel = new JLabel("Start Time");
		searchPane.add(startTimeLabel);
		startTime = new JComboBox<>(timeString);
		searchPane.add(startTime);

		JLabel endTimeLabel = new JLabel("End Time");
		searchPane.add(endTimeLabel);
		endTime = new DisableBoxModel(timeString);
		searchPane.add(endTime);

		// If a start time is selected, disable all previous entries from end time
		startTime.addActionListener(e->{
			int i = startTime.getSelectedIndex();
			endTime.removeAllItems();
			for (int j =0; j<timeString.length;j++) {
				endTime.addItem(timeString[j],j<=i);
			}
			endTime.setSelectedIndex(i+1);
		});

		// Capacity
		JLabel capacityLabel = new JLabel("Capacity");
		searchPane.add(capacityLabel);

		capacity = new JFormattedTextField(GuiUtils.getNumberFormatter(0,1000));
		capacity.setValue(new Integer(1));
		capacity.setColumns(3);
		searchPane.add(capacity);

		// Name
		JLabel nameLabel = new JLabel("Name");
		searchPane.add(nameLabel);
		nameField = new JTextField(10);
		searchPane.add(nameField);

		// Search Button
		searchButton = getSearchButton();
		searchPane.add(searchButton);
		return searchPane;
	}

	@Override
	// Reset when exit
	public void reset() {
		startTime.setSelectedIndex(0);
		endTime.setSelectedIndex(0);
		capacity.setValue(100);
		nameField.setText("");
	}

	@Override
	public void showPanel() {
	}

	private JButton getSearchButton() {
		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(e -> {
			SearchRoomConstraint src = new SearchRoomConstraint();

			String selectedDate = datePicker.getJFormattedTextField().getText();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

			// Parse
			try {
				src.setCapacity(Integer.parseInt(capacity.getText()));
				src.setEventDate(new SimpleDateFormat("yyyy-MM-dd").parse(selectedDate));
				src.setStartTime(sdf.parse(selectedDate + " " + startTime.getSelectedItem()));
				src.setEndTime(sdf.parse(selectedDate + " " + endTime.getSelectedItem()));
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			src.setRoomName(nameField.getText());
			// search from database
			List<Room> roomList = new ArrayList<>();
			try {
				roomList = RoomDaoFactory.getInstance().searchRooms(src);
				if (roomList.isEmpty())
					JOptionPane.showMessageDialog(null, "No rooms with your requirements found. Please Try Again.");
				else {
					// Get the name and populate the list
					List<Object[]> rows = new ArrayList<>();
					for (Room room : roomList) {
						Object[] row = new Object[2];
						row[0] = room.getName();
						JButton reserveButton = getReserveButton(room, src);
						row[1] = reserveButton;
						rows.add(row);
					}
					roomPane.populateList(rows);
				}
			} catch (DBConnectionException e2) {
				e2.printStackTrace();
			}
		});
		return searchButton;
	}

	private JButton getReserveButton(Room room, SearchRoomConstraint src) {
		JButton reserveButton = new JButton("Reserve");
		reserveButton.addActionListener(e -> {
			// convert room to reservation
			Reservation reservation = EntityUtils.roomToReservation(room, src.getEventDate(),
					src.getStartTime(), src.getEndTime(), 0);
			// reserve
			try {
				boolean success = ReserveRoomAction.reserveRoom(reservation);
				//@TODO handling success and failure
				if (success) {
					JOptionPane.showMessageDialog(null, "Success!");
					reserveButton.setEnabled(false);
				}else {
					JOptionPane.showMessageDialog(null, "There is something wrong with the reservation. Please Try Again.");
				}
			} catch (DBConnectionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		return reserveButton;
	}

	private String[] getTimeString() {
		String[] timeString = new String[6 * 24];
		int i = 0;
		for (int hour = 0; hour < 24; hour++) {
			for (int minute = 0; minute < 60; minute += 10) {
				timeString[i++] = LocalTime.of(hour, minute).toString();
			}
		}
		return timeString;
	}

}
