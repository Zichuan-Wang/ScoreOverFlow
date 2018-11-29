package ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileSystemView;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.components.TimePickerSettings.TimeIncrement;

import entity.EntityUtils;
import entity.Facility;
import entity.Reservation;
import entity.Room;
import entity.User;
import server.action.FacilityAction;
import server.action.ReservationAction;
import server.action.RoomAction;
import server.constraint.SearchRoomConstraint;

public class ReservePanel extends BasePanel {
	private static final long serialVersionUID = 1L;

	private final static String TITLE = "Reserve a Room";
	private final int DEFAULTCAPACITY = 1;
	private final int DEFAULTDATEYEARRANGE = 1;

	private User user;
	private DatePickerSettings dateSettings;
	private DatePicker datePicker;
	private TimePickerSettings startTimeSettings;
	private TimePicker startTimePicker;
	private TimePickerSettings endTimeSettings;
	private TimePicker endTimePicker;
	private JFormattedTextField capacity;
	private JTextField nameField;
	private JList<Facility> facilityList;
	private JCheckBox showBookedRooms;
	private JButton searchButton, backButton, uploadFileButton;
	private TablePanel roomPane;
	private TablePanel buttonPane;

	private ReservationAction reservationAction;
	private RoomAction roomAction;
	private FacilityAction facilityAction;
	
	private boolean alert = true;

	public ReservePanel(JPanel cards, User user, ReservationAction reservationAction, RoomAction roomAction, FacilityAction facilityAction) {
		super(TITLE, cards);
		this.user = user;
		this.reservationAction = reservationAction;
		this.roomAction = roomAction;
		this.facilityAction = facilityAction;
		initPanels();
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
		uploadFileButton = getUploadFileButton();
		
		buttonPane = new TablePanel();
		buttonPane.setPreferredSize(new Dimension(600, 200));
		
		buttonPane.add(backButton, c);
		backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPane.add(uploadFileButton, c);
		uploadFileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		middlePane.add(buttonPane, c);
		

		return middlePane;
	}
	
	public void setAlert(boolean changedAlertState) {
		alert = changedAlertState;
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
		startTimeSettings.initialTime = getCurTime();
		
		startTimePicker = new TimePicker(startTimeSettings);
		searchPane.add(startTimePicker);

		// End Time
		JLabel endTimeLabel = new JLabel("End Time");
		searchPane.add(endTimeLabel);

		endTimeSettings = new TimePickerSettings();
		endTimeSettings.use24HourClockFormat();
		endTimeSettings.setAllowEmptyTimes(false);
		endTimeSettings.generatePotentialMenuTimes(TimeIncrement.TenMinutes, null, null);
		endTimeSettings.initialTime = getCurTime().plusMinutes(10);
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
		// capacity.setValue(DEFAULTCAPACITY);
		capacity.setColumns(3);
		searchPane.add(capacity);

		// Name
		JLabel nameLabel = new JLabel("Name");
		searchPane.add(nameLabel);
		nameField = new JTextField(10);
		searchPane.add(nameField);
		
		// Facility
		JLabel facilityLabel = new JLabel("Facility");
		searchPane.add(facilityLabel);
		List<Facility> facilities = facilityAction.findAllFacilities();
		DefaultListModel<Facility> model = new DefaultListModel<>();
		for (Facility facility : facilities) {
			model.addElement(facility);
		}
		facilityList = new JList<>(model);
		facilityList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		searchPane.add(facilityList);

		// Show Booked Rooms for High Priority Users
		if(user.getUserGroup() == 1) {
			showBookedRooms = new JCheckBox();
			searchPane.add(showBookedRooms);
		}
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
		startTimePicker.setTime(getCurTime());
		endTimePicker.setTime(getCurTime().plusMinutes(10));
		capacity.setValue(DEFAULTCAPACITY);
		nameField.setText("");
		facilityList.clearSelection();
		roomPane.reset();
	}

	@Override
	public void showPanel() {
	}

	private JButton getSearchButton() {
		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(e -> {
			//reset
			roomPane.reset();
			// build up search constraint
			SearchRoomConstraint src = new SearchRoomConstraint();
			String selectedDate = datePicker.getDateStringOrEmptyString();
			String startTime = startTimePicker.getTimeStringOrEmptyString();
			String endTime = endTimePicker.getTimeStringOrEmptyString();

			// Parse
			try {
				src.setCapacity(capacity.getText().length() == 0 ? 0 : Integer.parseInt(capacity.getText())); // if no
																												// string
																												// specified,
																												// set 0
				src.setEventDate(new SimpleDateFormat("yyyy-MM-dd").parse(selectedDate));
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				src.setStartTime(sdf.parse(startTime));
				src.setEndTime(sdf.parse(endTime));
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			src.setRoomName(nameField.getText());
			src.getFacilities().addAll(facilityList.getSelectedValuesList());
			
			// search from database
			List<Room> roomList = roomAction.searchRooms(src);
			
			List<Room> reservedRoomList = new ArrayList<>();
			if(showBookedRooms != null && showBookedRooms.isSelected()) {
				reservedRoomList = roomAction.searchReservedRooms(src);
			}
			// build table
			if (roomList.isEmpty() && reservedRoomList.isEmpty()) {
				if (alert)
					JOptionPane.showMessageDialog(null, "No rooms with your requirements found. Please Try Again.");
			}else {
				List<Object[]> rows = new ArrayList<>();
				Object[] rowName = new Object[] { "Room Name", "Action" };
				
				// Room name, Reserve button
				for (Room room : roomList) {
					Object[] row = new Object[2];
					row[0] = room.getName();
					JButton reserveButton = getReserveButton(room, src);
					row[1] = reserveButton;
					rows.add(row);
				}
				// Room name, Override button
				for (Room room : reservedRoomList) {
					Object[] row = new Object[2];
					row[0] = room.getName();
					JButton overrideButton = getOverrideButton(room, src);
					row[1] = overrideButton;
					rows.add(row);
				}
				roomPane.populateList(rowName, rows, "Action");
			}
		});
		return searchButton;
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
					JOptionPane.showMessageDialog(null, "There is something wrong with making the reservation. Please Try Again.");
			}
		});
		return reserveButton;
	}
	
	private JButton getOverrideButton(Room room, SearchRoomConstraint src) {
		JButton overrideButton = new JButton("Override");
		overrideButton.addActionListener(e -> {
			// convert room to reservation
			Reservation reservation = EntityUtils.roomToReservation(room, src.getEventDate(), src.getStartTime(),
					src.getEndTime(), user.getId());
			// reserve
			boolean success = reservationAction.overrideRoom(reservation, src.getEventDate(), src.getStartTime(),
					src.getEndTime(), user.getId());
			if (success) {
				if (alert)
					JOptionPane.showMessageDialog(null, "Success!");
				overrideButton.setEnabled(false);
			} else {
				if (alert)
					JOptionPane.showMessageDialog(null, "There is something wrong with overriding the reservation. Please Try Again.");
			}
		});
		return overrideButton;
	}
	
	private JButton getUploadFileButton() {
		JButton button = GuiUtils.createButton("Upload");
		
		button.addActionListener(e -> {
			// prompt the window to choose a csv file
			JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			int option = fileChooser.showOpenDialog(null);
			if (option != JFileChooser.APPROVE_OPTION) {
				return;
			}
			
			File selectedFile = fileChooser.getSelectedFile();
			System.out.println(selectedFile.getAbsolutePath());
			
			// start parsing from the csv file
			// Reference: https://www.mkyong.com/java/how-to-read-and-parse-csv-file-in-java/
			String csvFile = selectedFile.getAbsolutePath();
	        BufferedReader br = null;
	        String line = "";
	        String cvsSplitBy = ",";
	        
	        List<Reservation> reservations = new ArrayList<>();
	        
	        try {
	            br = new BufferedReader(new FileReader(csvFile));
	            while ((line = br.readLine()) != null) {
	            	/* Each line should contain: 
	                 * room ID, date, start time, and end time, 
	                 * in the exact order. */
	                String[] groups = line.split(cvsSplitBy);
	                Reservation reservation = new Reservation().setUserId(user.getId())
	                		.setRoomId(Integer.parseInt(groups[0].trim()))
	                		.setEventDate(new Date(Integer.parseInt(groups[1].trim())))
	                		.setStartTime(new Time(Integer.parseInt(groups[2].trim())))
	                		.setEndTime(new Time(Integer.parseInt(groups[3].trim())));
	                reservations.add(reservation);
	            }
	            
	            // make the batch reservation request
	            reservationAction.reserveMultipleRooms(reservations);
	        } catch (FileNotFoundException exception) {
	        	exception.printStackTrace();
	        } catch (IOException exception) {
	        	exception.printStackTrace();
	        } finally {
	            if (br != null) {
	                try {
	                    br.close();
	                } catch (IOException exception) {
	                	exception.printStackTrace();
	                }
	            }
	        }
		});
		
		return button;
	}
	
	private LocalTime getCurTime() {
		LocalTime now = LocalTime.now();
		int minuteDiff = now.getMinute()%10 == 0? 0: 10 - now.getMinute()%10;
		return now.plusMinutes(minuteDiff);
	}

}
