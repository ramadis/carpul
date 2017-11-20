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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
public class HistoryDaoHibernate implements HistoryDao {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	UserDao userDao;

	@Autowired
	TripDao tripDao;

	public List<History> getHistories(User user) {
		String query = "from History as histories WHERE histories.trip IN (from Trip as trips WHERE driver = :driver) AND histories.own = FALSE OR histories.related = :related AND histories.own = TRUE ORDER BY histories.created desc";
		
		List<User> users = em.createQuery(query, User.class)
							 .setParameter("username", query)
							 .setMaxResults(5)
							 .getResultList();
		
		return users.isEmpty() ? null : users.get(0);
		
		
		List<History> histories = new ArrayList<>();

		String query = "SELECT * FROM histories WHERE (trip_id IN (SELECT id FROM trips WHERE driver_id = ?) AND own = FALSE ) OR (user_id = ? AND own = TRUE ) ORDER BY created desc LIMIT 5";
		Object[] params = new Object[] { user.getId(), user.getId() };
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

	public History addHistory(User user, Integer tripId, String type, Boolean own) {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		String query = "INSERT INTO histories (created, user_id, trip_id, type, own) VALUES (?,?,?,?, ?);";
		Object[] params = new Object[] { now, user.getId(), tripId, type, own };

		this.connection.update(query, params);
		
		History history = new History();
		Trip trip = new Trip();
		trip.setId(tripId);
		history.setCreated(now);
		history.setRelated(user);
		history.setTrip(trip);
		history.setType(type);
		history.setOwn(own);
		return history;
	}
	
//	public History addDeletedHistory(Integer tripId) {
//		// INSERT INTO histories (SELECT user_id, "DELETED" FROM trips_users WHETE trip_id = 2)
//	}
}
