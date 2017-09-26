package ar.edu.itba.paw.interfaces;

import java.util.List;

import ar.edu.itba.paw.models.Trip;

public interface TripDao {
	Trip create(Trip trip);
	Trip findById (String tripId);
	List<Trip> findByPassenger(Integer passengerId);
	List<Trip> findAll();
	void reserveTrip(Integer tripId);
}
