package ar.edu.itba.paw.models;

import java.sql.Timestamp;

public class User {
	private String username;
	private String password;
	private String first_name;
	private String last_name;
	private String phone_number;
	private Timestamp created;
	private Integer id;
	
	//TODO: check if necessary. If not remove
	private String days_since_creation;

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public User() {
		this.username = this.password = "";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User = " + first_name;
	}

	public Timestamp getCreated() {
		return created;
	}
	
	public void setCreated(Timestamp created) {
		this.created = created;
		Timestamp now = new Timestamp(System.currentTimeMillis());
		Long  ms = now.getTime() - created.getTime();
		Integer days = (int) (ms / (1000*60*60*24));
		this.setDays_since_creation(days.toString());
	}

	public String getDays_since_creation() {
		return days_since_creation;
	}

	public void setDays_since_creation(String days_since_creation) {
		this.days_since_creation = days_since_creation;
	}
}
