package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.ReviewDao;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class ReviewDaoHibernate implements ReviewDao {

	@PersistenceContext
	private EntityManager em;
	
	public Review uploadImage(Review review, byte[] image) {
		review.setImage(image);
		em.merge(review);
		return review;
	}
	
	public List<Review> getReviews(User user) {
		String query = "SELECT r FROM Review r WHERE r.reviewed = :reviewed ORDER BY created desc";
		
		List<Review> reviews = em.createQuery(query, Review.class)
								 .setParameter("reviewed", user)
								 .getResultList();
		
		return reviews;
	}
	
	public Review getReviewById(int id) {
		String query = "SELECT r FROM Review r where r.id = :id";
		Review review = em.createQuery(query, Review.class)
						.setParameter("id", id)
						.getSingleResult();
		return review;
	}
	
	public Boolean canLeaveReview(Trip trip, User user) {
		String query = "SELECT t from Trip t WHERE t = :trip AND EXISTS (SELECT r from Review r WHERE r.trip = :trip AND r.owner = :owner)";
		
		List<Trip> trips = em.createQuery(query, Trip.class)
							 .setParameter("trip", trip)
							 .setParameter("owner", user)
							 .getResultList();
		
		return trips.isEmpty();
	}


	public List<Review> getReviews(Trip trip) {
		String query = "from Review as r WHERE r.trip = :trip";
		
		List<Review> reviews = em.createQuery(query, Review.class)
								 .setParameter("trip", trip)
								 .getResultList();
		
		return reviews;
	}

	public Review add(Review review) {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		review.setCreated(now);
		
		em.persist(review);
		
		return review;
	}
}
