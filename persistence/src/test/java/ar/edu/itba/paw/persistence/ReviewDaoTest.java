package ar.edu.itba.paw.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.paw.interfaces.ReviewDao;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

import static org.junit.Assert.*;

import java.util.List;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
@Transactional
public class ReviewDaoTest {
	@Autowired
	private ReviewDao reviewDao;

	@Test
	public void testAdd() {
		// Create helper objects
		User passenger = new User();
		passenger.setId(TestUtils.ReviewUtils.NONEXISTING_PASSENGER_ID);
		User driver = new User();
		driver.setId(TestUtils.ReviewUtils.EXISTING_DRIVER_ID);
		Trip trip = new Trip();
		trip.setId(TestUtils.ReviewUtils.EXISTING_TRIP_ID);
		
		// Create review
		Review review = TestUtils.ReviewUtils.sampleReview(passenger, driver, trip);
		review = reviewDao.add(review);
		
		// Asserts for review
		assertNotNull(review);
		assertNotNull(review.getId());
		assertEquals(TestUtils.ReviewUtils.NONEXISTING_PASSENGER_ID, review.getOwner().getId());
		assertEquals(TestUtils.ReviewUtils.EXISTING_DRIVER_ID, review.getReviewed().getId());
		assertEquals(TestUtils.ReviewUtils.STARS, review.getStars());
		assertEquals(TestUtils.ReviewUtils.MESSAGE, review.getMessage());
		assertEquals(TestUtils.ReviewUtils.EXISTING_TRIP_ID, review.getTrip().getId());
		assertNotNull(review.getCreated());
	}

	@Test
	public void testGetReviewsTrip() {
		// Create helper objects
		Trip trip = new Trip();
		trip.setId(TestUtils.ReviewUtils.EXISTING_ID);
		int expectedReviewsAmount = 1;
		
		// Get reviews
		List<Review> reviews = reviewDao.getReviews(trip);
		
		// Asserts
		assertEquals(expectedReviewsAmount, reviews.size());
	}

	@Test
	public void testGetReviewsUser() {
		// Create helper objects
		User user = new User();
		user.setId(TestUtils.ReviewUtils.EXISTING_DRIVER_ID);
		int expectedReviewsAmount = 1;
		
		// Get reviews
		List<Review> reviews = reviewDao.getReviews(user);

		// Asserts
		assertEquals(expectedReviewsAmount, reviews.size());
	}
}
