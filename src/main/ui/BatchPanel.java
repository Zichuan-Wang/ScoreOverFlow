package ui;

import java.awt.Component;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileSystemView;

import batch.BatchReservationMaker;
import batch.CSVParseException;
import batch.CSVReader;
import batch.FileHandler;
import entity.User;
import server.action.FacilityAction;
import server.action.ReservationAction;
import server.action.RoomAction;
import server.constraint.SearchRoomConstraint;

public class BatchPanel extends BasePanel {
	private static final long serialVersionUID = 1L;
	private static final String TITLE = "Reserve Rooms in Batch";
	private static final String TEMPLATE = "This is the template of the batch reservation file. The third and fourth lines are examples. Fields with * are required. Please replace them with actual requests.\nEvent Date*,Start Time*,End Time*,Capacity,Room Name,Facilities\n12/20/2018,08:00,09:20,50,Mudd,projector; computer\n12/22/2018,15:00,22:10,250,,";
	private User user;

	private JButton uploadFileButton, downloadFileButton;
	private JLabel downloadFileLabel, uploadFileLabel;

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
		setBackButtonBottomPanel();

	}

	private void setMiddlePanel() {
		middlePane.setLayout(new BoxLayout(middlePane, BoxLayout.Y_AXIS));
		middlePane.add(Box.createVerticalGlue());
		
		downloadFileLabel = new JLabel("Step 1. Download this file and add your desired constraints. Do not modify the first 2 lines!");
		downloadFileLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		middlePane.add(downloadFileLabel);
		
		downloadFileButton = getDownloadFileButton();
		downloadFileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		middlePane.add(downloadFileButton);
		
		middlePane.add(Box.createRigidArea(new Dimension(0,100)));
		
		uploadFileLabel = new JLabel("Step 2. Upload your file below.");
		uploadFileLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		middlePane.add(uploadFileLabel);
		
		uploadFileButton = getUploadFileButton();
		uploadFileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		middlePane.add(uploadFileButton);
		
		middlePane.add(Box.createVerticalGlue());
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

			FileHandler handler = new FileHandler();
			handler.downloadFile(TEMPLATE, fileChooser.getSelectedFile().getAbsolutePath(), "/reservations", ".csv");
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
			try {
				br = new BufferedReader(new FileReader(csvFile));
				CSVReader reader = new CSVReader(facilityAction);
				List<SearchRoomConstraint> srcs = reader.readCSV(br);
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
			} catch (CSVParseException exception) {
				JOptionPane.showMessageDialog(null,
						"Line " + exception.getLine() + " cannot be parsed, please check your format.");
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
