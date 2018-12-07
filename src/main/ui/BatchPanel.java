package ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileSystemView;

import entity.Reservation;
import entity.User;
import server.action.ReservationAction;


public class BatchPanel extends BasePanel{
	private static final long serialVersionUID = 1L;
	private final static String TITLE = "Reserve Rooms in Batch";
	private User user;
	
	private JButton uploadFileButton;

	//private UserAction userAction;
	private ReservationAction reservationAction;
	//private RoomAction roomAction;
	//private FacilityAction facilityAction;
	
	public BatchPanel(JPanel cards, User user, ReservationAction reservationAction) {
		super(cards,TITLE);
		this.user = user;
		this.reservationAction = reservationAction;
		setMiddlePanel();
	}
	
	/*
	public BatchPanel(JPanel cards, User user, UserAction userAction, ReservationAction reservationAction,
			RoomAction roomAction, FacilityAction facilityAction) {
		super(cards,TITLE);
		this.user = user;
		this.userAction = userAction;
		this.reservationAction = reservationAction;
		this.roomAction = roomAction;
		this.facilityAction = facilityAction;
		setMiddlePanel();
	}
	*/
	
	private void setMiddlePanel() {
		uploadFileButton = getUploadFileButton();
		middlePane.add(uploadFileButton);
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
			// Reference:
			// https://www.mkyong.com/java/how-to-read-and-parse-csv-file-in-java/
			String csvFile = selectedFile.getAbsolutePath();
			BufferedReader br = null;
			String line = "";
			String cvsSplitBy = ",";

			List<Reservation> reservations = new ArrayList<>();

			try {
				br = new BufferedReader(new FileReader(csvFile));
				while ((line = br.readLine()) != null) {
					/*
					 * Each line should contain: room ID, date, start time, and end time, in the
					 * exact order.
					 */
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
	
	
}
