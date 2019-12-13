package ar.edu.itba.paw.interfaces;

import java.util.List;

import ar.edu.itba.paw.models.Pagination;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

public interface ReviewService {
	List<Review> getReviews(Trip trip, Pagination pagination);
	List<Review> getReviews(User user, Pagination pagination);
	Boolean canLeaveReview(Trip trip, User user);
	Review add(Review review);
	Review uploadImage(Review review, byte[] image);
	Review getReviewById(int id);
}

