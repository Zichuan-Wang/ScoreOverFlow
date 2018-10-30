package ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePickerImpl;

import dao.RoomDao;
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
	private String[] timeString = new String[6 * 24];
	private RoomDao myDao;

	private JDatePickerImpl datePicker;
	private JComboBox<String> startTime, endTime;
	private JFormattedTextField capacity;
	private JTextField nameField;
	private JButton searchButton, backButton;
	private TablePanel roomPane;

	public ReservePanel(JPanel cards) {
		super(TITLE);

		initTimeString();

		// middle Panel
		JPanel middlePane = new JPanel();

		middlePane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JPanel searchPane = createSearchPanel();
		searchPane.add(searchButton);

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
		backButton = GuiUtils.getJumpCardButton(cards, "back", "main");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		middlePane.add(backButton, c);

		setMiddlePanel(middlePane);
	}

	// Reset when exit
	public void reset() {
		startTime.setSelectedIndex(0);
		endTime.setSelectedIndex(0);
		capacity.setValue(100);
		nameField.setText("");

	}

	private void initTimeString() {
		int i = 0;
		for (int hour = 0; hour < 24; hour++) {
			for (int minute = 0; minute < 60; minute += 10) {
				timeString[i++] = LocalTime.of(hour, minute).toString();
			}
		}
	}

	private JPanel createSearchPanel() {
		// Date
		JPanel searchPane = new JPanel();
		JLabel dateLabel = new JLabel("Date");
		searchPane.add(dateLabel);

		datePicker = GuiUtils.getDatePicker();
		searchPane.add(datePicker);

		searchPane.add(Box.createRigidArea(new Dimension(6, 0)));

		// Time
		JLabel startTimeLabel = new JLabel("Start Time");
		searchPane.add(startTimeLabel);
		startTime = new JComboBox<>(timeString);
		searchPane.add(startTime);

		JLabel endTimeLabel = new JLabel("End Time");
		searchPane.add(endTimeLabel);
		endTime = new JComboBox<>(timeString);
		searchPane.add(endTime);

		// Capacity
		JLabel capacityLabel = new JLabel("Capacity");
		searchPane.add(capacityLabel);

		capacity = new JFormattedTextField(GuiUtils.getNumberFormatter());
		capacity.setValue(new Integer(100));
		searchPane.add(capacity);

		// Name
		JLabel nameLabel = new JLabel("Name");
		searchPane.add(nameLabel);
		nameField = new JTextField(10);
		searchPane.add(nameField);

		// Search Button
		searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SearchRoomConstraint src = new SearchRoomConstraint();
				// @TODO TRY CATCH
				src.setCapacity(Integer.parseInt(capacity.getText()));

				String selectedDate = datePicker.getJFormattedTextField().getText();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				try {
					src.setEventDate(new SimpleDateFormat("yyyy-MM-dd").parse(selectedDate));
					src.setStartTime(sdf.parse(selectedDate + " " + startTime.getSelectedItem()));
					src.setEndTime(sdf.parse(selectedDate + " " + endTime.getSelectedItem()));
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				src.setRoomName(nameField.getText());
				// search from database
				try {
					myDao = RoomDaoFactory.getInstance();
				} catch (DBConnectionException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				List<Room> roomList = myDao.searchRooms(src);

				List<Object[]> rows = new ArrayList<>();
				for (Room room : roomList) {
					Object[] row = new Object[2];
					row[0] = room.getName();
					JButton reserveButton = new JButton("Reserve");
					reserveButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							// convert room to reservation
							Reservation reservation = EntityUtils.roomToReservation(room, src.getEventDate(),
									src.getStartTime(), src.getEndTime(), 0);
							try {
								boolean success = ReserveRoomAction.reserveRoom(reservation);
							} catch (DBConnectionException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							// @TODO failure and success handling
						}
					});
					row[1] = reserveButton;
					rows.add(row);
				}
				roomPane.populateList(rows);
			}
		});
		return searchPane;
	}
}
