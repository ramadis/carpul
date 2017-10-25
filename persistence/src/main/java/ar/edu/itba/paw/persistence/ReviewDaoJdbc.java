package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.ReviewDao;
import ar.edu.itba.paw.interfaces.TripDao;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.Review;
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
public class ReviewDaoJdbc implements ReviewDao {

	private final JdbcTemplate connection;
	private final String dbSchema = "CREATE TABLE IF NOT EXISTS reviews (id serial PRIMARY KEY, created timestamp, owner_id integer, reviewed_id integer, trip_id integer, stars integer, message text)";

	@Autowired
	public ReviewDaoJdbc(final DataSource dataSource) {
		this.connection = new JdbcTemplate(dataSource);
		//this.connection.execute(dbSchema);
	}

	@Autowired
	UserDao userDao;

	@Autowired
	TripDao tripDao;

	private void loadResultIntoReview(ResultSet rs, Review review) {
		try {
			review.setId(rs.getInt("id"));
			review.setStars(rs.getInt("stars"));
			review.setMessage(rs.getString("message"));
			review.setReviewedUser(userDao.getById(rs.getInt("reviewed_id")));
			review.setOwner(userDao.getById(rs.getInt("owner_id")));
			review.setTrip(tripDao.findById(rs.getInt("trip_id")));
			review.setCreated(rs.getTimestamp("created"));
		} catch (Throwable e) {}
	}

	public List<Review> getReviews(User user) {
		List<Review> reviews = new ArrayList<>();

		String query = "SELECT * FROM reviews WHERE reviewed_id = ? ORDER BY created desc;";
		Object[] params = new Object[] { user.getId() };
		this.connection.query(query, params, (ResultSet rs) -> {
			do {
				Review review = new Review();
				loadResultIntoReview(rs, review);
				reviews.add(review);
			} while(rs.next());
		});

		return reviews;
	}
	
	public Boolean canLeaveReview(Trip trip, User user) {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		// TODO: Check why eta < now is not returning valid row
		String query = "SELECT * FROM trips WHERE trips.id = ? AND eta < ? AND NOT EXISTS (SELECT * FROM reviews WHERE trip_id = ? AND owner_id = ?)";
		Object[] params = new Object[] { trip.getId(), now, trip.getId(), user.getId() };
		
		Trip t = new Trip();
		this.connection.query(query, params, (ResultSet rs) -> {
			t.setId(rs.getInt("id"));
		});
		
		return t.getId() == null;
	}


	public List<Review> getReviews(Trip trip) {
		List<Review> reviews = new ArrayList<>();
		String query = "SELECT * FROM reviews WHERE trip_id = ?";
		Object[] params = new Object[] { trip.getId() };
		
		this.connection.query(query, params, (ResultSet rs) -> {
			Review review = new Review();
			loadResultIntoReview(rs, review);
			reviews.add(review);
		});
		
		return reviews;
	}

	public Boolean add(Review review) {
		String query = "INSERT INTO reviews (created, owner_id, reviewed_id, stars, message, trip_id) VALUES (?,?,?,?,?,?)";
		Timestamp now = new Timestamp(System.currentTimeMillis());
		Object[] params = new Object[] { now,
										review.getOwner().getId(),
										review.getReviewedUser().getId(),
										review.getStars(),
										review.getMessage(),
										review.getTrip().getId() };
		this.connection.update(query, params);
		return true;
	}
}
