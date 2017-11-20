package ar.edu.itba.paw.interfaces;

import java.util.List;

import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

public interface UserDao {
	User create(User user);
	User getById(Integer id);
	User findById(Integer id);
	User getByUsername(String username);
	Boolean exists(User user);
	List<User> getPassengers(Trip trip);
}
