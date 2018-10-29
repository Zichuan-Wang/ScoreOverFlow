package entity;

import java.util.Date;

public class EntityUtils {
	public static Reservation roomToReservation(Room room, Date eventDate, Date startTime, Date endTime, int userId) {
		Reservation reservation = new Reservation()
				.setUserId(userId)
				.setStartTime(startTime)
				.setEndTime(endTime)
				.setEventDate(eventDate)
				.setRoomId(room.getId())
				.setModified(new Date())
				.setCreated(new Date());
		return reservation;
	}
}
