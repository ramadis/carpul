package ar.edu.itba.paw.webapp.forms;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import ar.edu.itba.paw.models.User;

public class UserCreateForm {
	@NotBlank
	@Email
	private String username;
	
	@NotBlank
	@Pattern(regexp = "[\\p{L} ]+")
	private String first_name;
	
	@NotBlank
	@Pattern(regexp = "[\\p{L} ]+")
	private String last_name;
	
	@Size(min = 6, max = 20)
	@Pattern(regexp = "[0-9]+")
	private String phone_number;
	
	@Size(min = 6, max = 100)
	@NotBlank
	private String password;
	
	public String getUsername() {
		return username;
	}

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

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public User getUser() {
		User user = new User();
		user.setFirst_name(first_name);
		user.setLast_name(last_name);
		user.setPhone_number(phone_number);
		user.setUsername(username);
		user.setPassword(password);
		return user;
	}
}
