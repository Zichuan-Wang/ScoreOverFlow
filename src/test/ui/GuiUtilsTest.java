package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.CardLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;

import org.junit.jupiter.api.Test;

import dao.UserDao;
import entity.User;
import utils.EntityTestUtils;
import utils.UserDaoTestUtils;

public class GuiUtilsTest {
	private final String DEFAULT_USER_GROUP_STRING = "Normal User";
	private final String ADMIN_USER_GROUP_STRING = "Administrator";
	private final String PS_USER_GROUP_STRING = "Program supervisor";
	private final String HIGH_USER_GROUP_STRING = "High priority user";

	@Test
	public void createButtonTest() {
		JButton button = GuiUtils.createButton("Test", e -> System.out.println("Test"));
		assertNotNull(button);
		assertEquals(button.getText(), "Test");
	}

	@Test
	public void jumpToPanelTest() {
		JPanel cards = new JPanel(new CardLayout());
		JPanel pane1 = new JPanel();
		JPanel pane2 = new JPanel();
		cards.add(pane1, "1");
		cards.add(pane2, "2");
		GuiUtils.jumpToPanel(cards, "2");
		assertFalse(pane1.isVisible());
		assertTrue(pane2.isVisible());
	}

	@Test
	public void userGroupToStringTest() {
		UserDao dao = UserDaoTestUtils.getUserDao();
		User user = dao.findById(EntityTestUtils.DEFAULT_USER_ID);
		assertEquals(DEFAULT_USER_GROUP_STRING, GuiUtils.userGroupToString(user));
		user.setUserGroup(EntityTestUtils.ADMIN_USER_GROUP);
		assertEquals(ADMIN_USER_GROUP_STRING, GuiUtils.userGroupToString(user));
		user.setUserGroup(EntityTestUtils.HIGH_USER_GROUP);
		assertEquals(HIGH_USER_GROUP_STRING, GuiUtils.userGroupToString(user));
		user.setUserGroup(EntityTestUtils.SUPERVISOR_USER_GROUP);
		assertEquals(PS_USER_GROUP_STRING, GuiUtils.userGroupToString(user));
		// Not existing
		user.setUserGroup(4);
		assertEquals("",GuiUtils.userGroupToString(user));
	}

	@Test
	public void getNumTextFieldTest() throws BadLocationException {
		JTextField field = GuiUtils.getNumTextField(1);
		field.setText("22"); // over the limit
		assertEquals("", field.getText());// empty
		field.setText("1abc"); // invalid character
		assertEquals("", field.getText());// empty
		field.setText("2");
		assertEquals("2", field.getText());
		field.setText(null);
		assertEquals("2", field.getText());
		/*
		// Testing overriden functions
		DocumentFilter filter = ((AbstractDocument) field.getDocument()).getDocumentFilter();
		filter.insertString(null, 0, null, null); // nothing returns
		assertEquals("2", field.getText());
		filter.replace(null, 0, 0, null, null); // nothing returns
		assertEquals("2", field.getText());
		*/
		
	}

}
