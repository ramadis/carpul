package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.HistoryDao;
import ar.edu.itba.paw.interfaces.TripDao;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.History;
import ar.edu.itba.paw.models.Review;
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
	private UserDao userDao;

	@Autowired
	private TripDao tripDao;

	public List<History> getHistories(User user) {
		// TODO: Check queries with LIMITs that are filtered later
		String query = "SELECT h FROM History h WHERE h.own = FALSE and h.trip IN (SELECT t FROM Trip t WHERE t.driver = :user) OR h.own = TRUE AND h.related = :user ORDER BY h.created DESC";
		
		List<History> histories = em.createQuery(query, History.class)
									.setParameter("user", user)
									.setMaxResults(5)
				 				    .getResultList();

		return histories;
	}

	public List<History> getHistoriesByPage(User user, Integer page) {
		return getHistories(user);
	}

	public History addHistory(User user, Integer tripId, String type, Boolean own) {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		
		History history = new History();
		Trip trip = tripDao.findById(tripId);
		
		history.setCreated(now);
		history.setRelated(user);
		history.setTrip(trip);
		history.setType(type);
		history.setOwn(own);
		
		em.persist(history);
		return history;
	}
	
//	public History addDeletedHistory(Integer tripId) {
//		// INSERT INTO histories (SELECT user_id, "DELETED" FROM trips_users WHETE trip_id = 2)
//	}
}
