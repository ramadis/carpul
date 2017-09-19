package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.User;

public interface UserDao {
	/**
	 * Creates a new user
	 * @param username The user's username
	 * @param password The user's password
	 * @return The created user
	 */
	User create(User user);
	User findById (String userId);
}
