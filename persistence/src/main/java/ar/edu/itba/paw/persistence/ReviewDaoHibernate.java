package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.ReviewDao;
import ar.edu.itba.paw.models.Pagination;
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
	
	public List<Review> getReviews(User user, Pagination pagination) {
		String query = "FROM Review r WHERE r.reviewed = :reviewed ORDER BY created desc";
		
		List<Review> reviews = em.createQuery(query, Review.class)
								 .setParameter("reviewed", user)
								 .setMaxResults(pagination.getPer_page())
								 .setFirstResult(pagination.getFirstResult())
								 .getResultList();
		
		return reviews;
	}
	
	public Review getReviewById(int id) {
		String query = "SELECT r FROM Review r where r.id = :id";
		List<Review> reviews = em.createQuery(query, Review.class)
						.setParameter("id", id)
						.getResultList();
		
		return reviews.isEmpty() ? null : reviews.get(0);
	}
	
	public Boolean canLeaveReview(Trip trip, User user) {
		String query = "SELECT t from Trip t WHERE t = :trip AND EXISTS (SELECT r from Review r WHERE r.trip = :trip AND r.owner = :owner)";
		
		List<Trip> trips = em.createQuery(query, Trip.class)
							 .setParameter("trip", trip)
							 .setParameter("owner", user)
							 .getResultList();
		
		return trips.isEmpty();
	}


	public List<Review> getReviews(Trip trip, Pagination pagination) {
		String query = "FROM Review as r WHERE r.trip = :trip ORDER BY created desc";
		
		List<Review> reviews = em.createQuery(query, Review.class)
								 .setParameter("trip", trip)
								 .setFirstResult(pagination.getFirstResult())
								 .setMaxResults(pagination.getPer_page())
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
