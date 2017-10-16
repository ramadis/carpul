package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.TripDao;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TripDaoJdbc implements TripDao {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public TripDaoJdbc(final DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		/* TODO: export table name as a private final String */
		/* TODO: export table creation as a private final String */
		jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS trips_users (id serial PRIMARY KEY, created timestamp, trip_id integer, user_id integer)");
		jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS trips (id serial PRIMARY KEY, to_city varchar(100), from_city varchar(100), created timestamp, seats integer, driver_id integer, cost real, eta varchar(100), etd varchar(100), departure_gps varchar (300), arrival_gps varchar (300)");
	}
	
	private String stripAccents(String s) 
	{
	    s = Normalizer.normalize(s, Normalizer.Form.NFD);
	    s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
	    return s;
	}
	
	public Trip create(Trip trip, User driver) {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		jdbcTemplate.update("INSERT INTO trips (from_city, to_city, created, seats, driver_id, cost, eta, etd) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", new Object[] { trip.getFrom_city(), trip.getTo_city(), now, trip.getSeats(), driver.getId(), trip.getCost(), trip.getEta(), trip.getEtd()});
		return trip;
	}
	
	public void reserveTrip(Integer tripId, User user) {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		jdbcTemplate.update("INSERT INTO trips_users (created, trip_id, user_id) VALUES (?, ?,?)", new Object[] { now, tripId, user.getId()});
		return;
	}
	
	public void unreserveTrip(Integer tripId, User user) {
		jdbcTemplate.update("DELETE FROM trips_users WHERE trip_id = ? AND user_id = ?", new Object[] { tripId, user.getId()});
		return;
	}
	
	public void delete(Integer tripId, User user) {
		jdbcTemplate.update("DELETE FROM trips WHERE id = ? AND driver_id = ?", new Object[] { tripId, user.getId()});
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
	
	public List<Trip> findByRoute(User user, String from, String to) {
		List<Trip> trips = new ArrayList<>();
		System.out.println("from " + from + " to " + to);
		
		String newFrom = stripAccents(from);
		String newTo = stripAccents(to);
	    
	    
		this.jdbcTemplate.query("SELECT first_name, last_name, phone_number, trips.*, temp.reserved as is_reserved FROM trips JOIN users ON trips.driver_id = users.id LEFT OUTER JOIN (SELECT id as reserved, trip_id as relation_trip_id FROM trips_users WHERE user_id = ?) as temp ON relation_trip_id = trips.id WHERE driver_id <> ? AND LOWER(from_city) LIKE '?%' AND LOWER(to_city) LIKE '?%'", new Object[] { user.getId(), user.getId(), newFrom, newTo }, (final ResultSet rs) -> {
			do {
				Trip trip = new Trip();
				
				trip.setId(rs.getInt("id"));
				trip.setCreated(rs.getTimestamp("created"));
				trip.setEtd(rs.getString("etd"));
				trip.setEta(rs.getString("eta"));
				trip.setCost(rs.getDouble("cost"));
				trip.setReserved(rs.getInt("is_reserved") != 0);
				trip.setSeats(rs.getInt("seats"));
				trip.setFrom_city(rs.getString("from_city"));
				trip.setTo_city(rs.getString("to_city"));
				
				this.jdbcTemplate.query(" SELECT count(*) as amount FROM trips_users WHERE trip_id = ?", new Object[] { trip.getId()}, (final ResultSet rs2) -> {
					trip.setOccupied_seats(rs2.getInt("amount"));
				});
				
				if (trip.getAvailable_seats() < 1) continue;
				
				User driver = new User();
				driver.setId(rs.getInt("driver_id"));
				driver.setFirst_name(rs.getString("first_name"));
				driver.setLast_name(rs.getString("last_name"));
				driver.setPhone_number(rs.getString("phone_number"));
				trip.setDriver(driver);
				
				/*
				trip.setCar_id(rs.getInt("car_id"));
				trip.setDeparture_location(rs.getString("departure_location"));
				trip.setArrival_location(rs.getString("arrival_location"));
	            */
				trips.add(trip);
	        } while(rs.next());
		});

		return trips;
	}
	
	public List<Trip> findAll(User user) {
		List<Trip> trips = new ArrayList<>();
		this.jdbcTemplate.query("SELECT first_name, last_name, phone_number, trips.*, temp.reserved as is_reserved FROM trips JOIN users ON trips.driver_id = users.id LEFT OUTER JOIN (SELECT id as reserved, trip_id as relation_trip_id FROM trips_users WHERE user_id = ?) as temp ON relation_trip_id = trips.id WHERE driver_id <> ?", new Object[] { user.getId(), user.getId() }, (final ResultSet rs) -> {
			do {
				Trip trip = new Trip();
				
				trip.setId(rs.getInt("id"));
				trip.setCreated(rs.getTimestamp("created"));
				trip.setEtd(rs.getString("etd"));
				trip.setEta(rs.getString("eta"));
				trip.setCost(rs.getDouble("cost"));
				trip.setReserved(rs.getInt("is_reserved") != 0);
				trip.setFrom_city(rs.getString("from_city"));
				trip.setTo_city(rs.getString("to_city"));
				
				User driver = new User();
				driver.setId(rs.getInt("driver_id"));
				driver.setFirst_name(rs.getString("first_name"));
				driver.setLast_name(rs.getString("last_name"));
				driver.setPhone_number(rs.getString("phone_number"));
				trip.setDriver(driver);
				
				/*
				trip.setCar_id(rs.getInt("car_id"));
				trip.setDeparture_location(rs.getString("departure_location"));
				trip.setArrival_location(rs.getString("arrival_location"));
	            */
				trips.add(trip);
	        } while(rs.next());
		});

		return trips;
	}
	
	public Trip findById(final Integer tripId) {
		Trip trip = new Trip();
		this.jdbcTemplate.query("SELECT * FROM trips LEFT OUTER JOIN users ON driver_id = users.id  WHERE trips.id = ? LIMIT 1", new Object[] {tripId}, (final ResultSet rs) -> {
			trip.setId(rs.getInt("id"));
			trip.setCreated(rs.getTimestamp("created"));
			trip.setEtd(rs.getString("etd"));
			trip.setEta(rs.getString("eta"));
			trip.setCost(rs.getDouble("cost"));
			trip.setSeats(rs.getInt("seats"));
			trip.setFrom_city(rs.getString("from_city"));
			trip.setTo_city(rs.getString("to_city"));
			trip.setDriver_id(rs.getInt("driver_id"));
			
			this.jdbcTemplate.query(" SELECT id FROM trips_users WHERE trip_id = ? AND user_id = ?", new Object[] { tripId, trip.getDriver_id() }, (final ResultSet rs2) -> {
				
				trip.setReserved(rs2.next());
			});
			
			this.jdbcTemplate.query(" SELECT count(*) as amount FROM trips_users WHERE trip_id = ?", new Object[] { tripId }, (final ResultSet rs2) -> {
				trip.setOccupied_seats(rs2.getInt("amount"));
			});
			
			User driver = new User();
			driver.setId(rs.getInt("driver_id"));
			driver.setFirst_name(rs.getString("first_name"));
			driver.setLast_name(rs.getString("last_name"));
			driver.setPhone_number(rs.getString("phone_number"));
			trip.setDriver(driver);
		});

		return trip;
	}
}
