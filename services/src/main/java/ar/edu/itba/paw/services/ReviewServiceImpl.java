package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.ReviewDao;
import ar.edu.itba.paw.interfaces.ReviewService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

@Service
public class ReviewServiceImpl implements  ReviewService {

	@Autowired
	private ReviewDao reviewDao;
	
	public List<Review> getReviews(User user) {
		return reviewDao.getReviews(user);
	}
	
	public List<Review> getReviews(Trip trip) {
		return reviewDao.getReviews(trip);
	}
	
	public Boolean canLeaveReview(Trip trip, User user) {
		return reviewDao.canLeaveReview(trip, user);
	}
	
	public Review add(Review review) {
		return reviewDao.add(review);
	}
}
