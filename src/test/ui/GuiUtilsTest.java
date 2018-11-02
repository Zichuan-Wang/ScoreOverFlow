package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.text.NumberFormatter;

import org.junit.jupiter.api.Test;

public class GuiUtilsTest {

	@Test
	public void createNumberFormatterTest() {
		NumberFormatter nf = GuiUtils.getNumberFormatter(0, 100);
		assertNotNull(nf);
		assertEquals(nf.getValueClass(), Integer.class);
	}

	@Test
	public void createButtonTest() {
		JButton button = GuiUtils.createButton("Test", e -> System.out.println("Test"));
		assertNotNull(button);
		assertEquals(button.getText(), "Test");
	}

	@Test
	public void getJumpCardActionListenerTest() {
		ActionListener actionListener = GuiUtils.getJumpCardActionListener(new JPanel(), "test");
		assertNotNull(actionListener);
	}

	@Test
	public void getBackButtonTest() {
		JButton button = GuiUtils.getBackButton(new BasePanel("Test", new JPanel()), new JPanel());
		assertNotNull(button);
		assertEquals(button.getText(), "Back");
	}
}
