package ar.edu.itba.paw.interfaces;

import java.util.List;

import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

public interface UserDao {
	/**
	 * Creates a new user
	 * @param username The user's username
	 * @param password The user's password
	 * @return The created user
	 */
	User create(String username, String password);
	User findById (Integer userId);
	User getByUsername(String username);
	List<Trip> getUserTrips(User user);
	List<Trip> getReservedTrips(User user);
}
