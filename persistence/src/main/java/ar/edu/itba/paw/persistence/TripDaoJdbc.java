package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.TripDao;
import ar.edu.itba.paw.models.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TripDaoJdbc implements TripDao {

	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsertTrips;
	private final SimpleJdbcInsert jdbcInsertRelation;

	@Autowired
	public TripDaoJdbc(final DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		/* TODO: export table name as a private final String */
		this.jdbcInsertTrips = new SimpleJdbcInsert(jdbcTemplate).withTableName("trips");
		this.jdbcInsertRelation = new SimpleJdbcInsert(jdbcTemplate).withTableName("trips_users");
		/* TODO: export table creation as a private final String */
		jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS trips_users (id serial PRIMARY KEY, trip_id integer, user_id integer)");
		jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS trips (id serial PRIMARY KEY, driver_id integer, cost real, eta varchar(100), etd varchar(100))");
	}
	
	public Trip create(Trip trip) {
		final Map<String, Object> args = new HashMap<String, Object>();
		//args.put("username", username);
		//args.put("password", password);

		//jdbcInsert.execute(args);
		return null;
		//return new Trip(username, password);
	}
	
	public void reserveTrip(Integer tripId) {
		jdbcTemplate.update("INSERT INTO trips_users (trip_id, user_id) VALUES (?,?)", new Object[] { tripId, new Integer(1)});
		return;
	}
	
	public void unreserveTrip(Integer tripId) {
		jdbcTemplate.update("DELETE FROM trips_users WHERE trip_id = ? AND user_id = ?", new Object[] { tripId, new Integer(1)});
		return;
	}
	
	public List<Trip> findByPassenger(final Integer passengerId) {
		List<Trip> trips = new ArrayList<>();
		this.jdbcTemplate.query("SELECT * FROM trips JOIN trips_users ON trips.id = trips_users.trip_id WHERE trips.id = ?", new Object[] {passengerId}, (final ResultSet rs) -> {
			do {
				Trip trip = new Trip();
				
				trip.setId(rs.getInt("id"));
				trip.setEtd(rs.getString("etd"));
				trip.setEta(rs.getString("eta"));
				//trip.addPassenger(rs.getInt("user_id"));
				trip.setCost(rs.getDouble("cost"));
				trip.setReserved(passengerId.equals(rs.getInt("trips_users.user_id")));
				
				/*
				trip.setDriver_id(rs.getInt("driver_id"));
				trip.setCar_id(rs.getInt("car_id"));
				trip.setDeparture_location(rs.getString("departure_location"));
				trip.setArrival_location(rs.getString("arrival_location"));
	            */
				trips.add(trip);
	        } while(rs.next());
		});

		return trips;
	}
	
	public List<Trip> findAll() {
		List<Trip> trips = new ArrayList<>();
		Integer passengerId = 1; // Should get the logged user
		this.jdbcTemplate.query("SELECT * FROM trips LEFT OUTER JOIN trips_users ON trips.id = trips_users.trip_id", (final ResultSet rs) -> {
			do {
				Trip trip = new Trip();
				
				trip.setId(rs.getInt("id"));
				trip.setEtd(rs.getString("etd"));
				trip.setEta(rs.getString("eta"));
				trip.setCost(rs.getDouble("cost"));
				trip.setReserved(passengerId.equals(rs.getInt("user_id")));
				/*
				trip.setId(rs.getInt("id"));
				trip.setDriver_id(rs.getInt("driver_id"));
				trip.setCar_id(rs.getInt("car_id"));
				trip.setDeparture_location(rs.getString("departure_location"));
				trip.setArrival_location(rs.getString("arrival_location"));
				trip.setCost(rs.getDouble("cost"));
	            */
				trips.add(trip);
	        } while(rs.next());
		});

		return trips;
	}
	
	public Trip findById(final String tripId) {
		Trip trip = new Trip();
		/*this.jdbcTemplate.query("SELECT * FROM trips WHERE id = \'" + tripId + "\' LIMIT 1", (final ResultSet rs) -> {
			trip.setUsername(rs.getString("username"));
			trip.setPassword(rs.getString("password"));
		});*/

		return trip;
	}
}
