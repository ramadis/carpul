package ar.edu.itba.paw.interfaces;

import java.util.List;

import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

public interface ReviewDao {
	List<Review> getReviews(Trip trip);
	List<Review> getReviews(User user);
	Boolean add(Review review);
	Boolean canLeaveReview(Trip trip, User user);
}
