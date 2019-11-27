package ar.edu.itba.paw.interfaces;

import java.util.List;

import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

public interface ReviewService {
	Review getReviewById(int id);
	List<Review> getReviews(Trip trip);
	List<Review> getReviews(User user);
	Boolean canLeaveReview(Trip trip, User user);
	Review add(Review review);
	Review uploadImage(Review review, byte[] image);
}

