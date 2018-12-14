package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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

public class BatchPanel extends BasePanel {
	private static final long serialVersionUID = 1L;
	private static final String TITLE = "Reserve Rooms in Batch";
	private static final String TEMPLATE = "\"room id\", \"date\", \"start time\", \"end time\"\n6, 5234564545, 63000, 70300\n6, 6456784546, 42000, 60040";
	private User user;

	private JButton uploadFileButton, downloadFileButton, backButton;

	// private UserAction userAction;
	private ReservationAction reservationAction;
	// private RoomAction roomAction;
	// private FacilityAction facilityAction;

	public BatchPanel(JPanel cards, User user, ReservationAction reservationAction) {
		super(cards, TITLE);
		this.user = user;
		this.reservationAction = reservationAction;
		setMiddlePanel();
		
		
	}

	private void setMiddlePanel() {
        middlePane.setLayout(new GridBagLayout()); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
		
        gbc.gridx = 0;
        gbc.gridy = 0;
		uploadFileButton = getUploadFileButton();
		middlePane.add(uploadFileButton, gbc);
		
		gbc.gridx = 0;
        gbc.gridy = 1;
		downloadFileButton = getDownloadFileButton();
		middlePane.add(downloadFileButton, gbc);
		
        gbc.gridx = 0;
        gbc.gridy = 2;
		backButton = GuiUtils.createButton("Back", e -> GuiUtils.jumpToPanel(rootPane, "main"));
		middlePane.add(backButton, gbc);
	}
	
	private JButton getDownloadFileButton() {
		JButton button = GuiUtils.createButton("Download Template");
		
		button.addActionListener(e -> {
			JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			fileChooser.setDialogTitle("Select Directory for Download");
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fileChooser.setAcceptAllFileFilterUsed(false);
			int option = fileChooser.showOpenDialog(null);
			if (option != JFileChooser.APPROVE_OPTION) {
				return
						;
			}
			
			int probe = 0;
			String filename = "/reservations";
			String extension = ".csv";
			
			File file = new File(fileChooser.getSelectedFile().getAbsolutePath() + filename + extension);
			
			while (true) {
				try {
					if (file.createNewFile()) {
						FileWriter writer = new FileWriter(file);
						writer.write(TEMPLATE);
						writer.close();
						return;
					} else {
					    probe++;
					    file = new File(fileChooser.getSelectedFile().getAbsolutePath() + filename + "(" + probe + ")" + extension);
					}
				} catch (IOException exception) {
					exception.printStackTrace();
					break;
				}
			}
		});
		
		return button;
	}

	private JButton getUploadFileButton() {
		JButton button = GuiUtils.createButton("Upload File");

		button.addActionListener(e -> {
			// prompt the window to choose a csv file
			JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			fileChooser.setDialogTitle("Select File to Upload");
			int option = fileChooser.showOpenDialog(null);
			if (option != JFileChooser.APPROVE_OPTION) {
				return;
			}

			File selectedFile = fileChooser.getSelectedFile();
			// System.out.println(selectedFile.getAbsolutePath());

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
					try {
						Reservation reservation = new Reservation().setUserId(user.getId())
								.setRoomId(Integer.parseInt(groups[0].trim()))
								.setEventDate(new Date(Long.parseLong(groups[1].trim())))
								.setStartTime(new Time(Integer.parseInt(groups[2].trim())))
								.setEndTime(new Time(Integer.parseInt(groups[3].trim())));
						reservations.add(reservation);
					} catch (NumberFormatException nfe) {
						continue;
					}
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
