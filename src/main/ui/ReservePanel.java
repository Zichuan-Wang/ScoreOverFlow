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

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.components.TimePickerSettings.TimeIncrement;

import entity.EntityUtils;
import entity.Reservation;
import entity.Room;
import server.action.ReservationAction;
import server.action.RoomAction;
import server.constraint.SearchRoomConstraint;

public class ReservePanel extends BasePanel {
	/**
	 * Default serial version id
	 */
	private static final long serialVersionUID = 1L;
	private final static String TITLE = "Reserve a Room";

	private final LocalTime DEFAULTSTARTTIME = LocalTime.of(0, 0);
	private final LocalTime DEFAULTENDTIME = LocalTime.of(0, 10);
	private final int DEFAULTCAPACITY = 1;
	private final int DEFAULTDATEYEARRANGE = 1;

	private DatePickerSettings dateSettings;
	private DatePicker datePicker;
	private TimePickerSettings startTimeSettings;
	private TimePicker startTimePicker;
	private TimePickerSettings endTimeSettings;
	private TimePicker endTimePicker;
	private JFormattedTextField capacity;
	private JTextField nameField;
	private JButton searchButton, backButton;
	private TablePanel roomPane;

	private ReservationAction reservationAction;
	private RoomAction roomAction;

	public ReservePanel(JPanel cards, ReservationAction reservationAction, RoomAction roomAction) {
		super(TITLE, cards);
		this.reservationAction = reservationAction;
		this.roomAction = roomAction;
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
		backButton = GuiUtils.getBackButton(this, cards);
		middlePane.add(backButton, c);
		return middlePane;
	}

	private JPanel createSearchPanel() {
		// set default date to today
		LocalDate today = LocalDate.now();

		JPanel searchPane = new JPanel();

		// Date Picker
		JLabel dateLabel = new JLabel("Date");
		searchPane.add(dateLabel);

		dateSettings = new DatePickerSettings();
		datePicker = new DatePicker(dateSettings);
		dateSettings.setDateRangeLimits(today, today.plusYears(DEFAULTDATEYEARRANGE));
		dateSettings.setAllowEmptyDates(false);
		searchPane.add(datePicker);

		// Time Picker

		// Start Time
		JLabel startTimeLabel = new JLabel("Start Time");
		searchPane.add(startTimeLabel);

		startTimeSettings = new TimePickerSettings();
		startTimeSettings.use24HourClockFormat();
		startTimeSettings.setAllowEmptyTimes(false);
		startTimeSettings.generatePotentialMenuTimes(TimeIncrement.TenMinutes, null, null);
		startTimeSettings.initialTime = LocalTime.of(0, 0);

		startTimePicker = new TimePicker(startTimeSettings);
		searchPane.add(startTimePicker);

		// End Time
		JLabel endTimeLabel = new JLabel("End Time");
		searchPane.add(endTimeLabel);

		endTimeSettings = new TimePickerSettings();
		endTimeSettings.use24HourClockFormat();
		endTimeSettings.setAllowEmptyTimes(false);
		endTimeSettings.initialTime = DEFAULTENDTIME;
		endTimeSettings.generatePotentialMenuTimes(TimeIncrement.TenMinutes, null, null);
		endTimeSettings.initialTime = LocalTime.of(0, 10);

		endTimePicker = new TimePicker(endTimeSettings);
		searchPane.add(endTimePicker);

		// Setting action listener for adjusting end time based on start time
		startTimePicker.addTimeChangeListener(e -> {
			LocalTime selectedTime = startTimePicker.getTime().plusMinutes(10);
			endTimeSettings.generatePotentialMenuTimes(TimeIncrement.TenMinutes, selectedTime, null);
			if (endTimePicker.getTime().isBefore(startTimePicker.getTime())) {
				endTimePicker.setTime(selectedTime);
			}
		});

		// Capacity
		JLabel capacityLabel = new JLabel("Capacity");
		searchPane.add(capacityLabel);

		capacity = new JFormattedTextField(GuiUtils.getNumberFormatter(0, 1000));
		capacity.setValue(DEFAULTCAPACITY);
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
		LocalDate today = LocalDate.now();
		dateSettings.setDateRangeLimits(today, today.plusYears(DEFAULTDATEYEARRANGE));
		datePicker.setDateToToday();
		startTimePicker.setTime(DEFAULTSTARTTIME);
		endTimePicker.setTime(DEFAULTENDTIME);
		capacity.setValue(DEFAULTCAPACITY);
		nameField.setText("");
	}

	@Override
	public void showPanel() {
	}

	private JButton getSearchButton() {
		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(e -> {
			SearchRoomConstraint src = new SearchRoomConstraint();

			String selectedDate = datePicker.getDateStringOrEmptyString();
			String startTime = startTimePicker.getTimeStringOrEmptyString();
			String endTime = endTimePicker.getTimeStringOrEmptyString();

			// Parse
			try {
				src.setCapacity(Integer.parseInt(capacity.getText()));
				src.setEventDate(new SimpleDateFormat("yyyy-MM-dd").parse(selectedDate));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				src.setStartTime(sdf.parse(selectedDate + " " + startTime));
				src.setEndTime(sdf.parse(selectedDate + " " + endTime));
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			src.setRoomName(nameField.getText());
			// search from database
			List<Room> roomList = roomAction.searchRoom(src);
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
		});
		return searchButton;
	}

	private JButton getReserveButton(Room room, SearchRoomConstraint src) {
		JButton reserveButton = new JButton("Reserve");
		reserveButton.addActionListener(e -> {
			// convert room to reservation
			Reservation reservation = EntityUtils.roomToReservation(room, src.getEventDate(), src.getStartTime(),
					src.getEndTime(), 0);
			// reserve
			boolean success = reservationAction.reserveRoom(reservation);
			// @TODO handling success and failure
			if (success) {
				JOptionPane.showMessageDialog(null, "Success!");
				reserveButton.setEnabled(false);
			} else {
				JOptionPane.showMessageDialog(null, "There is something wrong with the reservation. Please Try Again.");
			}
		});
		return reserveButton;
	}

}
