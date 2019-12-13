package ar.edu.itba.paw.webapp.DTO;

import java.net.URI;
import java.sql.Timestamp;

import ar.edu.itba.paw.models.History;

public class HistoryDTO {
	private UserDTO user;
	private TripDTO trip;
	private String type;
	private Timestamp created;
	private Boolean own;
	private Integer id;
	
	public HistoryDTO (History history, URI uri) {
		this.trip = new TripDTO(history.getTrip(), uri);
		this.user = new UserDTO(history.getRelated(), uri.toString() + history.getRelated().getId());
		this.type = history.getType();
		this.own = history.getOwn();
		this.id = history.getId();
		this.created =history.getCreated();
	}
	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public TripDTO getTrip() {
		return trip;
	}

	public void setTrip(TripDTO trip) {
		this.trip = trip;
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
