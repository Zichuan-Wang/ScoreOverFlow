package entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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

	@Temporal(TemporalType.DATE)
	@Column(name = "event_date")
	private Date eventDate;

	@Temporal(TemporalType.TIME)
	@Column(name = "start_time")
	private Date startTime;

	@Temporal(TemporalType.TIME)
	@Column(name = "end_time")
	private Date endTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created")
	private Date created;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified")
	private Date modified;
	
	@Column(name = "overriden")
	private int overriden;

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

	public Date getStartTime() {
		return startTime;
	}

	public Reservation setStartTime(Date startTime) {
		this.startTime = startTime;
		return this;
	}

	public Date getEndTime() {
		return endTime;
	}

	public Reservation setEndTime(Date endTime) {
		this.endTime = endTime;
		return this;
	}

	public Date getCreated() {
		return created;
	}
	
	public int isOverriden() {
		return overriden;
	}
	
	public Reservation setOverriden(int overriden) {
		this.overriden = overriden;
		return this;
	}

	public Reservation setCreated(Date created) {
		this.created = created;
		return this;
	}

	public Date getModified() {
		return modified;
	}

	public Reservation setModified(Date modified) {
		this.modified = modified;
		return this;
	}
}
