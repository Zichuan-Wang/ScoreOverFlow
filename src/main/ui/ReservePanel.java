package ui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.NumberFormatter;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import dao.ReservationDao;
import dao.RoomDao;
import entity.Reservation;
import entity.Room;
import exception.DBConnectionException;
import server.constraint.SearchReservationConstraint;
import server.constraint.SearchRoomConstraint;

public class ReservePanel extends JPanel{
	private String[] timeString = new String[6*24];
	private RoomDao myDao;
	
	private JDatePickerImpl datePicker;
	private JComboBox<String> startTime,endTime;
	private JFormattedTextField capacity;
	private JTextField nameField;
	private JButton searchButton,backButton;
	private TablePanel roomPane = new TablePanel();
	
	public ReservePanel() {
		
	}
	public ReservePanel(JPanel contentPane) {
		initTimeString();
		
		try {
			myDao = new RoomDao();
		} catch (DBConnectionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JLabel dateLabel = new JLabel("Date");
		add(dateLabel);
		
		
		datePicker = GUIUtil.getDatePicker();
		add(datePicker);

        add(Box.createRigidArea(new Dimension(6, 0)));
        
        JLabel startTimeLabel = new JLabel("Start Time");
		add(startTimeLabel);
        startTime = new JComboBox<>(timeString);
        add(startTime);
        
        JLabel endTimeLabel = new JLabel("End Time");
		add(endTimeLabel);
        endTime = new JComboBox<>(timeString);
        add(endTime);
        
        JLabel capacityLabel = new JLabel("Capacity");
        add(capacityLabel);
            
        capacity = new JFormattedTextField(GUIUtil.getNumberFormatter());
        capacity.setValue(new Integer(100));
        add(capacity);
        
        JLabel nameLabel = new JLabel("Name");
        add(nameLabel);
        nameField = new JTextField(10);
        add(nameField);
        
        searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				roomPane.clear();
				SearchRoomConstraint src = new SearchRoomConstraint();
				//TRY CATCH
				src.setCapacity(Integer.parseInt(capacity.getText()));
				
				String selectedDate = datePicker.getJFormattedTextField().getText();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				try {
					src.setEventDate(new SimpleDateFormat("yyyy-MM-dd").parse(selectedDate));
					src.setStartTime(sdf.parse(selectedDate+" "+startTime.getSelectedItem()));
					src.setEndTime(sdf.parse(selectedDate+" "+endTime.getSelectedItem()));
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				src.setRoomName(nameField.getText());
				// search from database
				List<Room> roomList = myDao.searchRooms(src);
				
				List<String> roomNameList = new ArrayList<>();
				for (Room room: roomList) {
					roomNameList.add(room.getName());
				}
				roomPane.displayList(roomNameList,"Reserve");
				revalidate();
				repaint();
			}
		});
        
        add(searchButton);
        
        add(Box.createRigidArea(new Dimension(1080, 2)));
        
        roomPane = new TablePanel();
        JScrollPane scroll = new JScrollPane(roomPane);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.
		                                   VERTICAL_SCROLLBAR_ALWAYS);
		add(scroll);
        
		backButton = GUIUtil.getJumpCardButton(contentPane, "back","main");
		add(backButton);
	}

	public void reset() {
		startTime.setSelectedIndex(0);
		endTime.setSelectedIndex(0);
		capacity.setValue(100);
		nameField.setText("");
		roomPane.clear();
	}
	private void initTimeString() {
        int i = 0;
        for(int hour=0;hour<24;hour++) {
        	for (int minute=0;minute<60;minute+=10) {
        		timeString[i++] = LocalTime.of(hour, minute).toString();
        	}
        }
	}
	
}
