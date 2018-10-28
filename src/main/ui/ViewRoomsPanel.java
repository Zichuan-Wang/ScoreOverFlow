package ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import dao.ReservationDao;
import entity.Reservation;
import exception.DBConnectionException;
import server.constraint.SearchReservationConstraint;

public class ViewRoomsPanel extends JPanel {
	private ReservationDao myDao;
	private TablePanel reservationPane;

	public ViewRoomsPanel(JPanel contentPane) {
		try {
			myDao = new ReservationDao();
		} catch (DBConnectionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		reservationPane = new TablePanel();
        JScrollPane scrollReservationPane = new JScrollPane(reservationPane,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollReservationPane.setPreferredSize(new Dimension(600, 200));
        
        // fetch result directly
        printList();
        
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		c.gridheight = 2;
		c.weightx = 1.0;
		c.weighty = 1.0;
		add(scrollReservationPane,c);
		
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.weightx = 0.0;
		c.weighty = 1.0;
		JButton backButton = GUIUtil.getJumpCardButton(contentPane, "back","main");
		backButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		add(backButton,c);
	}
	
	private void reset() {
		reservationPane.reset();
	}
	
	public void printList() {
		SearchReservationConstraint src = new SearchReservationConstraint(); //@TODO need ID
		List<Reservation> reservationList = myDao.findReservationsByUserId(src);
		List<String> roomNameList = new ArrayList<>();
		for (Reservation reservation: reservationList) {
			roomNameList.add(Integer.toString(reservation.getRoomId())); //@TODO need name
		}
		reservationPane.displayList(roomNameList,"cancel");
	}
	
	
}
