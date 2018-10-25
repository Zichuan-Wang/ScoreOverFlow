package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "room")
public class Room {
	
	@Id
    @Column(name = "id", unique = true)
	private int id;
	
	@Column(name = "name", unique = true)
	private String name;
	
	@Column(name = "capacity")
	private int capacity;
	
	// TODO: discuss representation for room technology

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
}
