package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.TripDao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.interfaces.TripService;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

@Service
public class TripServiceImpl implements TripService {

	@Autowired
	private TripDao tripDao;

	public Trip register(final Trip trip, final User driver) {
		return tripDao.create(trip, driver);
	}
	
	public void reserve(Integer tripId, User user) {
		tripDao.reserveTrip(tripId, user);
	}
	
	public void unreserve(Integer tripId, User user) {
		tripDao.unreserveTrip(tripId, user);
	}
	
	public List<Trip> findByPassenger(final Integer passengerId) {
		return tripDao.findByPassenger(passengerId);
	}
	
	public Trip findById(final String tripId) {
		return tripDao.findById(tripId);
	}
	
	public List<Trip> findAll(User user) {
		return tripDao.findAll(user);
	}
}
