package ar.edu.itba.paw.interfaces;

import java.util.List;

import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

public interface UserDao {
	User create(User user);
	User getById(Integer id);
	User findById (Integer userId);
	User getByUsername(String username);
	List<Trip> getUserTrips(User user);
	List<Trip> getReservedTrips(User user);
}
