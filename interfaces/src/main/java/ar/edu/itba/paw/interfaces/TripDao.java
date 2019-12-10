package ar.edu.itba.paw.interfaces;

import java.util.List;

import ar.edu.itba.paw.models.Pagination;
import ar.edu.itba.paw.models.Reservation;
import ar.edu.itba.paw.models.Search;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

public interface TripDao {
	Trip create(Trip trip, User driver);
	Trip findById (Integer tripId);
	List<Trip> getUserTrips(User user, Pagination pagination);
	List<Trip> getSuggestions(String origin, Pagination pagination, User driver);
	Reservation reserveTrip(Integer tripId, User user);
	void delete(Integer tripId, User user);
	void unreserveTrip(Integer tripId, User user);
	List<Reservation> getReservationsByUser(User user, Pagination pagination);
	Boolean areReservationConflicts(Trip trip, User user);
	Boolean areDrivingConflicts(Trip trip, User user);
	List<Trip> findByRoute(Search search, Pagination pagination, User driver);
}
