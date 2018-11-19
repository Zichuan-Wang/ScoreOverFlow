package server.constraint;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import entity.Facility;

// Java object for specify search constraint for Room
public class SearchRoomConstraint {
	Date eventDate;
	Date startTime;
	Date endTime;
	String roomName;
	int capacity;
	Set<Facility> facilities;

	public SearchRoomConstraint() {
		eventDate = new Date(0);
		startTime = new Date(0);
		endTime = new Date(0);
		this.roomName = "";
		capacity = 0;
		facilities = new HashSet<>();
	}

	public Set<Facility> getFacilities() {
		return facilities;
	}

	public void setFacilities(Set<Facility> facilities) {
		this.facilities = facilities;
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
