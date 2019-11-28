package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.ReviewDao;
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
	
	@Autowired
	private ReviewDao reviewDao;

	@Transactional
	public Trip register(final Trip trip, final User driver) {
		return tripDao.create(trip, driver);
	}

	@Transactional
	public void reserve(Integer tripId, User user) {
		tripDao.reserveTrip(tripId, user);
		return;
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
		// TODO: ver si va ese filter
		return reserves.stream().map(reservation -> reservation.getTrip()).collect(Collectors.toList());
	}

	public List<Trip> getUserTrips(User user, Pagination pagination) {
		List<Trip> trips = tripDao.getUserTrips(user, pagination);
		
		return trips.stream().filter((trip) -> !trip.getExpired())
							 .distinct()
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
		
		for(Trip trip: trips) {
			trip.setReserved(trip.getPassengers().contains(user));
		}
		
		return filterExpired(trips);
	}

	public List<Trip> getSuggestions(User user, Search search) {
		List<Trip> trips = user == null ? findAfterDateByRoute(search) : findAfterDateByRoute(user, search);
		
		for(Trip trip: trips) {
			trip.setReserved(trip.getPassengers().contains(user));
		}
		
		return trips.subList(0, trips.size() >= 10 ? 9 : trips.size());

	}

	public List<Trip> findAfterDateByRoute(User user, Search search) {
		List<Trip> trips = tripDao.findByRouteWithDateComparision(user, search, ">");
		
		for(Trip trip: trips) {
			trip.setReserved(trip.getPassengers().contains(user));
		}
		
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
