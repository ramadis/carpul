package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.TripDao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.interfaces.TripService;
import ar.edu.itba.paw.models.Trip;

@Service
public class TripServiceImpl implements TripService {

	@Autowired
	private TripDao tripDao;

	public Trip register(final Trip trip) {
		return tripDao.create(trip);
	}
	
	public List<Trip> findByPassenger(final Integer passengerId) {
		return tripDao.findByPassenger(passengerId);
	}
	
	public Trip findById(final String tripId) {
		return tripDao.findById(tripId);
	}
	
	public List<Trip> findAll() {
		return tripDao.findAll();
	}
}
