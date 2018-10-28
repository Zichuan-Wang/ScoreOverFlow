package ui;

import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainWindow extends JFrame{
	private JPanel cards;
	final int SCREEN_WIDTH = 600;
	final int SCREEN_HEIGHT = 800;

	public MainWindow() {
		// Initialization
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Scheduler++");
		setSize(SCREEN_WIDTH,SCREEN_HEIGHT);

		// Create a cardlayout for switching between the panels
		cards = new JPanel(new CardLayout());

		// Create three panels
		JPanel mainPane = new BasePanel("Main",new MainPanel(cards));
		JPanel reservePane = new BasePanel("Reserve a Room",new ReservePanel(cards));
		JPanel viewRoomsPane = new BasePanel("Booked Rooms",new ViewRoomsPanel(cards));

		//add panels to card, add cards to JFrame
		cards.add(mainPane,"main");
		cards.add(reservePane,"reserve");
		cards.add(viewRoomsPane,"view rooms");
		add(cards);

		//Display the window.
		//pack();
		//setVisible(true);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
