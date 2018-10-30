package server.constraint;

import java.util.Date;

// Java object for specify search constraint for Room
public class SearchRoomConstraint {
	Date eventDate;
	Date startTime;
	Date endTime;
	String roomName;
	int capacity;
	
	public SearchRoomConstraint() {
		this.eventDate = new Date(0);
		this.startTime = new Date(0);
		this.endTime = new Date(0);
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
	
	public Date getStartTime() {
		return startTime;
	}
	
	public SearchRoomConstraint setStartTime(Date startTime) {
		this.startTime = startTime;
		return this;
	}
	
	public Date getEndTime() {
		return endTime;
	}
	
	public SearchRoomConstraint setEndTime(Date endTime) {
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
