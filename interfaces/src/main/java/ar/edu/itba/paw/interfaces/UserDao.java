package ar.edu.itba.paw.interfaces;

import java.util.List;

import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

public interface UserDao {
	User create(User user);
	User getById(Integer id);
	User getByUsername(String username);
	List<User> getPassengers(Trip trip);
}
