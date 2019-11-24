package ar.edu.itba.paw.webapp.DTO;

import java.sql.Timestamp;

import ar.edu.itba.paw.models.Review;

public class ReviewDTO {
	private Integer id;
	private Integer ownerId;
	private Integer reviewedId;
	private String message;
	private Integer stars;
	private Integer trip;
	private Timestamp created;
	
	public ReviewDTO (Review review) {
		this.id = review.getId();
		this.ownerId = review.getOwner().getId();
		this.reviewedId = review.getReviewed().getId();
		this.message = review.getMessage();
		this.stars = review.getStars();
		this.trip = review.getTrip().getId();
		this.created = review.getCreated();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}
	public Integer getReviewedId() {
		return reviewedId;
	}
	public void setReviewedId(Integer reviewedId) {
		this.reviewedId = reviewedId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getStars() {
		return stars;
	}
	public void setStars(Integer stars) {
		this.stars = stars;
	}
	public Integer getTrip() {
		return trip;
	}
	public void setTrip(Integer trip) {
		this.trip = trip;
	}
	public Timestamp getCreated() {
		return created;
	}
	public void setCreated(Timestamp created) {
		this.created = created;
	}

}
