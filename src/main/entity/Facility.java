package entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "facility")
public class Facility {
	
	@Id
    @Column(name = "id", unique = true)
	private int id;
	
	@Column(name = "name", unique = true)
	private String name;
	
	@ManyToMany(mappedBy = "facilities")
	private Set<Room> rooms = new HashSet<>();
	
	public int getId() {
		return id;
	}

	public Facility setId(int id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Facility setName(String name) {
		this.name = name;
		return this;
	}

	public Set<Room> getRooms() {
		return rooms;
	}

	public Facility setRooms(Set<Room> rooms) {
		this.rooms = rooms;
		return this;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
