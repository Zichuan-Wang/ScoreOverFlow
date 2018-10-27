package server;

import java.sql.Date;
import java.sql.Time;

public class SearchConstraint {
	Date eventDate;
	Time startTime;
	Time endTime;
	String roomName;
	int capacity;
	
	public SearchConstraint() {
		this.eventDate = new Date(0);
		this.startTime = new Time(0);
		this.endTime = new Time(0);
		roomName = "";
		this.capacity = 0;
	}
	
	public Date getEventDate() {
		return eventDate;
	}
	
	public SearchConstraint setEventDate(Date eventDate) {
		this.eventDate = eventDate;
		return this;
	}
	
	public Time getStartTime() {
		return startTime;
	}
	
	public SearchConstraint setStartTime(Time startTime) {
		this.startTime = startTime;
		return this;
	}
	
	public Time getEndTime() {
		return endTime;
	}
	
	public SearchConstraint setEndTime(Time endTime) {
		this.endTime = endTime;
		return this;
	}
	
	public String getRoomName() {
		return this.roomName;
	}
	
	public SearchConstraint setRoomName(String roomName) {
		this.roomName = roomName;
		return this;
	}
	
	public int getCapacity() {
		return this.capacity;
	}
	
	public SearchConstraint setCapacity(int capacity) {
		this.capacity = capacity;
		return this;
	}
}
