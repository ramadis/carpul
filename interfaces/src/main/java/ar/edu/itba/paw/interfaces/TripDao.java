package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Trip;

public interface TripDao {
	Trip create(Trip trip);
	Trip findById (String tripId);
}
