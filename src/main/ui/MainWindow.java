package ui;

import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import dao.factory.ReservationDaoFactory;
import dao.factory.RoomDaoFactory;
import dao.factory.UserDaoFactory;
import entity.User;
import server.action.ReservationAction;
import server.action.RoomAction;
import server.action.UserAction;

public class MainWindow extends JFrame {
	/**
	 * Default serial version id
	 */
	private static final long serialVersionUID = 1L;
	final int SCREEN_WIDTH = 600;
	final int SCREEN_HEIGHT = 800;

	public MainWindow(User user, ReservationAction reservationAction, RoomAction roomAction) {
		// Initialization
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Schedule++");
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

		// Create a cardLayout for switching between the panels
		JPanel cards = new JPanel(new CardLayout());

		// Create three panels
		ReservePanel reservePane = new ReservePanel(cards, user, reservationAction, roomAction);
		ViewRoomsPanel viewRoomsPane = new ViewRoomsPanel(cards, user, reservationAction, roomAction);
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
					UserAction userAction = new UserAction(UserDaoFactory.getInstance());
					ReservationAction reservationAction = new ReservationAction(ReservationDaoFactory.getInstance());
					RoomAction roomAction = new RoomAction(RoomDaoFactory.getInstance());
					// TODO: replace by actual user
					User user = userAction.findUserByUni("hx2209");
					MainWindow frame = new MainWindow(user, reservationAction, roomAction);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
