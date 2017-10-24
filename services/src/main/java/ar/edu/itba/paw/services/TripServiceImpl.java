package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.TripDao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.interfaces.TripService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.Search;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

@Service
public class TripServiceImpl implements TripService {

	@Autowired
	private TripDao tripDao;
	
	@Autowired
	private UserService us;

	public Trip register(final Trip trip, final User driver) {
		return tripDao.create(trip, driver);
	}
	
	public void reserve(Integer tripId, User user) {
		tripDao.reserveTrip(tripId, user);
	}
	
	public void unreserve(Integer tripId, User user) {
		tripDao.unreserveTrip(tripId, user);
	}
	
	public Trip findById(final Integer tripId) {
		return tripDao.findById(tripId);
	}
	
	public List<Trip> getReservedTrips(User user) {
		return tripDao.getReservedTrips(user);
	}
	
	public List<Trip> getUserTrips(User user) {
		List<Trip> trips = tripDao.getUserTrips(user);
		
		for(Trip t: trips) {
			t.setPassengers(us.getPassengers(t));
			t.setOccupied_seats(t.getPassengers().size());
		}
		
		return trips;
	}
	
	public void delete(Integer tripId, User user) {
		tripDao.delete(tripId, user);
	}
	
	public List<Trip> findByRoute(User user, Search search) {
		return tripDao.findByRouteWithDateComparision(user, search, "=");
	}
	
	public List<Trip> findAfterDateByRoute(User user, Search search) {
		return tripDao.findByRouteWithDateComparision(user, search, ">");
	}
}
