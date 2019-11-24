package ar.edu.itba.paw.webapp.DTO;

import java.sql.Timestamp;

import ar.edu.itba.paw.models.History;

public class HistoryDTO {
	private Integer user_id;
	private Integer trip_id;
	private String type;
	private Timestamp created;
	private Boolean own;
	private Integer id;
	
	public HistoryDTO (History history) {
		this.user_id = history.getRelated().getId();
		this.trip_id = history.getTrip().getId();
		this.type = history.getType();
		this.own = history.getOwn();
		this.id = history.getId();
		this.created =history.getCreated();
	}
	
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getTrip_id() {
		return trip_id;
	}
	public void setTrip_id(Integer trip_id) {
		this.trip_id = trip_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Timestamp getCreated() {
		return created;
	}
	public void setCreated(Timestamp created) {
		this.created = created;
	}
	public Boolean getOwn() {
		return own;
	}
	public void setOwn(Boolean own) {
		this.own = own;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
}
