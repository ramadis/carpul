package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.TripDao;
import ar.edu.itba.paw.models.Position;
import ar.edu.itba.paw.models.Reservation;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.Search;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TripDaoHibernate implements TripDao {

	@PersistenceContext
	private EntityManager em;
	
	public Trip create(Trip trip, User driver) {
		System.out.println("CHECK 3");
		Timestamp now = new Timestamp(System.currentTimeMillis());
		trip.setDriver(driver);
		System.out.println("CHECK 4");
		trip.setCreated(now);
		System.out.println("CHECK 5");
		
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
		
		System.out.println("CHECK AFTER GENERATE RESERVATION");
		
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
		String query = "SELECT t FROM Trip t WHERE trip.id = :tripId";
		
		List<Trip> trips = em.createQuery(query, Trip.class)
  						     .setParameter("tripId", tripId)
						     .getResultList();
		
		if (!trips.isEmpty()) {
			Trip trip = trips.get(0);
			trip.setDeleted(true);
			em.merge(trip);
		}
		return;
	}
	
	public List<Trip> findByRouteWithDateComparision(Search search, String comparision) {
		// Get available trips by a route
		// TODO: Check where and how to set trip.reserved
		String query = "SELECT t FROM Trip t WHERE t.deleted = FALSE AND lower(t.from_city) LIKE :from AND lower(t.to_city) LIKE :to ORDER BY etd ASC";
		
		List<Trip> trips = em.createQuery(query, Trip.class)
						     .setParameter("from", "%" + search.getFrom().toLowerCase() + "%")
						     .setParameter("to", "%" + search.getTo().toLowerCase() + "%")
						     .getResultList();

		if (comparision == ">" ) {
			return trips.stream().filter((trip) -> trip.getEtd().after(search.getWhen()))
								 .collect(Collectors.toList());
		} else if (comparision == "=") {
			return trips.stream().filter((trip) -> trip.getEtd().equals(search.getWhen()))
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
						     .setParameter("user", user)
						     .getResultList();

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
		// TODO: Check if is just a wrapper or an actual user
		return user.getReservations().stream().map((reservation) -> reservation.getTrip())
											  .filter((trip) -> trip.getReviews().stream().filter((review) -> review.getOwner().equals(user)).collect(Collectors.toList()).isEmpty())
											  .collect(Collectors.toList());
		
	}

	private Boolean isReserved(Trip trip) {
		String query = "SELECT r FROM Reservation r WHERE trip = :trip";
		
		List<Reservation> reservations = em.createQuery(query, Reservation.class)
										  .setParameter("trip", trip)
										  .getResultList();
		
		return !reservations.isEmpty();
	}

	public Trip findById(final Integer tripId) {
		return em.find(Trip.class, tripId);
	}
}
