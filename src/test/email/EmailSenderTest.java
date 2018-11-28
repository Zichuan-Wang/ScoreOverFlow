package email;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.mail.SendFailedException;

import org.junit.jupiter.api.Test;

public class EmailSenderTest {

	private static final String VALID_ADDRESS = "Scheduleplusplus@gmail.com";
	private static final String INVALID_ADDRESS = "invalid";
	private static final String SUBJECT = "subject";
	private static final String BODY = "body";
	
	@Test
	public void testAddress() {
		assertAll(() -> EmailSender.sendEmail(VALID_ADDRESS, SUBJECT, BODY));
		assertThrows(SendFailedException.class, () -> EmailSender.sendEmail(INVALID_ADDRESS, SUBJECT, BODY));
		assertThrows(SendFailedException.class, () -> EmailSender.sendEmail(null, SUBJECT, BODY));
	}
	
	@Test
	public void testSubject() {
		assertAll(() -> EmailSender.sendEmail(VALID_ADDRESS, SUBJECT, BODY));
		assertAll(() -> EmailSender.sendEmail(VALID_ADDRESS, "", BODY));
		assertAll(() -> EmailSender.sendEmail(VALID_ADDRESS, null, BODY));
	}
	
	@Test
	public void testBody() {
		assertAll(() -> EmailSender.sendEmail(VALID_ADDRESS, SUBJECT, BODY));
		assertAll(() -> EmailSender.sendEmail(VALID_ADDRESS, SUBJECT, ""));
		assertAll(() -> EmailSender.sendEmail(VALID_ADDRESS, SUBJECT, null));
	}
	
}
