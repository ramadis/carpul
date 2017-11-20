package ar.edu.itba.paw.models;

import java.sql.Timestamp;

public class History {
	private User related;
	private Trip trip;
	private String type;
	private Timestamp created;
	private Boolean own;
	
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
