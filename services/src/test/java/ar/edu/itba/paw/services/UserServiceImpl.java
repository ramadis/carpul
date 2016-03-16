package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.User;

public class UserServiceImpl implements UserService {
	public User register(final String username, final String password) {
		return new User(username, password);
	}
}
