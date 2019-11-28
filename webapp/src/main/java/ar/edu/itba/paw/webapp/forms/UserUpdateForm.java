package ar.edu.itba.paw.webapp.forms;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import ar.edu.itba.paw.models.User;

public class UserUpdateForm {
	@Pattern(regexp = "[\\p{L} ]+")
	private String first_name;

	@Pattern(regexp = "[\\p{L} ]+")
	private String last_name;
	
	@Size(min = 6, max = 20)
	@Pattern(regexp = "[0-9]+")
	private String phone_number;
	
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

	public User getUser() {
		User user = new User();
		user.setFirst_name(first_name);
		user.setLast_name(last_name);
		user.setPhone_number(phone_number);
		return user;
	}
}
