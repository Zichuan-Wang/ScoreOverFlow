package server.constraint;

import java.sql.Date;
import java.sql.Time;

public class SearchRoomConstraint {
	Date eventDate;
	Time startTime;
	Time endTime;
	String roomName;
	int capacity;
	
	public SearchRoomConstraint() {
		this.eventDate = new Date(0);
		this.startTime = new Time(0);
		this.endTime = new Time(0);
		roomName = "";
		this.capacity = 0;
	}
	
	public Date getEventDate() {
		return eventDate;
	}
	
	public SearchRoomConstraint setEventDate(Date eventDate) {
		this.eventDate = eventDate;
		return this;
	}
	
	public Time getStartTime() {
		return startTime;
	}
	
	public SearchRoomConstraint setStartTime(Time startTime) {
		this.startTime = startTime;
		return this;
	}
	
	public Time getEndTime() {
		return endTime;
	}
	
	public SearchRoomConstraint setEndTime(Time endTime) {
		this.endTime = endTime;
		return this;
	}
	
	public String getRoomName() {
		return this.roomName;
	}
	
	public SearchRoomConstraint setRoomName(String roomName) {
		this.roomName = roomName;
		return this;
	}
	
	public int getCapacity() {
		return this.capacity;
	}
	
	public SearchRoomConstraint setCapacity(int capacity) {
		this.capacity = capacity;
		return this;
	}
}
