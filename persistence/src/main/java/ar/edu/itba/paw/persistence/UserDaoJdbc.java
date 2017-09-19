package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.sql.ResultSet;
import java.util.Map;

@Repository
public class UserDaoJdbc implements UserDao {

	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;

	@Autowired
	public UserDaoJdbc(final DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		/* TODO: export table name as a private final String */
		this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("users");
		/* TODO: export table creation as a private final String */
		
		String query = "CREATE TABLE IF NOT EXISTS users (" +
					    "\"id\" serial," +
					    "\"username\" varchar(100)," +
					    "\"password\" varchar(100)," +
					    "\"first_name\" varchar(100)," +
					    "\"last_name\" varchar(100)," +
					    "\"session_token\" varchar(100)," +
					    "\"birthdate\" date," +
					    "\"deleted\" boolean DEFAULT 'False'," +
					    "PRIMARY KEY (\"id\"));";
		

		jdbcTemplate.execute(query);
	}

	public User create(User user) {
	
		final Map<String, Object> args = user.getParams();
		jdbcInsert.execute(args);
		return user;
	}
	
	public User findById(final String userId) {
		User user = new User();
		this.jdbcTemplate.query("SELECT * FROM users WHERE username = \'" + userId + "\' LIMIT 1", (final ResultSet rs) -> {
			user.setUsername(rs.getString("username"));
			user.setPassword(rs.getString("password"));
			user.setFirstName(rs.getString("first_name"));
			user.setLastName(rs.getString("last_name"));
		});

		return user;
	}
}
