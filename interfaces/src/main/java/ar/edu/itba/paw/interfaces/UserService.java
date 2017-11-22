package ar.edu.itba.paw.interfaces;

import java.util.List;


import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

public interface UserService {
	User register(User user);
	User getById(Integer id);
	User getByUsername(String username);
	User findById(Integer userId);
	Boolean exists(User user);
	List<User> getPassengers(Trip trip);
}

