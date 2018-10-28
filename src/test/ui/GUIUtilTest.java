package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.swing.text.NumberFormatter;

import org.jdatepicker.impl.JDatePickerImpl;
import org.junit.jupiter.api.Test;

import server.constraint.SearchReservationConstraint;

public class GUIUtilTest {
	@Test
	public void createDatePicker() {
		JDatePickerImpl datepicker = GUIUtil.getDatePicker();
		assertNotNull(datepicker);
	}
	
	@Test
	public void createNumberFormatter() {
		NumberFormatter nf = GUIUtil.getNumberFormatter();
		assertNotNull(nf);
		assertEquals(nf.getValueClass(),Integer.class);
	}
	
	//@TODO test getJumpCardButton
}
