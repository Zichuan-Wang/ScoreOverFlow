package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import dao.ReservationDao;
import entity.Reservation;
import exception.DBConnectionException;
import server.constraint.SearchReservationConstraint;

public class ViewRoomsPanel extends JPanel {
	private ReservationDao myDao;
	private TablePanel reservationPane;

	public ViewRoomsPanel() {
		
	}
	public ViewRoomsPanel(JPanel contentPane) {
		try {
			myDao = new ReservationDao();
		} catch (DBConnectionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		reservationPane = new TablePanel();
		JScrollPane scroll = new JScrollPane(reservationPane);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.
		                                   VERTICAL_SCROLLBAR_ALWAYS);
		add(scroll);
		
		JButton backButton = GUIUtil.getJumpCardButton(contentPane, "back","main");
		add(backButton);
	}
	
	public void printList() {
		SearchReservationConstraint src = new SearchReservationConstraint();
		List<Reservation> reservationList = myDao.findReservationsByUserId(src);
		List<String> roomNameList = new ArrayList<>();
		for (Reservation reservation: reservationList) {
			roomNameList.add(Integer.toString(reservation.getRoomId())); //@TODO need name
		}
		reservationPane.displayList(roomNameList,"cancel");
	}
	
	
}
