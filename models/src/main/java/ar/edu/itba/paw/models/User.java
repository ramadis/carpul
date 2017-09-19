package ar.edu.itba.paw.models;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class User {
	private Integer id;
	private String username;
	private String password;
	private String first_name;
	private String last_name;
	private String session_token;
	private Date birthdate;
	private Boolean deleted;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public Map getParams() {
		Map<String, Object> map = new HashMap<>();
		map.put("username", username);
		map.put("password", password);
		map.put("first_name", first_name);
		map.put("last_name", last_name);
		return map;
	}

	public String getFirstName() {
		return first_name;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId( Integer id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.first_name = firstName;
	}

	public String getLastName() {
		return last_name;
	}

	public void setLastName(String lastName) {
		this.last_name = lastName;
	}

	public String getSessionToken() {
		return session_token;
	}

	public void setSessionToken(String sessionToken) {
		this.session_token = sessionToken;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public User() {
		this.username = this.password = "";
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
		return "User = " + username;
	}
}
