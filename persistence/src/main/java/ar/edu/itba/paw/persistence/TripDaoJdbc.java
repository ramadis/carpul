package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.TripDao;
import ar.edu.itba.paw.models.Position;
import ar.edu.itba.paw.models.Search;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TripDaoJdbc implements TripDao {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public TripDaoJdbc(final DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private static void loadResultIntoTrip(ResultSet rs, Trip trip) {
		try {
			trip.setId(rs.getInt("id"));
			trip.setCreated(rs.getTimestamp("created"));
			trip.setEtd(rs.getTimestamp("etd"));
			trip.setEta(rs.getTimestamp("eta"));
			trip.setCost(rs.getDouble("cost"));
			trip.setSeats(rs.getInt("seats"));
			trip.setFrom_city(rs.getString("from_city"));
			trip.setTo_city(rs.getString("to_city"));
			trip.setDriver_id(rs.getInt("driver_id"));

			Long now = System.currentTimeMillis();
			trip.setExpired(now > trip.getEta().getTime());

			Position departure = new Position(rs.getDouble("departure_lat"), rs.getDouble("departure_lon"));
			Position arrival = new Position(rs.getDouble("arrival_lat"), rs.getDouble("arrival_lon"));
			trip.setArrival(arrival);
			trip.setDeparture(departure);
		} catch (Exception e) {}
	}

	public Trip create(Trip trip, User driver) {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		String query = "INSERT INTO trips (from_city, to_city, created, seats, driver_id, cost, eta, etd, departure_lat, departure_lon, arrival_lat, arrival_lon) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		Object[] params = new Object[] { trip.getFrom_city(), trip.getTo_city(), now, trip.getSeats(), driver.getId(), trip.getCost(), trip.getEta(), trip.getEtd(), trip.getDeparture().getLatitude(), trip.getDeparture().getLongitude(), trip.getArrival().getLatitude(), trip.getArrival().getLongitude() };

		jdbcTemplate.update(query, params);
		return trip;
	}

	public void reserveTrip(Integer tripId, User user) {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		String query = "INSERT INTO trips_users (created, trip_id, user_id) VALUES (?, ?,?)";
		Object[] params = new Object[] { now, tripId, user.getId() };

		jdbcTemplate.update(query, params);
		return;
	}

	public void unreserveTrip(Integer tripId, User user) {
		String query = "DELETE FROM trips_users WHERE trip_id = ? AND user_id = ?";
		Object[] params = new Object[] { tripId, user.getId() };

		jdbcTemplate.update(query, params);
		return;
	}

	public void delete(Integer tripId, User user) {
		String query = "UPDATE trips SET deleted = TRUE WHERE id = ? AND driver_id = ?";
		Object[] params =new Object[] { tripId, user.getId() };
		jdbcTemplate.update(query, params);
		return;
	}

	public List<Trip> findByRouteWithDateComparision(User user, Search search, String comparision) {
		// Get available trips by a user and a route

		List<Trip> trips = new ArrayList<>();
		String from = search.getFrom().toLowerCase();
		String to = search.getTo().toLowerCase();

		String query = "SELECT users.id as userId, first_name, last_name, phone_number, trips.*, temp.reserved as is_reserved FROM trips JOIN users ON trips.driver_id = users.id LEFT OUTER JOIN (SELECT id as reserved, trip_id as relation_trip_id FROM trips_users WHERE user_id = ?) as temp ON relation_trip_id = trips.id WHERE driver_id <> ? AND LOWER(from_city) LIKE ? AND LOWER(to_city) LIKE ? AND etd::date::timestamp " + comparision + " ? ORDER BY etd ASC";
		Object[] params = new Object[] { user.getId(), user.getId(), "%" + from + "%", "%" + to + "%", search.getWhen() };

		this.jdbcTemplate.query(query, params, (final ResultSet rs) -> {
			do {
				Trip trip = new Trip();

				loadResultIntoTrip(rs, trip);
				trip.setOccupied_seats(getPassengerAmount(trip));
				if (trip.getAvailable_seats() < 1) continue;

				User driver = new User();
				UserDaoJdbc.loadReducedResultIntoUser(rs, driver);
				driver.setId(rs.getInt("userId"));
				trip.setDriver(driver);

				trips.add(trip);
	        } while(rs.next());
		});

		return trips;
	}

	public List<Trip> getUserTrips(User user) {
		// Get trips owned by a given user

		List<Trip> trips = new ArrayList<>();

		String query = "SELECT * FROM trips WHERE trips.driver_id = ?";
		Object[] params = new Object[] { user.getId() };

		this.jdbcTemplate.query(query, params, (final ResultSet rs) -> {
			do {
				Trip trip = new Trip();
				loadResultIntoTrip(rs, trip);
				trips.add(trip);
	        } while(rs.next());
		});

		return trips;
	}

	private Integer getPassengerAmount(Trip trip) {
		String query = "SELECT count(*) as amount FROM trips_users WHERE trip_id = ?";
		Object[] params = new Object[] { trip.getId() };
		List<Integer> ints = new ArrayList<>();

		this.jdbcTemplate.query(query, params, (final ResultSet rs) -> {
			ints.add(rs.getInt("amount"));
		});

		return ints.size() > 0 ? ints.get(0) : 0;
	}

	public List<Trip> getReservedTrips(User user) {
		// Get no-reviewed trips for a given user
		List<Trip> trips = new ArrayList<>();
		String query = "SELECT trips.*, trips_users.*, users.*, users.id as userIdAux FROM trips JOIN trips_users ON trip_id = trips.id JOIN users ON users.id = driver_id WHERE user_id = ? AND NOT EXISTS (SELECT * FROM reviews WHERE owner_id = ? AND trip_id = trips.id)";
		Object[] params = new Object[] { user.getId(), user.getId() };

		this.jdbcTemplate.query(query, params, (final ResultSet rs) -> {
			do {
				Trip trip = new Trip();

				loadResultIntoTrip(rs, trip);
				trip.setOccupied_seats(getPassengerAmount(trip));
				User driver = new User();
				UserDaoJdbc.loadResultIntoUser(rs, driver);
				driver.setId(rs.getInt("userIdAux"));
				trip.setDriver(driver);

				trips.add(trip);
	        } while(rs.next());
		});

		return trips;
	}

	private Boolean isReserved(Trip trip) {
		String query = "SELECT id FROM trips_users WHERE trip_id = ? AND user_id = ?";
		Object[] params = new Object[] { trip.getId(), trip.getDriver_id() };
		List<Boolean> bools = new ArrayList<>();

		this.jdbcTemplate.query(query, params, (final ResultSet rs) -> {
			bools.add(rs.next());
		});

		return bools.size() > 0 ? bools.get(0) : false;
	}

	public Trip findById(final Integer tripId) {
		Trip trip = new Trip();

		String query = "SELECT trips.*, users.id as userId FROM trips LEFT OUTER JOIN users ON driver_id = users.id  WHERE trips.id = ? LIMIT 1";
		Object[] params = new Object[] { tripId };

		this.jdbcTemplate.query(query, params, (final ResultSet rs) -> {
			loadResultIntoTrip(rs, trip);

			trip.setReserved(isReserved(trip));
			trip.setOccupied_seats(getPassengerAmount(trip));

			User driver = new User();
			UserDaoJdbc.loadResultIntoUser(rs, driver);
			driver.setId(rs.getInt("userId"));
			trip.setDriver(driver);
		});

		return trip;
	}
}
