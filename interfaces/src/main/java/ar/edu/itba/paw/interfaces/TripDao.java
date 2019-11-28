package ar.edu.itba.paw.interfaces;

import java.util.List;

import ar.edu.itba.paw.models.Pagination;
import ar.edu.itba.paw.models.Search;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

public interface TripDao {
	Trip create(Trip trip, User driver);
	Trip findById (Integer tripId);
	List<Trip> getUserTrips(User user, Pagination pagination);
	List<Trip> getReservedTrips(User user);
	void reserveTrip(Integer tripId, User user);
	void delete(Integer tripId, User user);
	void unreserveTrip(Integer tripId, User user);
	List<Trip> findByRouteWithDateComparision(Search search, String string);
	List<Trip> findByRouteWithDateComparision(User user, Search search, String string);
}
