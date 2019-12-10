package ar.edu.itba.paw.interfaces;

import java.util.List;

import ar.edu.itba.paw.models.Pagination;
import ar.edu.itba.paw.models.Reservation;
import ar.edu.itba.paw.models.Search;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

public interface TripService {
	Trip register(Trip trip, User driver);
	Trip findById(Integer tripId);
	List<Trip> findByRoute(Search search, Pagination pagination, User driver);
	List<Trip> getUserTrips(User user, Pagination pagination);
	List<Trip> getReservedTrips(User user, Pagination pagination);
	List<Trip> getSuggestions(String origin, Pagination pagination, User driver);
	Boolean areTimeConflicts(Trip trip, User user);
	Reservation reserve(Integer tripId, User user);
	void delete(Integer tripId, User user);
	void unreserve(Integer tripId, User user);
}

