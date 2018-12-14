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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileSystemView;

import batch.BatchReservationMaker;
import entity.Facility;
import entity.User;
import server.action.FacilityAction;
import server.action.ReservationAction;
import server.action.RoomAction;
import server.constraint.SearchRoomConstraint;

public class BatchPanel extends BasePanel {
	private static final long serialVersionUID = 1L;
	private static final String TITLE = "Reserve Rooms in Batch";
	private static final String TEMPLATE = "Event Date,Start Time,End Time,Capacity,Room Name,Facilities\n12/20/2018,08:00,09:20,50,Mudd,projector; computer\n12/22/2018,15:00,22:10,250,,";
	private User user;

	private JButton uploadFileButton, downloadFileButton, backButton;

	// private UserAction userAction;
	private ReservationAction reservationAction;
	private RoomAction roomAction;
	private FacilityAction facilityAction;

	public BatchPanel(JPanel cards, User user, FacilityAction facilityAction, RoomAction roomAction,
			ReservationAction reservationAction) {
		super(cards, TITLE);
		this.user = user;
		this.facilityAction = facilityAction;
		this.roomAction = roomAction;
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
				return;
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
						file = new File(fileChooser.getSelectedFile().getAbsolutePath() + filename + "(" + probe + ")"
								+ extension);
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
			String csvFile = selectedFile.getAbsolutePath();
			BufferedReader br = null;
			String line = "";
			String cvsSplitBy = ",";
			int lineNum = 0;

			List<SearchRoomConstraint> srcs = new ArrayList<>();
			List<Facility> facilities = facilityAction.findAllFacilities();
			Map<String, Facility> facilityNames = new HashMap<>();
			for (Facility facility : facilities) {
				facilityNames.put(facility.getName(), facility);
			}

			try {
				br = new BufferedReader(new FileReader(csvFile));
				while ((line = br.readLine()) != null) {
					lineNum++;
					if (lineNum < 2) {
						continue;
					}
					String[] groups = line.split(cvsSplitBy, -1);
					try {
						SearchRoomConstraint src = new SearchRoomConstraint();
						src.setEventDate(new SimpleDateFormat("MM/dd/yyyy").parse(groups[0].trim()));
						src.setStartTime(new SimpleDateFormat("hh:mm").parse(groups[1].trim()));
						src.setEndTime(new SimpleDateFormat("hh:mm").parse(groups[2].trim()));
						if (groups[3].trim().length() != 0) {
							src.setCapacity(Integer.parseInt(groups[3].trim()));
						}
						if (groups[4].trim().length() != 0) {
							src.setRoomName(groups[4].trim());
						}
						if (groups[5].trim().length() != 0) {
							String[] requestedfacilityNames = groups[5].trim().split(";");
							Set<Facility> requestedfacilities = new HashSet<>();
							for (String name : requestedfacilityNames) {
								if (facilityNames.containsKey(name.trim())) {
									requestedfacilities.add(facilityNames.get(name.trim()));
								}
							}
							src.setFacilities(requestedfacilities);
						}
						srcs.add(src);
					} catch (Exception exception) {
						JOptionPane.showMessageDialog(null,
								"Line " + lineNum + " cannot be parsed, please check your format.");
						return;
					}
				}

				BatchReservationMaker reservationMaker = new BatchReservationMaker(roomAction, reservationAction, user);
				List<Integer> unsuccessful = reservationMaker.makeReservationsGreedy(srcs);
				String unsuccessfulTest = "";
				if (unsuccessful.size() > 0) {
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < unsuccessful.size(); i++) {
						sb.append(unsuccessful.get(i) + 1);
						if (i != unsuccessful.size() - 1) {
							sb.append(", ");
						}
					}
					unsuccessfulTest = " Cannot satisfy the requirement for the following items: " + sb.toString()
							+ ".";
				}

				JOptionPane.showMessageDialog(null,
						"Made " + (srcs.size() - unsuccessful.size()) + " reservations." + unsuccessfulTest);

			} catch (FileNotFoundException exception) {
				JOptionPane.showMessageDialog(null, "Cannot read selected file.");
				exception.printStackTrace();
			} catch (IOException exception) {
				JOptionPane.showMessageDialog(null, "An error occured while reading file.");
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
