package main.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
	
	@Id
    @Column(name = "id", unique = true)
	private int id;
	
	@Column(name = "uni", nullable = false, unique = true)
	private String uni;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "is_admin")
	private boolean isAdmin;
	
	@Column(name = "user_group")
	private int userGroup;
	
	@Column(name = "email")
	private String email;

	public int getId() {
		return id;
	}

	public User setId(int id) {
		this.id = id;
		return this;
	}

	public String getUni() {
		return uni;
	}

	public User setUni(String uni) {
		this.uni = uni;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public User setPassword(String password) {
		this.password = password;
		return this;
	}

	public boolean getIsAdmin() {
		return isAdmin;
	}

	public User setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
		return this;
	}

	public int getUserGroup() {
		return userGroup;
	}

	public User setUserGroup(int userGroup) {
		this.userGroup = userGroup;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public User setEmail(String email) {
		this.email = email;
		return this;
	}
}
