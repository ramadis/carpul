package ar.edu.itba.paw.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "reviews")
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reviews_id_seq")
	@SequenceGenerator(sequenceName = "reviews_id_seq", name = "reviews_id_seq", allocationSize = 1)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name="owner_id")
	private User owner;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name="reviewed_id")
	private User reviewed;
	
	@Column(length = 500)
	private String message;
	
    @Column(name = "image")
    private byte[] image;
	
	@Column
	private Integer stars;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name="trip_id")
	private Trip trip;
	
	@Column
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
	
	public User getReviewed() {
		return reviewed;
	}
	
	public void setReviewedUser(User reviewedUser) {
		this.reviewed = reviewedUser;
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

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
}
