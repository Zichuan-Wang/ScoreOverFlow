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

import javax.mail.MessagingException;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

//import org.apache.shiro.SecurityUtils;

import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.components.TimePickerSettings.TimeIncrement;

import email.EmailSender;
import entity.EntityUtils;
import entity.Facility;
import entity.Reservation;
import entity.Room;
import entity.User;
import server.action.FacilityAction;
import server.action.ReservationAction;
import server.action.RoomAction;
import server.action.UserAction;
import server.constraint.SearchRoomConstraint;

public class ReservePanel extends BasePanel {
	private static final long serialVersionUID = 1L;
	private final static String TITLE = "Reserve a Room";
	private final int DEFAULTDATEYEARRANGE = 1;

	private User user;
	private DatePickerSettings dateSettings;
	private DatePicker datePicker;
	private TimePickerSettings startTimeSettings;
	private TimePicker startTimePicker;
	private TimePickerSettings endTimeSettings;
	private TimePicker endTimePicker;
	private JTextField capacity;
	private JTextField nameField;
	private JList<Facility> facilityList;
	private JCheckBox showBookedRooms;
	private JButton searchButton, backButton;
	private TablePanel roomPane;

	private UserAction userAction;
	private ReservationAction reservationAction;
	private RoomAction roomAction;
	private FacilityAction facilityAction;

	public ReservePanel(JPanel cards, User user, UserAction userAction, ReservationAction reservationAction,
			RoomAction roomAction, FacilityAction facilityAction) {
		super(cards, TITLE);
		this.user = user;
		this.userAction = userAction;
		this.reservationAction = reservationAction;
		this.roomAction = roomAction;
		this.facilityAction = facilityAction;
		setMiddlePanel();
	}

	private void setMiddlePanel() {
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
		roomPane.setPreferredSize(new Dimension(600, 200));
		middlePane.add(roomPane, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.weightx = 0.0;
		c.weighty = 1.0;

		backButton = GuiUtils.createButton("Back", e -> GuiUtils.jumpToPanel(rootPane, "main"));
		middlePane.add(backButton, c);
	}

	private JPanel createSearchPanel() {
		// set default date to today
		LocalDate today = LocalDate.now();

		JPanel searchBox = new JPanel();
		GroupLayout layout = new GroupLayout(searchBox);
		searchBox.setLayout(layout);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);

		// Date Picker
		JLabel dateLabel = new JLabel("Date");

		dateSettings = new DatePickerSettings();
		datePicker = new DatePicker(dateSettings);

		dateSettings.setDateRangeLimits(today, today.plusYears(DEFAULTDATEYEARRANGE));
		dateSettings.setAllowEmptyDates(false);

		// Time Picker
		// Start Time
		JLabel startTimeLabel = new JLabel("Start Time");

		startTimeSettings = new TimePickerSettings();
		startTimeSettings.use24HourClockFormat();
		startTimeSettings.setAllowEmptyTimes(false);
		startTimeSettings.generatePotentialMenuTimes(TimeIncrement.TenMinutes, getCurTime(), null);
		startTimeSettings.initialTime = getCurTime();
		startTimePicker = new TimePicker(startTimeSettings);

		// End Time
		JLabel endTimeLabel = new JLabel("End Time");

		endTimeSettings = new TimePickerSettings();
		endTimeSettings.use24HourClockFormat();
		endTimeSettings.setAllowEmptyTimes(false);
		endTimeSettings.generatePotentialMenuTimes(TimeIncrement.TenMinutes, getCurTime().plusMinutes(10), null);
		endTimeSettings.initialTime = getCurTime().plusMinutes(10);
		endTimePicker = new TimePicker(endTimeSettings);

		// Setting action listener for adjusting end time based on start time
		startTimePicker.addTimeChangeListener(e -> {
			LocalTime selectedTime = startTimePicker.getTime().plusMinutes(10);
			endTimeSettings.generatePotentialMenuTimes(TimeIncrement.TenMinutes, selectedTime, null);
			if (endTimePicker.getTime().isBefore(startTimePicker.getTime())) {
				endTimePicker.setTime(selectedTime);
			}
		});
		
		// Setting action listener for adjusting start time based on date
		datePicker.addDateChangeListener(e ->{
			if (datePicker.getDate().equals(today)) { // set to cur date
				startTimeSettings.generatePotentialMenuTimes(TimeIncrement.TenMinutes, getCurTime(), null);
				startTimePicker.setTime(getCurTime());
				endTimeSettings.generatePotentialMenuTimes(TimeIncrement.TenMinutes, startTimePicker.getTime().plusMinutes(10), null);
				endTimePicker.setTime(getCurTime().plusMinutes(10));
			}else { // set to a new date
				startTimeSettings.generatePotentialMenuTimes(TimeIncrement.TenMinutes, null, null);
				startTimePicker.setTime(LocalTime.MIDNIGHT);
				endTimeSettings.generatePotentialMenuTimes(TimeIncrement.TenMinutes, startTimePicker.getTime().plusMinutes(10), null);
				endTimePicker.setTime(LocalTime.MIDNIGHT.plusMinutes(10));
			}
		});

		// Capacity
		JLabel capacityLabel = new JLabel("Capacity");
		capacity = GuiUtils.getNumTextField(5);

		// Name
		JLabel nameLabel = new JLabel("Name");
		nameField = new JTextField(50);

		// Facility
		JLabel facilityLabel = new JLabel("Facility");
		List<Facility> facilities = facilityAction.findAllFacilities();
		DefaultListModel<Facility> model = new DefaultListModel<>();
		for (Facility facility : facilities) {
			model.addElement(facility);
		}
		facilityList = new JList<>(model);
		// enables clicking multiple items
		facilityList.setSelectionModel(new DefaultListSelectionModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public void setSelectionInterval(int index0, int index1) {
				if (super.isSelectedIndex(index0)) {
					super.removeSelectionInterval(index0, index1);
				} else {
					super.addSelectionInterval(index0, index1);
				}
			}
		});

		// Show Booked Rooms for High Priority Users
		JLabel overrideLabel = new JLabel();
		// if (SecurityUtils.getSubject().hasRole("High")) {
		if (user.getUserGroup() < 2) {
			overrideLabel = new JLabel("Show Overridable Rooms");
			showBookedRooms = new JCheckBox();
		}
		// Search Button
		searchButton = getSearchButton();

		// create panel
		ParallelGroup hGroup = layout.createParallelGroup(Alignment.CENTER)
				.addGroup(layout.createSequentialGroup().addComponent(dateLabel).addComponent(datePicker)
						.addComponent(startTimeLabel).addComponent(startTimePicker).addComponent(endTimeLabel)
						.addComponent(endTimePicker))
				.addGroup(layout.createSequentialGroup().addComponent(capacityLabel).addComponent(capacity)
						.addComponent(nameLabel).addComponent(nameField));
		// manage override CheckBox
		// if (SecurityUtils.getSubject().hasRole("High")) {
		if (user.getUserGroup() < 2) {
			hGroup.addGroup(layout.createSequentialGroup().addComponent(facilityLabel).addComponent(facilityList)
					.addComponent(overrideLabel).addComponent(showBookedRooms).addComponent(searchButton));
		} else {
			hGroup.addGroup(layout.createSequentialGroup().addComponent(facilityLabel).addComponent(facilityList)
					.addComponent(searchButton));
		}
		layout.setHorizontalGroup(hGroup);

		SequentialGroup vGroup = layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(dateLabel)
						.addComponent(datePicker).addComponent(startTimeLabel).addComponent(startTimePicker)
						.addComponent(endTimeLabel).addComponent(endTimePicker))
				.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(capacityLabel)
						.addComponent(capacity).addComponent(nameLabel).addComponent(nameField));

		// manage override CheckBox
		// if (SecurityUtils.getSubject().hasRole("High")) {
		if (user.getUserGroup() < 2) {
			vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(facilityLabel)
					.addComponent(facilityList).addComponent(overrideLabel).addComponent(showBookedRooms)
					.addComponent(searchButton));
		} else {
			vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(facilityLabel)
					.addComponent(facilityList).addComponent(searchButton));
		}
		layout.setVerticalGroup(vGroup);

		return searchBox;
	}

	@Override
	// Reset when exit
	public void exitPanel() {
		LocalDate today = LocalDate.now();
		dateSettings.setDateRangeLimits(today, today.plusYears(DEFAULTDATEYEARRANGE));
		datePicker.setDateToToday();
		startTimePicker.setTime(getCurTime());
		endTimePicker.setTime(getCurTime().plusMinutes(10));
		capacity.setText("");
		nameField.setText("");
		facilityList.clearSelection();
		roomPane.reset();
	}

	private JButton getSearchButton() {
		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(e -> searchRoom());
		return searchButton;
	}
	
	public void searchRoom(SearchRoomConstraint src) {
		// search from database
		List<Room> roomList = roomAction.searchRooms(src);
		List<Object[]> reservedRoomList = showBookedRooms != null && showBookedRooms.isSelected()
				? roomAction.searchReservedRooms(src)
				: new ArrayList<>();
		// build table
		if (roomList.isEmpty() && reservedRoomList.isEmpty()) {
			if (alert)
				JOptionPane.showMessageDialog(null, "No rooms with your requirements found. Please Try Again.");
		} else {
			List<Object[]> rows = new ArrayList<>();
			Object[] rowName = new Object[] { "Room Name", "Capacity", "Facilities", "Action" };

			// Room name, Capacities, Facilities, Reserve button
			for (Room room : roomList) {
				Object[] row = new Object[4];
				row[0] = room.getName();
				row[1] = room.getCapacity();
				ArrayList<String> facilities = new ArrayList<>();
				for (Facility f : room.getFacilities()) {
					facilities.add(f.getName());
				}
				row[2] = String.join(", ", facilities);
				JButton reserveButton = getReserveButton(room, src);
				row[3] = reserveButton;
				rows.add(row);
			}
			// Room name, Override button
			for (Object[] result : reservedRoomList) {
				Room room = (Room) result[0];
				int id = (int) result[1];
				Object[] row = new Object[4];
				row[0] = room.getName();
				row[1] = room.getCapacity();
				ArrayList<String> facilities = new ArrayList<>();
				for (Facility f : room.getFacilities()) {
					facilities.add(f.getName());
				}
				row[2] = String.join(", ", facilities);
				JButton overrideButton = getOverrideButton(src, id);
				row[3] = overrideButton;
				rows.add(row);
			}
			roomPane.populateList(rowName, rows, "Action");
		}
	}
	
	
	
	
	
	private void searchRoom() {
		// reset
		roomPane.reset();
		// build up search constraint
		SearchRoomConstraint src = new SearchRoomConstraint();
		String selectedDate = datePicker.getDateStringOrEmptyString();
		String startTime = startTimePicker.getTimeStringOrEmptyString();
		String endTime = endTimePicker.getTimeStringOrEmptyString();

		// Parse
		try {
			src.setCapacity(capacity.getText().length() == 0 ? 0 : Integer.parseInt(capacity.getText()));
			src.setEventDate(new SimpleDateFormat("yyyy-MM-dd").parse(selectedDate));
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			src.setStartTime(sdf.parse(startTime));
			src.setEndTime(sdf.parse(endTime));
			src.setRoomName(nameField.getText());
			src.getFacilities().addAll(facilityList.getSelectedValuesList());
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		searchRoom(src);
	}
	

	private JButton getReserveButton(Room room, SearchRoomConstraint src) {
		JButton reserveButton = new JButton("Reserve");
		reserveButton.addActionListener(e -> {
			// convert room to reservation
			Reservation reservation = EntityUtils.roomToReservation(room, src.getEventDate(), src.getStartTime(),
					src.getEndTime(), user.getId());
			// reserve
			boolean success = reservationAction.reserveRoom(reservation);
			if (success) {
				if (alert)
					JOptionPane.showMessageDialog(null, "Success!");
				reserveButton.setEnabled(false);
			} else {
				if (alert)
					JOptionPane.showMessageDialog(null,
							"There is something wrong with making the reservation. Please Try Again.");
			}
		});
		return reserveButton;
	}

	private JButton getOverrideButton(SearchRoomConstraint src, int id) {
		JButton overrideButton = new JButton("Override");
		overrideButton.addActionListener(e -> {
			// convert room to reservation
			Reservation reservation = reservationAction.getReservationById(id);
			User oldUser = userAction.findUserById(reservation.getUserId());
			// reserve
			boolean success = reservationAction.overrideRoom(reservation, src.getEventDate(), src.getStartTime(),
					src.getEndTime(), user.getId());
			if (success) {
				if (alert)
					JOptionPane.showMessageDialog(null, "Success!");
				overrideButton.setEnabled(false);
				// send email
				try {
					EmailSender.sendEmail(oldUser, reservation, roomAction);
				} catch (MessagingException e1) {
					e1.printStackTrace();
				}
			} else {
				if (alert)
					JOptionPane.showMessageDialog(null,
							"There is something wrong with overriding the reservation. Please Try Again.");
			}
		});
		return overrideButton;
	}

	private LocalTime getCurTime() {
		LocalTime now = LocalTime.now();
		int minuteDiff = now.getMinute() % 10 == 0 ? 0 : 10 - now.getMinute() % 10;
		return now.plusMinutes(minuteDiff);
	}

}
