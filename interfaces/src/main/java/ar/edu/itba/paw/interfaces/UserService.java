package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.User;

public interface UserService {
	/**
	 * Registers a new user
	 * @param username The user's username
	 * @param password The user's password
	 * @return The registered user
	 */
	User register(String username, String password);
	
	User findById(String userId);
}

