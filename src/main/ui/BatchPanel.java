package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileSystemView;

import entity.Reservation;
import entity.Facility;
import entity.User;
import server.action.ReservationAction;
import server.action.RoomAction;
import server.constraint.SearchRoomConstraint;
import server.action.FacilityAction;

public class BatchPanel extends BasePanel {
	private static final long serialVersionUID = 1L;
	private static final String TITLE = "Reserve Rooms in Batch";
	private static final String TEMPLATE = "\"room id\", \"date\", \"start time\", \"end time\"\n6, 5234564545, 63000, 70300\n6, 6456784546, 42000, 60040";
	private User user;

	private JButton uploadFileButton, downloadFileButton;

	// private UserAction userAction;
	private ReservationAction reservationAction;
	private RoomAction roomAction;
	private FacilityAction facilityAction;

	public BatchPanel(JPanel cards, User user, FacilityAction facilityAction, RoomAction roomAction, ReservationAction reservationAction) {
		super(cards, TITLE);
		this.user = user;
		this.facilityAction = facilityAction;
		this.roomAction = roomAction;
		this.reservationAction = reservationAction;
		setMiddlePanel();
		if (this.user == null) {
			System.out.println("");
		}
		if (this.roomAction == null) {
			System.out.println("");
		}
		setBackButtonBottomPanel();
		
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
			List<SearchRoomConstraint> srcs = new ArrayList<>();
			List<Facility> facilities = facilityAction.findAllFacilities();
			int lineNum = -2;
			List<Integer> failedLines = new ArrayList<>();

			try {
				br = new BufferedReader(new FileReader(csvFile));
				while ((line = br.readLine()) != null) {
					/*
					 * Each line should contain: room ID, date, start time, and end time, in the
					 * exact order.
					 */
					lineNum++;
					if (lineNum < 1) {
						continue;
					}
					String[] groups = line.split(cvsSplitBy);
					try {
						SearchRoomConstraint src = new SearchRoomConstraint();
						srcs.add(new SearchRoomConstraint());
						src.setEventDate(new SimpleDateFormat("MM/dd/yyyy").parse(groups[0].trim()));
						src.setStartTime(new SimpleDateFormat("hh:mm").parse(groups[1].trim()));
						src.setEndTime(new SimpleDateFormat("hh:mm").parse(groups[2].trim()));
						if(groups[3].trim().length() != 0) {
							src.setCapacity(Integer.parseInt(groups[3].trim()));
						}
						if(groups[4].trim().length() != 0) {
							src.setRoomName(groups[4].trim());
						}
						if(groups[5].trim().length() != 0) {
							String[] requestedfacilities = groups[5].trim().split(",");
							Set<Facility> set = new HashSet<>();
							for (String rf : requestedfacilities) {
								for (Facility f : facilities) {
									if (f.getName().equals(rf)) {
										set.add(f);
									}
								}			
							}
							src.setFacilities(set);
						}
					} catch (Exception exception) {
						failedLines.add(lineNum);
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
