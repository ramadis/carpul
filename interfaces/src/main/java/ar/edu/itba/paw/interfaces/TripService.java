package ar.edu.itba.paw.interfaces;

import java.util.List;

import ar.edu.itba.paw.models.Search;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

public interface TripService {
	Trip register(Trip trip, User driver);
	Trip findById(Integer tripId);
	List<Trip> findByRoute(User user, Search search);
	List<Trip> findByRoute(Search search);
	List<Trip> getUserTrips(User user);
	List<Trip> getReservedTrips(User user);
	List<Trip> findAfterDateByRoute(User user, Search search);
	List<Trip> findAfterDateByRoute(Search search);
	List<Trip> getSuggestions(User user, Search search);
	void reserve(Integer tripId, User user);
	void delete(Integer tripId, User user);
	void unreserve(Integer tripId, User user);
}

