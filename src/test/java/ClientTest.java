package java;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import client.Main;

import javax.swing.JButton;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

// The JUnit tests for GUI
public class ClientTest {
	private static Main frame;
	
	@BeforeAll
	public static void pullUpMainWindow () {
		System.out.println("0");
		frame = new Main();
		frame.setVisible(true);
	}
	
	@AfterAll
	public static void closeMainWindow () {
		System.out.println("2");
		frame.setVisible(false);
		frame.dispose();
	}
	
	@Test
	public void LoadWindow() {
		System.out.println("1");
		assertNotNull(frame);
	}

	@Test
	public void GetSubmitButton() {
		JButton btnSubmit = frame.getSubmitButton();
		assertNotNull(btnSubmit);
		assertEquals(btnSubmit.getText(), "Submit");
	}

}
