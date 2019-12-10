package ar.edu.itba.paw.webapp.DTO;

import java.net.URI;
import java.sql.Timestamp;

import ar.edu.itba.paw.models.Review;

public class ReviewDTO {
	private Integer id;
	private UserDTO owner;
	private TripDTO trip;
	private Integer reviewedId;
	private String message;
	private Integer stars;
	private Timestamp created;
	private String image;
	
	public ReviewDTO() {}
	
	public ReviewDTO(Review review, String url, URI uri) {
		this(review, uri);
		this.image = review.getImage() == null ? null : url + "/image"; // Image in dto is just the stringified id of the review
	}
	
	public ReviewDTO (Review review, URI uri) {
		this.id = review.getId();
		this.owner = new UserDTO(review.getOwner(), uri.toString() + review.getOwner().getId());
		this.reviewedId = review.getReviewed().getId();
		this.message = review.getMessage();
		this.stars = review.getStars();
		this.trip = new TripDTO(review.getTrip(), uri);
		this.created = review.getCreated();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Timestamp getCreated() {
		return created;
	}
	public void setCreated(Timestamp created) {
		this.created = created;
	}
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public UserDTO getOwner() {
		return owner;
	}

	public void setOwner(UserDTO owner) {
		this.owner = owner;
	}

	public TripDTO getTrip() {
		return trip;
	}

	public void setTrip(TripDTO trip) {
		this.trip = trip;
	}

}
