package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
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

	@Autowired
	public UserDaoJdbc(final DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		//jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS users (id serial PRIMARY KEY, created timestamp, first_name varchar (100), phone_number varchar (100), last_name varchar (100), username varchar (100), password varchar (100))");
	}
	
	public static void loadResultIntoUser(ResultSet rs, User user) {
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
	
	public static void loadReducedResultIntoUser(ResultSet rs, User user) {
		try {
			user.setFirst_name(rs.getString("first_name"));
			user.setLast_name(rs.getString("last_name"));
			user.setPhone_number(rs.getString("phone_number"));
		} catch (Exception e) {}
	}

	public User create(User user) {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		String query = "INSERT INTO users (username, password, first_name, last_name, phone_number, created) VALUES (?,?,?,?,?,?)";
		Object[] params = new Object[] { user.getUsername(), user.getPassword(), user.getFirst_name(), user.getLast_name(), user.getPhone_number(), now };

		jdbcTemplate.update(query, params);

		return user;
	}

	public User getByUsername(String username) {
		String query = "SELECT * FROM users WHERE username = ? LIMIT 1";
		Object[] params = new Object[] { username };
		User user = new User();
		
		this.jdbcTemplate.query(query, params, (final ResultSet rs) -> {
			loadResultIntoUser(rs, user);
		});

		return user;
	}

	public User getById(Integer id) {
		String query = "SELECT * FROM users WHERE id = ? LIMIT 1";
		Object[] params = new Object[] { id };
		User user = new User();
		
		this.jdbcTemplate.query(query, params, (final ResultSet rs) -> {
			loadResultIntoUser(rs, user);
		});

		return user;
	}
	
	public List<User> getPassengers(Trip trip) {
		String query = "SELECT * FROM users JOIN trips_users on users.id = trips_users.user_id WHERE trip_id = ?";
		Object[] params = new Object[] { trip.getId() };
		List<User> users = new ArrayList<>();
		
		this.jdbcTemplate.query(query, params, (final ResultSet rs) -> {
			do {
				User passenger = new User();
				loadResultIntoUser(rs, passenger);
				users.add(passenger);
			}while (rs.next());
		});
		
		return users;
	}


}
