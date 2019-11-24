package ar.edu.itba.paw.webapp.DTO;

import java.sql.Timestamp;

import ar.edu.itba.paw.models.Position;
import ar.edu.itba.paw.models.Trip;

public class TripDTO {
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
	private Integer driver_id;
	private Boolean expired;
	
	public TripDTO() {}
	
	public TripDTO(Trip trip) {
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
		this.driver_id = trip.getDriver().getId();
		this.expired = trip.getExpired();
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
	public Integer getDriver_id() {
		return driver_id;
	}
	public void setDriver_id(Integer driver_id) {
		this.driver_id = driver_id;
	}
	public Boolean getExpired() {
		return expired;
	}
	public void setExpired(Boolean expired) {
		this.expired = expired;
	}
}

