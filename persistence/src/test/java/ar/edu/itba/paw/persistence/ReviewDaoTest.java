package ar.edu.itba.paw.persistence;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.paw.interfaces.ReviewDao;
import ar.edu.itba.paw.interfaces.TripDao;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.persistence.UserDaoJdbc;

import static org.junit.Assert.*;

import java.util.List;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class ReviewDaoTest {
	@Autowired
	private DataSource ds;

	@Autowired
	private ReviewDao reviewDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private TripDao tripDao;

	private JdbcTemplate jdbcTemplate;
	private Review testReview;

	@Before
	public void setUp() {
		
		jdbcTemplate = new JdbcTemplate(ds);
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "trips");
		jdbcTemplate.execute("TRUNCATE TABLE trips RESTART IDENTITY;");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
		jdbcTemplate.execute("TRUNCATE TABLE users RESTART IDENTITY;");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "reviews");
		jdbcTemplate.execute("TRUNCATE TABLE reviews RESTART IDENTITY;");
		
		String[] sequences = {"users_id_seq", "trips_id_seq", "reviews_id_seq"}; 
		
		for (String sequence : sequences) {
		    jdbcTemplate.execute("DROP SEQUENCE " + sequence + " IF EXISTS");
		    jdbcTemplate.execute("CREATE SEQUENCE " + sequence + " as INTEGER");
		}
		
		userDao.create(TestUtils.UserUtils.sampleUser());
		tripDao.create(TestUtils.TripUtils.sampleTrip(), userDao.getById(1));
		
		testReview = TestUtils.ReviewUtils.sampleReview(userDao.getById(1), userDao.getById(1), tripDao.findById(1));
	}

	public void assertReview(Review review) {
		assertNotNull(review);
		assertEquals(TestUtils.ReviewUtils.OWNER_ID, review.getOwner().getId());
		assertEquals(TestUtils.ReviewUtils.REVIEWED_ID, review.getReviewed().getId());
		assertEquals(TestUtils.ReviewUtils.STARS, review.getStars());
		assertEquals(TestUtils.ReviewUtils.MESSAGE, review.getMessage());
		assertEquals(TestUtils.ReviewUtils.TRIP_ID, review.getTrip().getId());
		assertNotNull(review.getCreated());
	}
	
	public Review createReview() {
		return reviewDao.add(testReview);
	}

	@Test
	@Transactional
	public void testAdd() {
		// Create review
		Review createdReview = createReview();

		// Asserts for review
		assertReview(createdReview);
	}

	@Test
	@Transactional
	public void testGetReviewsTrip() {
		// Create review
		reviewDao.add(testReview);

		// Create review
		List<Review> reviews = reviewDao.getReviews(TestUtils.TripUtils.sampleTrip());

		// Asserts for review
		assertReview(reviews.get(0));
	}

	@Test
	@Transactional
	public void testGetReviewsUser() {
		// Create review
		createReview();

		// Create review
		List<Review> reviews = reviewDao.getReviews(TestUtils.UserUtils.sampleUser());

		// Asserts for review
		assertReview(reviews.get(0));
	}

}
