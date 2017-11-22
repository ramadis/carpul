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
@Table(name = "trips_users")
public class Reservation {
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name="trip_id")
	private Trip trip;
	
	@Column
	private Timestamp created;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trips_users_id_seq")
	@SequenceGenerator(sequenceName = "trips_users_id_seq", name = "trips_users_id_seq", allocationSize = 1)
	private Integer id;
	
	public Reservation() {
		super();
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
}
