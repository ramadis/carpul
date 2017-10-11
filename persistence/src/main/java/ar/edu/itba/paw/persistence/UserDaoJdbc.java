package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDaoJdbc implements UserDao {

	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	
	public void loadResultIntoUser(ResultSet rs, User user) {
		try {
			user.setUsername(rs.getString("username"));
			user.setPassword(rs.getString("password"));
			user.setFirst_name(rs.getString("first_name"));
			user.setCreated(rs.getTimestamp("created"));
			user.setLast_name(rs.getString("last_name"));
			user.setPhone_number(rs.getString("phone_number"));
			user.setId(rs.getInt("id"));
		} catch (Exception e) {}
	}
	
	public void loadResultIntoTrip(ResultSet rs, Trip trip) {
		try {
			trip.setId(rs.getInt("id"));
			trip.setCreated(rs.getTimestamp("created"));
			trip.setEtd(rs.getString("etd"));
			trip.setEta(rs.getString("eta"));
			trip.setCost(rs.getDouble("cost"));
			trip.setFrom_city(rs.getString("from_city"));
			trip.setTo_city(rs.getString("to_city"));
		} catch (Exception e) {}
	}
	
	@Autowired
	public UserDaoJdbc(final DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		/* TODO: export table name as a private final String */
		this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("users").usingColumns("username", "password");
		/* TODO: export table creation as a private final String */
		jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS users (id serial PRIMARY KEY, created timestamp, first_name varchar (100), phone_number varchar (100), last_name varchar (100), username varchar (100), password varchar (100))");
	}

	public User create(User user) {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		jdbcTemplate.update("INSERT INTO users (username, password, first_name, last_name, phone_number, created) VALUES (?,?,?,?,?,?)",
				new Object[] { user.getUsername(), user.getPassword(), user.getFirst_name(), user.getLast_name(), user.getPhone_number(), now });
		
		return user;
	}
	
	public User getByUsername(String username) {
		User user = new User();
		this.jdbcTemplate.query("SELECT * FROM users WHERE username = \'" + username + "\' LIMIT 1", (final ResultSet rs) -> {
			this.loadResultIntoUser(rs, user);
		});
		
		return user;
	}
	
	public List<Trip> getUserTrips(User user) {
		List<Trip> trips = new ArrayList<>();
		System.out.println("El driver_id es " + user.getId());
		this.jdbcTemplate.query("SELECT * FROM trips WHERE trips.driver_id = ?", new Object[] {user.getId()}, (final ResultSet rs) -> {
			do {
				Trip trip = new Trip();
				
				trip.setId(rs.getInt("id"));
				trip.setEtd(rs.getString("etd"));
				trip.setEta(rs.getString("eta"));
				//trip.addPassenger(rs.getInt("user_id"));
				trip.setCost(rs.getDouble("cost"));
				trip.setDriver_id(rs.getInt("driver_id"));
				
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
	
	public List<Trip> getReservedTrips(User user) {
		List<Trip> trips = new ArrayList<>();
		
		this.jdbcTemplate.query("SELECT * FROM trips JOIN trips_users ON trip_id = trips.id JOIN users ON users.id = driver_id WHERE user_id = ?", new Object[] {user.getId()}, (final ResultSet rs) -> {
			do {
				Trip trip = new Trip();
				loadResultIntoTrip(rs, trip);

				
				User driver = new User();
				loadResultIntoUser(rs, driver);
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
	
	public User findById(final Integer userId) {
		User user = new User();
		this.jdbcTemplate.query("SELECT * FROM users WHERE id = ? LIMIT 1", new Object[] {user.getId()}, (final ResultSet rs) -> {
			this.loadResultIntoUser(rs, user);
		});

		return user;
	}
}
