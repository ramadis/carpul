package ar.edu.itba.paw.webapp.DTO;

import java.net.URI;
import java.sql.Timestamp;
import java.util.stream.Collectors;

import ar.edu.itba.paw.models.Position;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

public class UnauthTripDTO {
	private Integer id;
	private Timestamp etd;
	private Timestamp eta;
	private String from_city;
	private String to_city;
	private Double cost;
	private Integer seats;
	private PositionDTO departure;
	private PositionDTO arrival;
	private Integer occupied_seats;
	private UserDTO driver;
	private Boolean expired;
	private Boolean reserved;
	private Boolean reviewed;
	private Integer available_seats;
	
	public UnauthTripDTO() {}
	
	public UnauthTripDTO(Trip trip, User loggedUser, URI uri) {
		this(trip, uri);
		this.reserved = trip.getPassengers().contains(loggedUser);
		this.reviewed = expired && trip.getReviews().stream().map(review -> review.getOwner()).collect(Collectors.toList()).contains(loggedUser);
	}
	
	public UnauthTripDTO(Trip trip, URI uri) {
		this.id = trip.getId();
		this.etd = trip.getEtd();
		this.eta = trip.getEta();
		this.from_city = trip.getFrom_city();
		this.to_city = trip.getTo_city();
		this.cost = trip.getCost();
		this.seats = trip.getSeats();
		this.departure = new PositionDTO(new Position(trip.getDeparture_lat(), trip.getDeparture_lon()));
		this.arrival = new PositionDTO(new Position(trip.getArrival_lat(), trip.getArrival_lon()));
		this.occupied_seats = trip.getOccupied_seats();
		this.driver = new UserDTO(trip.getDriver(), uri.toString() + trip.getDriver().getId());
		this.expired = trip.getExpired();
		this.available_seats = this.seats - this.occupied_seats;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Timestamp getEta() {
		return eta;
	}
	public void setEta(Timestamp eta) {
		this.eta = eta;
	}
	public Timestamp getEtd() {
		return etd;
	}
	public void setEtd(Timestamp etd) {
		this.etd = etd;
	}
	public String getFrom_city() {
		return from_city;
	}
	public void setFrom_city(String from_city) {
		this.from_city = from_city;
	}
	public String getTo_city() {
		return to_city;
	}
	public void setTo_city(String to_city) {
		this.to_city = to_city;
	}
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	public Integer getSeats() {
		return seats;
	}
	public void setSeats(Integer seats) {
		this.seats = seats;
	}
	public PositionDTO getDeparture() {
		return departure;
	}
	public void setDeparture(PositionDTO departure) {
		this.departure = departure;
	}
	public PositionDTO getArrival() {
		return arrival;
	}
	public void setArrival(PositionDTO arrival) {
		this.arrival = arrival;
	}
	public Integer getOccupied_seats() {
		return occupied_seats;
	}
	public void setOccupied_seats(Integer occupied_seats) {
		this.occupied_seats = occupied_seats;
	}
	public Boolean getExpired() {
		return expired;
	}
	public void setExpired(Boolean expired) {
		this.expired = expired;
	}

	public UserDTO getDriver() {
		return driver;
	}

	public void setDriver(UserDTO driver) {
		this.driver = driver;
	}

	public Boolean getReserved() {
		return reserved;
	}

	public void setReserved(Boolean reserved) {
		this.reserved = reserved;
	}

	public Integer getAvailable_seats() {
		return available_seats;
	}

	public void setAvailable_seats(Integer available_seats) {
		this.available_seats = available_seats;
	}

	public Boolean getReviewed() {
		return reviewed;
	}

	public void setReviewed(Boolean reviewed) {
		this.reviewed = reviewed;
	}
	
}

