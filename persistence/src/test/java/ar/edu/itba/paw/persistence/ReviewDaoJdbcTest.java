package ar.edu.itba.paw.persistence;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;

import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.persistence.UserDaoJdbc;

import static org.junit.Assert.*;

import java.util.List;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class ReviewDaoJdbcTest {
	@Autowired
	private DataSource ds;

	@Autowired
	private ReviewDaoJdbc reviewDao;

	@Autowired
	private UserDaoJdbc userDao;

	@Autowired
	private TripDaoHibernate tripDao;

	private JdbcTemplate jdbcTemplate;
	private Review testReview;

	@Before
	public void setUp() {
		testReview = TestUtils.ReviewUtils.sampleReview();
		jdbcTemplate = new JdbcTemplate(ds);
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "trips");
		jdbcTemplate.execute("TRUNCATE TABLE trips RESTART IDENTITY;");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
		jdbcTemplate.execute("TRUNCATE TABLE users RESTART IDENTITY;");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "reviews");
		jdbcTemplate.execute("TRUNCATE TABLE reviews RESTART IDENTITY;");
		userDao.create(TestUtils.UserUtils.sampleUser());
		tripDao.create(TestUtils.TripUtils.sampleTrip(), TestUtils.UserUtils.sampleUser());
	}

	public void assertReview(Review review) {
		assertNotNull(review);
		assertEquals(TestUtils.ReviewUtils.OWNER_ID, review.getOwner().getId());
		assertEquals(TestUtils.ReviewUtils.REVIEWED_ID, review.getReviewed().getId());
		assertEquals(TestUtils.ReviewUtils.STARS, review.getStars());
		assertEquals(TestUtils.ReviewUtils.MESSAGE, review.getMessage());
		assertEquals(TestUtils.ReviewUtils.TRIP_ID, review.getTrip().getId());
		assertNotNull(review.getCreated());
		assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "reviews"));
	}

	@Test
	public void testAdd() {
		// Create review
		Review createdReview = reviewDao.add(testReview);

		// Asserts for review
		assertReview(createdReview);
	}

	@Test
	public void testGetReviewsTrip() {
		// Create review
		reviewDao.add(TestUtils.ReviewUtils.sampleReview());

		// Create review
		List<Review> reviews = reviewDao.getReviews(TestUtils.TripUtils.sampleTrip());

		// Asserts for review
		assertReview(reviews.get(0));
	}

	@Test
	public void testGetReviewsUser() {
		// Create review
		testAdd();

		// Create review
		List<Review> reviews = reviewDao.getReviews(TestUtils.UserUtils.sampleUser());

		// Asserts for review
		assertReview(reviews.get(0));
	}

}
