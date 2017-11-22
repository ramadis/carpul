package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.TripDao;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Transactional
	public Trip register(final Trip trip, final User driver) {
		return tripDao.create(trip, driver);
	}

	@Transactional
	public void reserve(Integer tripId, User user) {
		// TODO: Check if tripId belongs to user. Check if it's not already reserved
		Trip trip = tripDao.findById(tripId);
		if (trip.getDriver().equals(user) || us.getPassengers(trip).contains(user)) return;
		tripDao.reserveTrip(tripId, user);
	}

	@Transactional
	public void unreserve(Integer tripId, User user) {
		tripDao.unreserveTrip(tripId, user);
	}

	public Trip findById(final Integer tripId) {
		// TODO: Evaluate performance issues
		Trip trip = tripDao.findById(tripId);
		return trip;
	}

	public List<Trip> getReservedTrips(User user) {
		return tripDao.getReservedTrips(user);
		//return user.getReservations().stream().map((reservation) -> reservation.getTrip()).collect(Collectors.toList());
	}

	public List<Trip> getUserTrips(User user) {
		List<Trip> trips = user.getDrived_trips();

		return trips.stream().filter((trip) -> !trip.getExpired() && !trip.getDeleted())
							 .collect(Collectors.toList());
	}

	@Transactional
	public void delete(Integer tripId, User user) {
		tripDao.delete(tripId, user);
	}
	
	private List<Trip> filterExpired(List<Trip> trips) {
		return trips.stream().filter((trip) -> !trip.getExpired() && !trip.getDeleted()).collect(Collectors.toList());
	}

	public List<Trip> findByRoute(User user, Search search) {
		List<Trip> trips = tripDao.findByRouteWithDateComparision(user, search, "=");
		return filterExpired(trips);
	}

	public List<Trip> findAfterDateByRoute(User user, Search search) {
		List<Trip> trips = tripDao.findByRouteWithDateComparision(user, search, ">");
		return filterExpired(trips);	}

	public List<Trip> findByRoute(Search search) {
		List<Trip> trips = tripDao.findByRouteWithDateComparision(search, "=");
		return filterExpired(trips);
	}

	public List<Trip> findAfterDateByRoute(Search search) {
		List<Trip> trips = tripDao.findByRouteWithDateComparision(search, ">");
		return filterExpired(trips);
	}
}
