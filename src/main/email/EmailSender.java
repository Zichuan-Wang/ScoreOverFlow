package email;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

	public static void sendEmail(String to, String subject, String body) throws AddressException, MessagingException {
		InputStream inputStream;
		try {
			inputStream = new FileInputStream("src/main/resources/email.properties");
			Properties properties = new Properties();
			properties.load(inputStream);

			String username = properties.getProperty("mail.from.username");
			String password = properties.getProperty("mail.from.password");
			String from = properties.getProperty("mail.from.address");

			Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
			message.setText(body);
			Transport.send(message);

		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
}
