package utils;

import java.sql.Time;
import java.util.Date;

import entity.Facility;
import entity.Reservation;
import entity.Room;
import entity.User;

public class EntityTestUtils {
	// Reservation
	public static final int DEFAULT_RESERVATION_ID = 1;
	public static final Date DEFAULT_EVENT_DATE = new Date(1234567);
	public static final Time DEFAULT_START_TIME = new Time(12345);
	public static final Time DEFAULT_END_TIME = new Time(54321);
	public static final Date DEFAULT_CREATED = new Date();
	public static final Date DEFAULT_MODIFIED = new Date();

	// Room
	public static final int DEFAULT_ROOM_ID = 1;
	public static final String DEFAULT_ROOM_NAME = "Mudd 282";
	public static final int DEFAULT_CAPACITY = 15;

	// Facility
	public static final int DEFAULT_FACILITY_ID = 1;
	public static final String DEFAULT_FACILITY_NAME = "projector";

	// User
	public static final int DEFAULT_USER_ID = 27;
	public static final String DEFAULT_EMAIL = "x@columbia.edu";
	public static final String DEFAULT_PASSWORD = "***";
	public static final String DEFAULT_UNI = "x";
	public static final boolean DEFAULT_ADMIN = true;
	public static final int DEFAULT_USER_GROUP = 0;

	public static Reservation getDefaultReservation() {
		return new Reservation().setId(DEFAULT_RESERVATION_ID)//
				.setUserId(DEFAULT_USER_ID)//
				.setEventDate(DEFAULT_EVENT_DATE)//
				.setStartTime(DEFAULT_START_TIME)//
				.setEndTime(DEFAULT_END_TIME)//
				.setCreated(DEFAULT_CREATED)//
				.setModified(DEFAULT_MODIFIED);
	}

	public static Room getDefaultRoom() {
		return new Room().setId(DEFAULT_ROOM_ID)//
				.setName(DEFAULT_ROOM_NAME)//
				.setCapacity(DEFAULT_CAPACITY);
	}

	public static Facility getDefaultFacility() {
		return new Facility().setId(DEFAULT_FACILITY_ID)//
				.setName(DEFAULT_FACILITY_NAME);
	}

	public static User getDefaultUser() {
		return new User().setId(DEFAULT_USER_ID)//
				.setEmail(DEFAULT_EMAIL)//
				.setPassword(DEFAULT_PASSWORD)//
				.setUni(DEFAULT_UNI)//
				.setIsAdmin(DEFAULT_ADMIN)//
				.setUserGroup(DEFAULT_USER_GROUP);
	}
}
