package ar.edu.itba.paw.models;

import java.sql.Timestamp;

public class Review {
	private Integer id;
	private User owner;
	private User reviewedUser;
	private String message;
	private Integer stars;
	private Trip trip;
	private Timestamp created;
	
	public Review() {
		super();
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public User getReviewedUser() {
		return reviewedUser;
	}
	public void setReviewedUser(User reviewedUser) {
		this.reviewedUser = reviewedUser;
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
	public Trip getTrip() {
		return trip;
	}
	public void setTrip(Trip trip) {
		this.trip = trip;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Timestamp getCreated() {
		return created;
	}
	public void setCreated(Timestamp created) {
		this.created = created;
	}
	
	
}
