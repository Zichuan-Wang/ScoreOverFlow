package main.entity;

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

	public Room setId(int id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Room setName(String name) {
		this.name = name;
		return this;
	}

	public int getCapacity() {
		return capacity;
	}

	public Room setCapacity(int capacity) {
		this.capacity = capacity;
		return this;
	}
}
