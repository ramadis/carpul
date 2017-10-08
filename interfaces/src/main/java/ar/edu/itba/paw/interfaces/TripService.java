package ar.edu.itba.paw.interfaces;

import java.util.List;

import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

public interface TripService {
	Trip register(Trip trip, User driver);
	Trip findById(String tripId);
	List<Trip> findByPassenger(Integer passengerId);
	List<Trip> findAll(User user);
	void reserve(Integer tripId, User user);
	void unreserve(Integer tripId, User user);
}

