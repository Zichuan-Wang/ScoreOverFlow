package ui;

import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JPanel;
import javax.swing.text.NumberFormatter;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class GuiUtils {

	public static JDatePickerImpl getDatePicker() {
		class DateLabelFormatter extends AbstractFormatter {
			/**
			 * Default serial version id
			 */
			private static final long serialVersionUID = 1L;
			private String datePattern = "yyyy-MM-dd";
			private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

			@Override
			public Object stringToValue(String text) throws ParseException {
				return dateFormatter.parseObject(text);
			}

			@Override
			public String valueToString(Object value) throws ParseException {
				if (value != null) {
					Calendar cal = (Calendar) value;
					return dateFormatter.format(cal.getTime());
				}
				return "";
			}
		}

		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		UtilDateModel model = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		return datePicker;
	}

	public static NumberFormatter getNumberFormatter(int minimum, int maximum) {
		NumberFormatter numberFormatter = new NumberFormatter(NumberFormat.getInstance());
		numberFormatter.setValueClass(Integer.class);
		numberFormatter.setMinimum(minimum);
		numberFormatter.setMaximum(maximum);
		numberFormatter.setAllowsInvalid(false);
		return numberFormatter;
	}

	public static JButton createButton(String buttonName, ActionListener... actions) {
		JButton button = new JButton(buttonName);
		for (ActionListener action: actions) {
			button.addActionListener(action);
		}
		return button;
	}

	public static ActionListener getJumpCardActionListener(JPanel cards, String id) {
		return e -> {
			CardLayout cl = (CardLayout) cards.getLayout();
			cl.show(cards, id);
		};
	}
	
	public static JButton getBackButton(BasePanel pane, JPanel cards) {
		ActionListener initAction = e -> pane.reset();
		JButton backButton = createButton("Back", getJumpCardActionListener(cards,"main"),initAction);
		return backButton;
	}

}
