package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.ReviewDao;
import ar.edu.itba.paw.interfaces.TripDao;
import ar.edu.itba.paw.models.Reservation;
import ar.edu.itba.paw.models.Search;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TripDaoHibernate implements TripDao {
	private final static Logger console = LoggerFactory.getLogger(TripDaoHibernate.class);

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private ReviewDao reviewDao;

	public Trip create(Trip trip, User driver) {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		trip.setDriver(driver);
		trip.setCreated(now);

		em.persist(trip);
		return trip;
	}

	public void reserveTrip(Integer tripId, User user) {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		Trip trip = findById(tripId);

		// Create reservation
		Reservation reserve = new Reservation();
		reserve.setTrip(trip);
		reserve.setUser(user);
		reserve.setCreated(now);

		em.persist(reserve);
		return;
	}

	public void unreserveTrip(Integer tripId, User user) {
		String query = "SELECT r FROM Reservation r WHERE trip.id = :tripId AND user = :user";

		List<Reservation> reservations = em.createQuery(query, Reservation.class)
										  .setParameter("tripId", tripId)
										  .setParameter("user", user)
										  .getResultList();

		if (!reservations.isEmpty()) em.remove(reservations.get(0));
		return;
	}

	public void delete(Integer tripId, User user) {
		
		Trip trip = findById(tripId);

		if (trip != null) {
			trip.setDeleted(true);
			em.merge(trip);
		}

		return;
	}

	public List<Trip> findByRouteWithDateComparision(Search search, String comparision) {
		// Get available trips by a route
		String query = "SELECT t FROM Trip t WHERE t.deleted = FALSE AND lower(t.from_city) LIKE :from AND lower(t.to_city) LIKE :to ORDER BY etd ASC";

		List<Trip> trips = em.createQuery(query, Trip.class)
						     .setParameter("from", "%" + search.getFrom().toLowerCase() + "%")
						     .setParameter("to", "%" + search.getTo().toLowerCase() + "%")
						     .getResultList();

		if (comparision == ">" ) {
			return trips.stream().filter((trip) -> trip.getEtd().after(search.getWhen()))
								.filter((trip) -> trip.getPassengers().size() < trip.getSeats())
								 .collect(Collectors.toList());
		} else if (comparision == "=") {
			return trips.stream().filter((trip) -> trip.getEtd().equals(search.getWhen()))
					.filter((trip) -> trip.getPassengers().size() < trip.getSeats())
					 .collect(Collectors.toList());
		}

		return trips;
	}

	public List<Trip> findByRouteWithDateComparision(User user, Search search, String comparision) {
		List<Trip> trips = findByRouteWithDateComparision(search, comparision);

		return trips.stream().filter((trip) -> !trip.getDriver().equals(user))
							 .collect(Collectors.toList());
	}

	public List<Trip> getUserTrips(User user) {
		// Get trips owned by a given user
		String query = "SELECT t FROM Trip t WHERE t.deleted = FALSE AND t.driver = :user";

		List<Trip> trips = em.createQuery(query, Trip.class)
						     .setParameter("user", user.getId())
						     .getResultList();
		
		console.info("GET_USER_TRIPS");
		console.info(trips.toString());

		return trips;
	}

	private Integer getPassengerAmount(Trip trip) {
		String query = "SELECT r FROM Reservation r WHERE trip = :trip";

		List<Reservation> reserves = em.createQuery(query, Reservation.class)
  								     .setParameter("trip", trip)
								     .getResultList();

		return reserves.size();
	}

	public List<Trip> getReservedTrips(User user) {
		List<Trip> trips = user.getReservations().stream().map((reservation) -> reservation.getTrip())
											.filter((trip) -> reviewDao.canLeaveReview(trip, user))
											.collect(Collectors.toList());

		return trips;

	}

	private Boolean isReserved(Trip trip) {
		String query = "SELECT r FROM Reservation r WHERE trip = :trip";

		List<Reservation> reservations = em.createQuery(query, Reservation.class)
										  .setParameter("trip", trip)
										  .getResultList();

		return !reservations.isEmpty();
	}

	public Trip findById(final Integer tripId) {
		Trip trip = em.find(Trip.class, tripId);
		if (trip == null) return null;
		trip.setReserved(isReserved(trip));
		return trip;
	}
}
