package ar.edu.itba.paw.webapp.forms;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class UserLoginForm {
	@NotBlank
	@Pattern(regexp = "[a-zA-Z0-9]+")
	private String username;
	
	@NotBlank
	private String password;

	public String getUsername() {
		return username;
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
}
