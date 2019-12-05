package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.ReviewDao;
import ar.edu.itba.paw.interfaces.TripDao;
import ar.edu.itba.paw.models.Pagination;
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
import javax.persistence.TypedQuery;

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
		console.info("Persistence: Creating trip");
		Timestamp now = new Timestamp(System.currentTimeMillis());
		trip.setDriver(driver);
		trip.setCreated(now);

		em.persist(trip);
		return trip;
	}

	public void reserveTrip(Integer tripId, User user) {
		console.info("Persistence: Reserving trip");
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
		console.info("Persistance: Unreserving trip");
		String query = "SELECT r FROM Reservation r WHERE trip.id = :tripId AND user = :user";

		List<Reservation> reservations = em.createQuery(query, Reservation.class)
										  .setParameter("tripId", tripId)
										  .setParameter("user", user)
										  .getResultList();

		if (!reservations.isEmpty()) em.remove(reservations.get(0));
		return;
	}

	public void delete(Integer tripId, User user) {
		console.info("Persistence: Deleting trip");
		Trip trip = findById(tripId);

		if (trip != null) {
			trip.setDeleted(true);
			em.merge(trip);
		}

		return;
	}

	public List<Trip> findByRoute(Search search, Pagination pagination, User driver) {
		// Get available trips by a route
		console.info("Persistence: Searching trips");
		String operator = driver == null ? "is not null" : "!= :driver";
		String query = "FROM Trip t WHERE t.deleted = FALSE AND lower(t.from_city) LIKE :from AND lower(t.to_city) LIKE :to AND etd >= :when AND (SELECT count(r.id) FROM Reservation r WHERE r.trip = t) < t.seats AND t.driver " + operator + " ORDER BY etd ASC";
		
		TypedQuery<Trip> partialQuery = em.createQuery(query, Trip.class)
										  .setParameter("from", "%" + search.getFrom().toLowerCase() + "%")
										  .setParameter("to", "%" + search.getTo().toLowerCase() + "%")
										  .setParameter("when", search.getWhen())
										  .setFirstResult(pagination.getFirstResult())
										  .setMaxResults(pagination.getPer_page());
		
		if (driver == null) return partialQuery.getResultList();
		
		List<Trip> trips = partialQuery.setParameter("driver", driver)
						     		   .getResultList();

		return trips;
	}


	public List<Trip> getUserTrips(User user, Pagination pagination) {
		console.info("Persistence: Get owned trips");
		// Get trips owned by a given user
		String query = "FROM Trip t WHERE t.deleted = FALSE AND t.driver = :user ORDER BY eta ASC";

		List<Trip> trips = em.createQuery(query, Trip.class)
						     .setParameter("user", user)
						     .setFirstResult(pagination.getFirstResult())
						     .setMaxResults(pagination.getPer_page())
						     .getResultList();
		
		return trips;
	}

	public List<Reservation> getReservationsByUser(User user, Pagination pagination) {
		console.info("Persistence: Get reservations");
		String query = "FROM Reservation r WHERE user = :user";
				
		List<Reservation> reserves  = em.createQuery(query, Reservation.class)
										.setParameter("user", user)
										.setFirstResult(pagination.getFirstResult())
										.setMaxResults(pagination.getPer_page())
										.getResultList();
		
		return reserves;
				
	}

	public List<Trip> getReservedTrips(User user, Pagination pagination) {
		List<Trip> trips = user.getReservations().stream().map((reservation) -> reservation.getTrip())
											.filter((trip) -> reviewDao.canLeaveReview(trip, user))
											.collect(Collectors.toList());

		return trips;

	}
	
	public List<Trip> getSuggestions(String origin, Pagination pagination, User driver) {
		console.info("Persistence: Get suggestions");
		String queryCount = "SELECT count(t.id) FROM Trip t WHERE t.deleted = FALSE AND t.etd > CURRENT_TIMESTAMP() AND lower(t.from_city) LIKE :from";
		String query = "FROM Trip t WHERE t.deleted = FALSE AND t.etd > CURRENT_TIMESTAMP() AND lower(t.from_city) LIKE :from ORDER BY etd ASC";
		Long count = em.createQuery(queryCount, Long.class).setParameter("from", "%" + origin.toLowerCase() + "%").getSingleResult();
		List<Trip> trips = em.createQuery(query, Trip.class)
							 .setParameter("from", "%" + origin.toLowerCase() + "%")
							 .setFirstResult(pagination.getFirstResult())
							 .setMaxResults(pagination.getPer_page())
							 .getResultList();
		
		if (trips.size() < pagination.getPer_page()) {
			query = "FROM Trip t WHERE t.deleted = FALSE AND t.etd > CURRENT_TIMESTAMP() AND lower(t.from_city) NOT LIKE :from ORDER BY etd ASC";
			trips = em.createQuery(query, Trip.class)
								 .setParameter("from", "%" + origin.toLowerCase() + "%")
								 .setFirstResult((int) Math.max(pagination.getFirstResult() - count, 0))
								 .setMaxResults(pagination.getPer_page())
								 .getResultList();
			return trips;
		}
		
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
