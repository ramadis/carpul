package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.TripDao;
import ar.edu.itba.paw.models.Pagination;
import ar.edu.itba.paw.models.Reservation;
import ar.edu.itba.paw.models.Search;
import ar.edu.itba.paw.models.SearchResult;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class TripDaoHibernate implements TripDao {
	private final static Logger console = LoggerFactory.getLogger(TripDaoHibernate.class);

	@PersistenceContext
	private EntityManager em;

	public Trip create(Trip trip, User driver) {
		console.info("Persistence: Creating trip");
		Timestamp now = new Timestamp(System.currentTimeMillis());
		trip.setDriver(driver);
		trip.setCreated(now);

		em.persist(trip);
		return trip;
	}

	public Reservation reserveTrip(Integer tripId, User user) {
		console.info("Persistence: Reserving trip");
		Timestamp now = new Timestamp(System.currentTimeMillis());
		Trip trip = findById(tripId);

		// Create reservation
		Reservation reserve = new Reservation();
		reserve.setTrip(trip);
		reserve.setUser(user);
		reserve.setCreated(now);

		em.persist(reserve);
		
		return reserve;
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
	
	public Boolean areDrivingConflicts(Trip trip, User user) {
		console.info("Persistence: Checking if there are any conflicts for the user {} creating a new trip", user.getId());
		String query = "SELECT count(t.id) FROM Trip t WHERE t.driver = :user AND (:tripEtd >= t.etd  AND :tripEtd <= t.eta OR :tripEta >= t.etd AND :tripEta <= t.eta OR :tripEtd <= t.etd AND :tripEta >= t.eta)";
		
		Long count = em.createQuery(query, Long.class)
							.setParameter("user", user)
							.setParameter("tripEtd", trip.getEtd())
							.setParameter("tripEta", trip.getEta())
							.getSingleResult();
		
		return count > 0;
	}
	
	public Boolean areReservationConflicts(Trip trip, User user) {
		console.info("Persistence: Checking if there are any conflicts for the user {} reserving the trip", user.getId());
		String query = "SELECT count(r.id) FROM Reservation r WHERE r.user = :user AND (:tripEtd >= r.trip.etd  AND :tripEtd <= r.trip.eta OR :tripEta >= r.trip.etd AND :tripEta <= r.trip.eta OR :tripEtd <= r.trip.etd AND :tripEta >= r.trip.eta)";
		
		Long count = em.createQuery(query, Long.class)
							.setParameter("user", user)
							.setParameter("tripEtd", trip.getEtd())
							.setParameter("tripEta", trip.getEta())
							.getSingleResult();
		
		return count > 0;
	}
	
	private Long executeSearchCount(String query, Search search, Pagination pagination, User driver) {
		
		String finalQuery = "SELECT count(t.id) " + query.replace("ORDER BY etd ASC", "");
		TypedQuery<Long> partialQuery =  em.createQuery(finalQuery, Long.class)
				 						   .setParameter("when", search.getWhen());
		
		if (query.contains(":driver")) partialQuery.setParameter("driver", driver);
		if (query.contains(":arrLat")) partialQuery.setParameter("arrLat", search.getArrival().getLatitude());
		if (query.contains(":arrLon")) partialQuery.setParameter("arrLon", search.getArrival().getLongitude());
		if (query.contains(":depLat")) partialQuery.setParameter("depLat", search.getDeparture().getLatitude());
		if (query.contains(":depLon")) partialQuery.setParameter("depLon", search.getDeparture().getLongitude());
		if (query.contains(":maxDist")) partialQuery.setParameter("maxDist", (double) 15);
		if (query.contains(":from")) partialQuery.setParameter("from", "%" + search.getFrom().toLowerCase() + "%");
		if (query.contains(":to")) partialQuery.setParameter("to", "%" + search.getTo().toLowerCase() + "%");

		return partialQuery.getSingleResult();
	}
	
	private List<Trip> executeSearch(String query, Search search, Pagination pagination, User driver) {
		TypedQuery<Trip> partialQuery =  em.createQuery(query, Trip.class)
				 .setParameter("when", search.getWhen())
				 .setFirstResult(pagination.getFirstResult())
				 .setMaxResults(pagination.getPer_page());
		
		if (query.contains(":driver")) partialQuery.setParameter("driver", driver);
		if (query.contains(":arrLat")) partialQuery.setParameter("arrLat", search.getArrival().getLatitude());
		if (query.contains(":arrLon")) partialQuery.setParameter("arrLon", search.getArrival().getLongitude());
		if (query.contains(":depLat")) partialQuery.setParameter("depLat", search.getDeparture().getLatitude());
		if (query.contains(":depLon")) partialQuery.setParameter("depLon", search.getDeparture().getLongitude());
		if (query.contains(":maxDist")) partialQuery.setParameter("maxDist", (double) 15);
		if (query.contains(":from")) partialQuery.setParameter("from", "%" + search.getFrom().toLowerCase() + "%");
		if (query.contains(":to")) partialQuery.setParameter("to", "%" + search.getTo().toLowerCase() + "%");

		return partialQuery.getResultList();
	}
	

	public SearchResult searchByRest(Search search, Pagination pagination, User driver) {
		String operatorDriver = driver == null ? "is not null" : "!= :driver";
		String initialQuery = "FROM Trip t WHERE t.deleted = FALSE AND etd >= :when AND t.driver " + operatorDriver + " AND (SELECT count(r.id) FROM Reservation r WHERE r.trip = t) < t.seats";

		String firstLevelQuery = initialQuery;
		if (search.getArrival().isValid()) firstLevelQuery += " AND 1.60934 * 2 * 3961 * asin(sqrt((sin(radians((t.arrival_lat - :arrLat) / 2))) * (sin(radians((t.arrival_lat - :arrLat) / 2))) + cos(radians(:arrLat)) * cos(radians(t.arrival_lat)) * (sin(radians((t.arrival_lon - :arrLon) / 2))) * (sin(radians((t.arrival_lon - :arrLon) / 2))) )) < :maxDist";
		if (search.getDeparture().isValid()) firstLevelQuery += " AND 1.60934 * 2 * 3961 * asin(sqrt((sin(radians((t.departure_lat - :depLat) / 2))) * (sin(radians((t.departure_lat - :depLat) / 2))) + cos(radians(:depLat)) * cos(radians(t.departure_lat)) * (sin(radians((t.departure_lon - :depLon) / 2))) * (sin(radians((t.departure_lon - :depLon) / 2))) )) < :maxDist";
		if (!(search.getDeparture().isValid())) firstLevelQuery += " AND lower(t.from_city) LIKE :from";
		if (!(search.getArrival().isValid())) firstLevelQuery += " AND lower(t.to_city) LIKE :to";
		firstLevelQuery += " ORDER BY etd ASC";
		
		String secondLevelQuery = initialQuery;
		if (search.getDeparture().isValid()) secondLevelQuery += " AND 1.60934 * 2 * 3961 * asin(sqrt((sin(radians((t.departure_lat - :depLat) / 2))) * (sin(radians((t.departure_lat - :depLat) / 2))) + cos(radians(:depLat)) * cos(radians(t.departure_lat)) * (sin(radians((t.departure_lon - :depLon) / 2))) * (sin(radians((t.departure_lon - :depLon) / 2))) )) < :maxDist";
		if (!(search.getDeparture().isValid())) secondLevelQuery += " AND lower(t.from_city) LIKE :from";
		secondLevelQuery += " AND t NOT IN (" + firstLevelQuery + ") ORDER BY etd ASC";
		
		String thirdLevelQuery = initialQuery + " AND (t NOT IN (" + secondLevelQuery + ") AND t NOT IN (" + firstLevelQuery + ")) ORDER BY etd ASC";
		List<Trip> trips = this.executeSearch(thirdLevelQuery, search, pagination, driver);
		Long count = this.executeSearchCount(thirdLevelQuery, search, pagination, driver);
		return new SearchResult(trips, count);
	}
	
	public SearchResult searchByOrigin(Search search, Pagination pagination, User driver) {
		String operatorDriver = driver == null ? "is not null" : "!= :driver";
		String initialQuery = "FROM Trip t WHERE t.deleted = FALSE AND etd >= :when AND t.driver " + operatorDriver + " AND (SELECT count(r.id) FROM Reservation r WHERE r.trip = t) < t.seats";

		String firstLevelQuery = initialQuery;
		if (search.getArrival().isValid()) firstLevelQuery += " AND 1.60934 * 2 * 3961 * asin(sqrt((sin(radians((t.arrival_lat - :arrLat) / 2))) * (sin(radians((t.arrival_lat - :arrLat) / 2))) + cos(radians(:arrLat)) * cos(radians(t.arrival_lat)) * (sin(radians((t.arrival_lon - :arrLon) / 2))) * (sin(radians((t.arrival_lon - :arrLon) / 2))) )) < :maxDist";
		if (search.getDeparture().isValid()) firstLevelQuery += " AND 1.60934 * 2 * 3961 * asin(sqrt((sin(radians((t.departure_lat - :depLat) / 2))) * (sin(radians((t.departure_lat - :depLat) / 2))) + cos(radians(:depLat)) * cos(radians(t.departure_lat)) * (sin(radians((t.departure_lon - :depLon) / 2))) * (sin(radians((t.departure_lon - :depLon) / 2))) )) < :maxDist";
		if (!(search.getDeparture().isValid())) firstLevelQuery += " AND lower(t.from_city) LIKE :from";
		if (!(search.getArrival().isValid())) firstLevelQuery += " AND lower(t.to_city) LIKE :to";
		firstLevelQuery += " ORDER BY etd ASC";
		
		String secondLevelQuery = initialQuery;
		if (search.getDeparture().isValid()) secondLevelQuery += " AND 1.60934 * 2 * 3961 * asin(sqrt((sin(radians((t.departure_lat - :depLat) / 2))) * (sin(radians((t.departure_lat - :depLat) / 2))) + cos(radians(:depLat)) * cos(radians(t.departure_lat)) * (sin(radians((t.departure_lon - :depLon) / 2))) * (sin(radians((t.departure_lon - :depLon) / 2))) )) < :maxDist";
		if (!(search.getDeparture().isValid())) secondLevelQuery += " AND lower(t.from_city) LIKE :from";
		secondLevelQuery += " AND t NOT IN (" + firstLevelQuery + ") ORDER BY etd ASC";
		
		List<Trip> trips = this.executeSearch(secondLevelQuery, search, pagination, driver);
		Long count = this.executeSearchCount(secondLevelQuery, search, pagination, driver);
		return new SearchResult(trips, count);
	}
	
	public SearchResult searchByClosest(Search search, Pagination pagination, User driver) {
		String operatorDriver = driver == null ? "is not null" : "!= :driver";
		String initialQuery = "FROM Trip t WHERE t.deleted = FALSE AND etd >= :when AND t.driver " + operatorDriver + " AND (SELECT count(r.id) FROM Reservation r WHERE r.trip = t) < t.seats";

		String firstLevelQuery = initialQuery;
		if (search.getArrival().isValid()) firstLevelQuery += " AND 1.60934 * 2 * 3961 * asin(sqrt((sin(radians((t.arrival_lat - :arrLat) / 2))) * (sin(radians((t.arrival_lat - :arrLat) / 2))) + cos(radians(:arrLat)) * cos(radians(t.arrival_lat)) * (sin(radians((t.arrival_lon - :arrLon) / 2))) * (sin(radians((t.arrival_lon - :arrLon) / 2))) )) < :maxDist";
		if (search.getDeparture().isValid()) firstLevelQuery += " AND 1.60934 * 2 * 3961 * asin(sqrt((sin(radians((t.departure_lat - :depLat) / 2))) * (sin(radians((t.departure_lat - :depLat) / 2))) + cos(radians(:depLat)) * cos(radians(t.departure_lat)) * (sin(radians((t.departure_lon - :depLon) / 2))) * (sin(radians((t.departure_lon - :depLon) / 2))) )) < :maxDist";
		if (!(search.getDeparture().isValid())) firstLevelQuery += " AND lower(t.from_city) LIKE :from";
		if (!(search.getArrival().isValid())) firstLevelQuery += " AND lower(t.to_city) LIKE :to";
		firstLevelQuery += " ORDER BY etd ASC";
		
		List<Trip> trips = this.executeSearch(firstLevelQuery, search, pagination, driver);
		Long count = this.executeSearchCount(firstLevelQuery, search, pagination, driver);
		return new SearchResult(trips, count);
	}

	public List<Trip> getUserTrips(User user, Pagination pagination) {
		console.info("Persistence: Get owned trips");
		// Get trips owned by a given user
		String query = "FROM Trip t WHERE t.deleted = FALSE AND t.driver = :user ORDER BY etd DESC";

		List<Trip> trips = em.createQuery(query, Trip.class)
						     .setParameter("user", user)
						     .setFirstResult(pagination.getFirstResult())
						     .setMaxResults(pagination.getPer_page())
						     .getResultList();
		
		return trips;
	}

	public List<Reservation> getReservationsByUser(User user, Pagination pagination, Boolean exlcudeReviewed) {
		console.info("Persistence: Get reservations");
		String query = "FROM Reservation r WHERE user = :user" + (exlcudeReviewed ? " AND NOT EXISTS (FROM Review rw WHERE rw.owner = :user AND rw.trip = r.trip) " : " ") + "ORDER BY r.trip.etd DESC";
				
		List<Reservation> reserves  = em.createQuery(query, Reservation.class)
										.setParameter("user", user)
										.setFirstResult(pagination.getFirstResult())
										.setMaxResults(pagination.getPer_page())
										.getResultList();
		
		return reserves;
				
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
