package ui;

import java.awt.CardLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainWindow extends JFrame{
	private JPanel contentPane;

	public MainWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Main Frame");
		setSize(1080,720);
		
		contentPane = new JPanel(new CardLayout());
		setContentPane(contentPane);
		
		MainPanel mainPane = new MainPanel(contentPane);
		ReservePanel reservePane = new ReservePanel(contentPane);
		ViewRoomsPanel viewRoomsPane = new ViewRoomsPanel(contentPane);
		
		Map<String, JPanel> map = new HashMap<String,JPanel>()
		{{
		     put("main",mainPane);
		     put("reserve", reservePane);
		     put("view rooms", viewRoomsPane);
		}};
		
		((MainPanel) mainPane).createJump(map);
		
		contentPane.add(mainPane,"main");
		contentPane.add(reservePane,"reserve");
		contentPane.add(viewRoomsPane,"view rooms");
		//Display the window.
        //pack();
		setVisible(true);
	}
	
	public static void main(String[] args) {
	      new MainWindow();
	   }
}
