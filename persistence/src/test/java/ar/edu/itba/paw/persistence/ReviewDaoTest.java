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

import static org.junit.Assert.*;

import java.util.List;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
@Transactional
public class ReviewDaoTest {
	@Autowired
	private DataSource ds;

	@Autowired
	private ReviewDao reviewDao;

	private JdbcTemplate jdbcTemplate;

	@Before
//	public void setUp() {
//		
//		jdbcTemplate = new JdbcTemplate(ds);
//		
//		testReview = TestUtils.ReviewUtils.sampleReview(userDao.getById(1), userDao.getById(1), tripDao.findById(1));
//	}

	public void assertReview(Review review) {
		assertNotNull(review);
		assertEquals(TestUtils.ReviewUtils.OWNER_ID, review.getOwner().getId());
		assertEquals(TestUtils.ReviewUtils.REVIEWED_ID, review.getReviewed().getId());
		assertEquals(TestUtils.ReviewUtils.STARS, review.getStars());
		assertEquals(TestUtils.ReviewUtils.MESSAGE, review.getMessage());
		assertEquals(TestUtils.ReviewUtils.TRIP_ID, review.getTrip().getId());
		assertNotNull(review.getCreated());
	}
	
//	public Review createReview() {
//		return reviewDao.add(testReview);
//	}
//
//	@Test
//	@Transactional
//	public void testAdd() {
//		Review review = TestUtils.ReviewUtils.sampleReview(userDao.getById(1), userDao.getById(1), tripDao.findById(1));
//		// Create review
//		Review createdReview = createReview();
//
//		// Asserts for review
//		assertReview(createdReview);
//	}
//
//	@Test
//	@Transactional
//	public void testGetReviewsTrip() {
//		// Create review
//		reviewDao.add(testReview);
//
//		// Create review
//		List<Review> reviews = reviewDao.getReviews(tripDao.findById(1));
//
//		// Asserts for review
//		assertReview(reviews.get(0));
//	}
//
//	@Test
//	@Transactional
//	public void testGetReviewsUser() {
//		// Create review
//		createReview();
//
//		// Create review
//		List<Review> reviews = reviewDao.getReviews(tripDao.findById(1));
//
//		// Asserts for review
//		assertReview(reviews.get(0));
//	}

}
