package ui;

import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import dao.ReservationDao;
import dao.RoomDao;
import dao.factory.ReservationDaoFactory;
import dao.factory.RoomDaoFactory;
import server.action.ReservationAction;
import server.action.RoomAction;

public class MainWindow extends JFrame {
	/**
	 * Default serial version id
	 */
	private static final long serialVersionUID = 1L;
	private JPanel cards;
	final int SCREEN_WIDTH = 600;
	final int SCREEN_HEIGHT = 800;

	public MainWindow(ReservationAction reservationAction, RoomAction roomAction) {
		// Initialization
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Schedule++");
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

		// Create a cardLayout for switching between the panels
		cards = new JPanel(new CardLayout());
		
		// Create three panels
		ReservePanel reservePane = new ReservePanel(cards, reservationAction, roomAction);
		ViewRoomsPanel viewRoomsPane = new ViewRoomsPanel(cards, reservationAction);
		MainPanel mainPane = new MainPanel(cards, reservePane, viewRoomsPane);

		// add panels to card, add cards to JFrame
		cards.add(mainPane, "main");
		cards.add(reservePane, "reserve");
		cards.add(viewRoomsPane, "view rooms");
		add(cards);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReservationDao reservationDao = ReservationDaoFactory.getInstance();
					RoomDao roomDao = RoomDaoFactory.getInstance();
					ReservationAction reservationAction = new ReservationAction(reservationDao);
					RoomAction roomAction = new RoomAction(roomDao);
					MainWindow frame = new MainWindow(reservationAction, roomAction);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
