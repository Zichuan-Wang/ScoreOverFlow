package email;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import entity.Reservation;
import entity.Room;
import entity.User;
import server.action.RoomAction;

public class EmailSender {

	public static void sendEmail(String to, String subject, String body) throws AddressException, MessagingException {
		to = to == null ? "" : to;
		subject = subject == null ? "" : subject;
		body = body == null ? "" : body;

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
	
	public static void sendOverrideEmail(User user, Reservation reservation, RoomAction roomAction) throws AddressException, MessagingException {
		Room room = roomAction.getRoomById(reservation.getRoomId());
		String date = new SimpleDateFormat("yyyy-MM-dd").format(reservation.getEventDate());
		String beginTime = new SimpleDateFormat("hh:mm:ss").format(reservation.getStartTime());
		String endTime = new SimpleDateFormat("hh:mm:ss").format(reservation.getEndTime());
		String to = user.getEmail();
		String subject = "Your reservation has been overriden by another user.";
		String body = "Dear " + user.getUni() + ":\n"
				+ "\t Your reservation of " + room.getName() +" for " + date 
				+ " from " + beginTime + " to " + endTime
				+ " has been unfortunately replaced by another user.\n"
				+ "Schedule++";
		sendEmail(to, subject, body);
	}
	
	public static void sendDeleteRoomEmail(User user, Reservation reservation, Room room) throws AddressException, MessagingException {
		String date = new SimpleDateFormat("yyyy-MM-dd").format(reservation.getEventDate());
		String beginTime = new SimpleDateFormat("hh:mm:ss").format(reservation.getStartTime());
		String endTime = new SimpleDateFormat("hh:mm:ss").format(reservation.getEndTime());
		String to = user.getEmail();
		String subject = "The room you reserved is no longer available";
		String body = "Dear " + user.getUni() + ":\n"
				+ "\t Your reservation of " + room.getName() +" for " + date 
				+ " from " + beginTime + " to " + endTime
				+ " has been cancelled because the room " + room.getName() + " is no longer available.\n"
				+ "Schedule++";
		sendEmail(to, subject, body);
	}

}
