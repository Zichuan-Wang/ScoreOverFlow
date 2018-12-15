package ui;

import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import dao.FacilityDao;
import dao.ReservationDao;
import dao.RoomDao;
import dao.UserDao;
import dao.factory.FacilityDaoFactory;
import dao.factory.ReservationDaoFactory;
import dao.factory.RoomDaoFactory;
import dao.factory.UserDaoFactory;
import security.SecurityService;
import server.action.FacilityAction;
import server.action.ReservationAction;
import server.action.RoomAction;
import server.action.UserAction;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;

	public MainWindow(UserAction userAction, ReservationAction reservationAction, RoomAction roomAction,
			FacilityAction facilityAction) {
		// Initialization
		setDefaultLookAndFeelDecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Schedule++");
		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenSize.width / 2, screenSize.height);

		// Create a cardLayout for switching between the panels
		JPanel rootPanel = new JPanel(new CardLayout());
		LoginPanel loginPane = new LoginPanel(rootPanel, userAction, reservationAction, roomAction, facilityAction);
		rootPanel.add(loginPane, "login");
		add(rootPanel);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// nicer look and feel with Nimbus
					for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
						if ("Nimbus".equals(info.getName())) {
							UIManager.setLookAndFeel(info.getClassName());
							break;
						}
					}

					// Set up necessary parameters
					RoomDao roomDao = RoomDaoFactory.getInstance();
					UserDao userDao = UserDaoFactory.getInstance();
					FacilityDao facilityDao = FacilityDaoFactory.getInstance();
					ReservationDao reservationDao = ReservationDaoFactory.getInstance();
					
					UserAction userAction = new UserAction(userDao);
					ReservationAction reservationAction = new ReservationAction(reservationDao, roomDao);
					RoomAction roomAction = new RoomAction(roomDao, reservationDao, userDao);
					FacilityAction facilityAction = new FacilityAction(facilityDao);
					SecurityService.initialize(null);
					
					MainWindow frame = new MainWindow(userAction, reservationAction, roomAction, facilityAction);
					
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
}
