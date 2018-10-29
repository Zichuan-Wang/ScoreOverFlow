package entity;

import java.util.Date;

public class EntityUtil {
	public static Reservation roomToReservation(Room room, Date eventDate, Date startTime, Date endTime, int userId) {
		Reservation reservation = new Reservation();
		reservation.setUserId(userId);
		reservation.setStartTime(startTime);
		reservation.setEndTime(endTime);
		reservation.setEventDate(eventDate);
		reservation.setRoomId(room.getId());
		reservation.setModified(new Date());
		reservation.setCreated(new Date());
		return reservation;
	}
}
