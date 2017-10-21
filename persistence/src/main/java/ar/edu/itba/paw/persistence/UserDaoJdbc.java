package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.Position;
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
public class UserDaoJdbc implements UserDao {

	private final JdbcTemplate jdbcTemplate;
	
	public void loadResultIntoUser(ResultSet rs, User user) {
		try {
			user.setUsername(rs.getString("username"));
			user.setPassword(rs.getString("password"));
			user.setFirst_name(rs.getString("first_name"));
			user.setCreated(rs.getTimestamp("created"));
			user.setLast_name(rs.getString("last_name"));
			user.setPhone_number(rs.getString("phone_number"));
			user.setId(rs.getInt("id"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void loadResultIntoTrip(ResultSet rs, Trip trip) {
		try {
			// TODO: this should be referencede to TripDaoJDBC.
			trip.setId(rs.getInt("id"));
			trip.setCreated(rs.getTimestamp("created"));
			trip.setEtd(rs.getTimestamp("etd"));
			trip.setEta(rs.getTimestamp("eta"));
			trip.setCost(rs.getDouble("cost"));
			trip.setSeats(rs.getInt("seats"));
			trip.setFrom_city(rs.getString("from_city"));
			trip.setTo_city(rs.getString("to_city"));
			
			Position departure = new Position(rs.getDouble("departure_lat"), rs.getDouble("departure_lon"));
			Position arrival = new Position(rs.getDouble("arrival_lat"), rs.getDouble("arrival_lon"));
			trip.setArrival(arrival);
			trip.setDeparture(departure);
		} catch (Exception e) {}
	}
	
	@Autowired
	public UserDaoJdbc(final DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		/* TODO: export table name as a private final String */
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
	
	public User getById(Integer id) {
		User user = new User();
		this.jdbcTemplate.query("SELECT * FROM users WHERE id = ? LIMIT 1", new Object[] {id}, (final ResultSet rs) -> {
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
				loadResultIntoTrip(rs, trip);
				this.jdbcTemplate.query("SELECT * FROM users JOIN trips_users on users.id = trips_users.user_id WHERE trip_id = ?", new Object[] { trip.getId()}, (final ResultSet rs2) -> {
					do {
						User passenger = new User();
						loadResultIntoUser(rs2, passenger);
						trip.addPassenger(passenger);
					}while (rs2.next());
				});
				trip.setOccupied_seats(trip.getPassengers().size());
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

				this.jdbcTemplate.query("SELECT count(*) as amount FROM trips_users WHERE trip_id = ?", new Object[] { trip.getId()}, (final ResultSet rs2) -> {
					trip.setOccupied_seats(rs2.getInt("amount"));
				});
				
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
		return this.getById(userId);
	}
}
