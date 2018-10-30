package ui;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.text.NumberFormatter;

import org.jdatepicker.impl.JDatePickerImpl;
import org.junit.jupiter.api.Test;

import ui.GuiUtils;

public class GuiUtilsTest {
	@Test
	public void createDatePicker() {
		JDatePickerImpl datepicker = GuiUtils.getDatePicker();
		assertNotNull(datepicker);
	}
	
	@Test
	public void createNumberFormatter() {
		NumberFormatter nf = GuiUtils.getNumberFormatter();
		assertNotNull(nf);
		assertEquals(nf.getValueClass(),Integer.class);
	}
	
	@Test
	public void createButtonTest() {
		JButton button = GuiUtils.createButton("Test",e->System.out.println("Test"));
		assertNotNull(button);
		assertEquals(button.getText(),"Test");
	}
		
	@Test
	public void getJumpCardActionListenerTest() {
		ActionListener actionListener = GuiUtils.getJumpCardActionListener(new JPanel(),"test");
		assertNotNull(actionListener);
	}
	
	@Test
	public void getBackButtonTest() {
		JButton button = GuiUtils.getBackButton(new BasePanel("Test", new JPanel()),new JPanel());
		assertNotNull(button);
		assertEquals(button.getText(),"Back");
	}
}
