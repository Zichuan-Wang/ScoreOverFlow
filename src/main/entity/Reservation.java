package entity;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "reservation")
public class Reservation {
	
	@Id
    @Column(name = "id", unique = true)
	private int id;
	
	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "room_id")
	private int roomId;
	
	@Column(name = "event_date")
	private Date eventDate;
	
	@Column(name = "start_time")
	private Time startTime;
	
	@Column(name = "end_time")
	private Time endTime;
	
	@Column(name = "created")
	private Timestamp created;
	
	// TODO: discuss the data type of modified
	@Column(name = "modified")
	private Timestamp modified;

	public int getId() {
		return id;
	}

	public Reservation setId(int id) {
		this.id = id;
		return this;
	}

	public int getUserId() {
		return userId;
	}

	public Reservation setUserId(int userId) {
		this.userId = userId;
		return this;
	}
	
	public int getRoomId() {
		return roomId;
	}

	public Reservation setRoomId(int roomId) {
		this.roomId = roomId;
		return this;
	}
	
	public Date getEventDate() {
		return eventDate;
	}

	public Reservation setEventDate(Date eventDate) {
		this.eventDate = eventDate;
		return this;
	}
	
	public Time getStartTime() {
		return startTime;
	}

	public Reservation setStartTime(Time startTime) {
		this.startTime = startTime;
		return this;
	}
	
	public Time getEndTime() {
		return endTime;
	}

	public Reservation setEndTime(Time endTime) {
		this.endTime = endTime;
		return this;
	}
	
	public Timestamp getCreated() {
		return created;
	}

	public Reservation setCreated(Timestamp created) {
		this.created = created;
		return this;
	}
	
	public Timestamp getModified() {
		return modified;
	}

	public Reservation setModified(Timestamp modified) {
		this.modified = modified;
		return this;
	}
}
