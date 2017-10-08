package ar.edu.itba.paw.interfaces;

import java.util.List;

import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

public interface TripDao {
	Trip create(Trip trip, User driver);
	Trip findById (String tripId);
	List<Trip> findByPassenger(Integer passengerId);
	List<Trip> findAll(User user);
	void reserveTrip(Integer tripId, User user);
	void unreserveTrip(Integer tripId, User user);
}
