package client;

import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JSplitPane;

import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Main extends JFrame {

	private JPanel contentPane;
	private JButton btnSubmit;

	/**
	 * Create the frame.
	 */
	public Main() {		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JSplitPane splitPane_in = new JSplitPane();
		splitPane_in.setBounds(6, 6, 496, 159);
		contentPane.add(splitPane_in);
		splitPane_in.setDividerLocation(150);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		splitPane_in.setLeftComponent(scrollPane_2);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane_in.setRightComponent(scrollPane_1);
		
		JSplitPane splitPane_out = new JSplitPane();
		splitPane_out.setBounds(6, 168, 496, 107);
		contentPane.add(splitPane_out);
		splitPane_out.setDividerLocation(150);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		splitPane_out.setLeftComponent(scrollPane_3);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		splitPane_out.setRightComponent(scrollPane_4);
		
		List list_out = new List();
		scrollPane_4.setViewportView(list_out);
		
		btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(281, 275, 128, 29);
		contentPane.add(btnSubmit);
		
		JLabel label1 = new JLabel("<Put some instribustions here>");
		label1.setBounds(6, 299, 458, 37);
		contentPane.add(label1);
		
		JLabel label2 = new JLabel("<Put some instribustions here>");
		label2.setBounds(6, 316, 458, 37);
		contentPane.add(label2);
	
		
		listenButton(btnSubmit);
    }
	
	private void listenButton(JButton button) {
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Button clicked!");	
			}
		});
	}
	
	public JButton getSubmitButton() {
		return this.btnSubmit;
	}
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}