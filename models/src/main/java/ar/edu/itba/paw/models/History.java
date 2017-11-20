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
@Table(name = "histories")
public class History {
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name="user_id")
	private User related;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name="trip_id")
	private Trip trip;
	
	@Column(length = 20)
	private String type;
	
	@Column
	private Timestamp created;
	
	@Column
	private Boolean own;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "histories_id_seq")
	@SequenceGenerator(sequenceName = "histories_id_seq", name = "histories_id_seq", allocationSize = 1)
	private Integer id;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Boolean getOwn() {
		return own;
	}
	
	public void setOwn(Boolean own) {
		this.own = own;
	}
	
	public User getRelated() {
		return related;
	}
	
	public void setRelated(User related) {
		this.related = related;
	}
	
	public Trip getTrip() {
		return trip;
	}
	
	public void setTrip(Trip trip) {
		this.trip = trip;
	}
	
	public Timestamp getCreated() {
		return created;
	}
	
	public void setCreated(Timestamp created) {
		this.created = created;
	}
	
	public History() {
		super();
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
}
