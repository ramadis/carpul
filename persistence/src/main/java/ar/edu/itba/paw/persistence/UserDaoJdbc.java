package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UserDaoJdbc implements UserDao {

	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;

	@Autowired
	public UserDaoJdbc(final DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		/* TODO: export table name as a private final String */
		this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("users").usingColumns("username", "password");
		/* TODO: export table creation as a private final String */
		jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS users (id serial PRIMARY KEY, username varchar (100), password varchar (100))");
	}

	public User create(String username, String password) {
		final Map<String, Object> args = new HashMap<String, Object>();
		args.put("username", username);
		args.put("password", password);

		jdbcInsert.execute(args);
		
		return new User(username, password);
	}
	
	public User getByUsername(String username) {
		User user = new User();
		this.jdbcTemplate.query("SELECT * FROM users WHERE username = \'" + username + "\' LIMIT 1", (final ResultSet rs) -> {
			user.setUsername(rs.getString("username"));
			user.setPassword(rs.getString("password"));
			user.setId(rs.getInt("id"));
		});
		
		return user;
	}
	
	public User findById(final Integer userId) {
		User user = new User();
		this.jdbcTemplate.query("SELECT * FROM users WHERE id = \'" + userId + "\' LIMIT 1", (final ResultSet rs) -> {
			user.setUsername(rs.getString("username"));
			user.setPassword(rs.getString("password"));
			user.setId(rs.getInt("id"));
		});

		return user;
	}
}
