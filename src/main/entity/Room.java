package entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	
	@ManyToMany(cascade = {
		CascadeType.PERSIST, 
		CascadeType.MERGE
	})
	@JoinTable(name = "room_facility",
	    joinColumns = @JoinColumn(name = "room_id"),
	    inverseJoinColumns = @JoinColumn(name = "facility_id")
	)
	private Set<Facility> facilities = new HashSet<>();

	public int getId() {
		return id;
	}

	public Set<Facility> getFacilities() {
		return facilities;
	}

	public Room setFacilities(Set<Facility> facilities) {
		this.facilities = facilities;
		return this;
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
