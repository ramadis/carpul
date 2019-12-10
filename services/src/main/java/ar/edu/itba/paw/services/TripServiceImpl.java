package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.TripDao;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.paw.interfaces.TripService;
import ar.edu.itba.paw.models.Pagination;
import ar.edu.itba.paw.models.Reservation;
import ar.edu.itba.paw.models.Search;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

@Service
public class TripServiceImpl implements TripService {

	@Autowired
	private TripDao tripDao;
	
	@Transactional
	public Trip register(final Trip trip, final User driver) {
		return tripDao.create(trip, driver);
	}

	@Transactional
	public void reserve(Integer tripId, User user) {
		tripDao.reserveTrip(tripId, user);
		return;
	}
	
	public Boolean areTimeConflicts(Trip trip, User user) {
		return tripDao.areReservationConflicts(trip, user) || tripDao.areDrivingConflicts(trip, user);
	}

	@Transactional
	public void unreserve(Integer tripId, User user) {
		tripDao.unreserveTrip(tripId, user);
	}

	public Trip findById(final Integer tripId) {
		Trip trip = tripDao.findById(tripId);
		return trip;
	}

	public List<Trip> getReservedTrips(User user, Pagination pagination) {
		List<Reservation> reserves = tripDao.getReservationsByUser(user, pagination);
		// TODO: ver si va este filter
		return reserves.stream().map(reservation -> reservation.getTrip()).collect(Collectors.toList());
	}

	public List<Trip> getUserTrips(User user, Pagination pagination) {
		List<Trip> trips = tripDao.getUserTrips(user, pagination);
		
		return trips;
	}

	@Transactional
	public void delete(Integer tripId, User user) {
		tripDao.delete(tripId, user);
	}

	public List<Trip> getSuggestions(String origin, Pagination pagination, User driver) {
		// TODO: improve this
		List<Trip> trips = tripDao.getSuggestions(origin, pagination, driver);
		return trips;
	}

	public List<Trip> findByRoute(Search search, Pagination pagination, User driver) {
		List<Trip> trips = tripDao.findByRoute(search, pagination, driver);
		return trips;
	}

}
