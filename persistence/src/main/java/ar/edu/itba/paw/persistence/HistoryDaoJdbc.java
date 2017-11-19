package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.HistoryDao;
import ar.edu.itba.paw.interfaces.TripDao;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.History;
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
public class HistoryDaoJdbc implements HistoryDao {

	private final JdbcTemplate connection;

	@Autowired
	public HistoryDaoJdbc(final DataSource dataSource) {
		this.connection = new JdbcTemplate(dataSource);
	}

	@Autowired
	UserDao userDao;

	@Autowired
	TripDao tripDao;

	private void loadResultIntoHistory(ResultSet rs, History history) {
		try {
			history.setCreated(rs.getTimestamp("created"));
			history.setType(rs.getString("type"));
			history.setRelated(userDao.getById(rs.getInt("user_id")));
			history.setTrip(tripDao.findById(rs.getInt("trip_id")));
		} catch (Throwable e) {
		}
	}

	public List<History> getHistories(User user) {
		List<History> histories = new ArrayList<>();

		String query = "SELECT * FROM histories WHERE trip_id IN (SELECT id FROM trips WHERE driver_id = ?) ORDER BY created desc LIMIT 5";
		Object[] params = new Object[] { user.getId() };
		this.connection.query(query, params, (ResultSet rs) -> {
			do {
				History history = new History();
				loadResultIntoHistory(rs, history);
				histories.add(history);
			} while(rs.next());
		});

		return histories;
	}

	public List<History> getHistoriesByPage(User user, Integer page) {
		List<History> histories = new ArrayList<>();

		String query = "SELECT * FROM histories WHERE trip_id IN (SELECT id FROM trips WHERE driver_id = ?) ORDER BY created desc LIMIT 5 OFFSET ?;";
		Object[] params = new Object[] { user.getId(), new Integer(page * 5) };
		this.connection.query(query, params, (ResultSet rs) -> {
			do {
				History history = new History();
				loadResultIntoHistory(rs, history);
				histories.add(history);
			} while(rs.next());
		});

		return histories;
	}

	public History addHistory(User user, Integer tripId, String type) {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		String query = "INSERT INTO histories (created, user_id, trip_id, type) VALUES (?,?,?,?);";
		Object[] params = new Object[] { now, user.getId(), tripId, type };

		this.connection.update(query, params);
		
		History history = new History();
		Trip trip = new Trip();
		trip.setId(tripId);
		history.setCreated(now);
		history.setRelated(user);
		history.setTrip(trip);
		history.setType(type);
		return history;
	}
	
//	public History addDeletedHistory(Integer tripId) {
//		// INSERT INTO histories (SELECT user_id, "DELETED" FROM trips_users WHETE trip_id = 2)
//	}
}
